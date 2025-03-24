package wh.future.framework.web.api_log;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;
import wh.future.framework.rpc.api_log.ApiAccessLogApi;
import wh.future.framework.rpc.api_log.req.ApiAccessLogCreateReq;
import wh.future.framework.common.enums.ErrorCodeConstants;
import wh.future.framework.common.pojo.R;
import wh.future.framework.common.util.JsonUtil;
import wh.future.framework.common.util.ServletUtil;
import wh.future.framework.common.util.TraceUtil;
import wh.future.framework.web.util.WebFrameworkUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;
import java.util.Map;

import static wh.future.framework.web.api_log.AccessLogInterceptor.ATTRIBUTE_HANDLER_METHOD;

/**
 * API 访问日志 Filter
 * @author Administrator
 */
@Slf4j
public class ApiAccessLogFilter extends OncePerRequestFilter {

    private static final String[] SANITIZE_KEYS = new String[]{"password", "token", "accessToken", "refreshToken"};

    private final String applicationName;

    private final ApiAccessLogApi apiAccessLogApi;

    private final WebProperties webProperties;

    public ApiAccessLogFilter(WebProperties webProperties, String applicationName, ApiAccessLogApi apiAccessLogApi) {
        this.applicationName = applicationName;
        this.apiAccessLogApi = apiAccessLogApi;
        this.webProperties = webProperties;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String apiUri = request.getRequestURI().substring(request.getContextPath().length());
        return !StrUtil.startWithAny(apiUri, webProperties.getWebApi().getPrefix());
    }

    @Override
    @SuppressWarnings("NullableProblems")
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        LocalDateTime beginTime = LocalDateTime.now();
        Map<String, String> queryString = ServletUtil.getParamsMap(request);
        String requestBody = ServletUtil.isJsonRequest(request) ? ServletUtil.getBody(request) : null;
        try {
            filterChain.doFilter(request, response);
            doCreateApiAccessLog(request, beginTime, queryString, requestBody, null);
        } catch (Exception ex) {
            doCreateApiAccessLog(request, beginTime, queryString, requestBody, ex);
            throw ex;
        }
    }

    private void doCreateApiAccessLog(HttpServletRequest request, LocalDateTime beginTime,
                                      Map<String, String> queryString, String requestBody, Exception ex) {
        ApiAccessLogCreateReq accessLog = new ApiAccessLogCreateReq();
        try {
            boolean enable = buildApiAccessLogCreateReq(accessLog, request, beginTime, queryString, requestBody, ex);
            if (!enable) {
                return;
            }
            apiAccessLogApi.createApiAccessLogAsync(accessLog);
        } catch (Throwable th) {
            log.error("[createApiAccessLog][url({}) api_log({}) 发生异常]", request.getRequestURI(), JsonUtil.toJsonString(accessLog), th);
        }
    }

    private boolean buildApiAccessLogCreateReq(ApiAccessLogCreateReq accessLog, HttpServletRequest request, LocalDateTime beginTime,
                                               Map<String, String> queryString, String requestBody, Exception ex) {
        HandlerMethod handlerMethod = (HandlerMethod) request.getAttribute(ATTRIBUTE_HANDLER_METHOD);
        ApiAccessLog accessLogAnnotation = null;
        if (handlerMethod != null) {
            accessLogAnnotation = handlerMethod.getMethodAnnotation(ApiAccessLog.class);
            if (accessLogAnnotation != null && BooleanUtil.isFalse(accessLogAnnotation.enabled())) {
                return false;
            }
        }
        // 处理用户信息
        accessLog.setUserId(WebFrameworkUtils.getLoginUserId(request))
                .setUserType(WebFrameworkUtils.getLoginUserType(request));
        // 设置访问结果
        R<?> result = WebFrameworkUtils.getR(request);
        if (result != null) {
            accessLog.setResultCode(result.getCode()).setResultMsg(result.getMsg());
        } else if (ex != null) {
            accessLog.setResultCode(ErrorCodeConstants.INTERNAL_SERVER_ERROR.getCode())
                    .setResultMsg(ExceptionUtil.getRootCauseMessage(ex));
        } else {
            accessLog.setResultCode(ErrorCodeConstants.SUCCESS.getCode()).setResultMsg("");
        }
        // 设置请求字段
        accessLog.setTraceId(TraceUtil.getTraceId())
                .setApplicationName(applicationName)
                .setRequestUrl(request.getRequestURI()).setRequestMethod(request.getMethod())
                .setUserAgent(ServletUtil.getUserAgent(request)).setUserIp(ServletUtil.getClientIP(request));
        String[] sanitizeKeys = accessLogAnnotation != null ? accessLogAnnotation.sanitizeKeys() : null;
        Boolean requestEnable = accessLogAnnotation != null ? accessLogAnnotation.requestEnable() : Boolean.TRUE;
        if (!BooleanUtil.isFalse(requestEnable)) {
            // 默认记录，所以判断 !false
            Map<String, Object> requestParams = MapUtil.<String, Object>builder()
                    .put("query", sanitizeMap(queryString, sanitizeKeys))
                    .put("body", sanitizeJson(requestBody, sanitizeKeys)).build();
            accessLog.setRequestParams(JsonUtil.toJsonString(requestParams));
        }
        Boolean responseEnable = accessLogAnnotation != null ? accessLogAnnotation.responseEnable() : Boolean.FALSE;
        if (BooleanUtil.isTrue(responseEnable)) { // 默认不记录，默认强制要求 true
            accessLog.setResponseBody(sanitizeJson(result, sanitizeKeys));
        }
        // 持续时间
        accessLog.setBeginTime(beginTime).setEndTime(LocalDateTime.now())
                .setDuration((int) LocalDateTimeUtil.between(accessLog.getBeginTime(), accessLog.getEndTime(), ChronoUnit.MILLIS));
        // 操作模块
        if (handlerMethod != null) {
            Tag tagAnnotation = handlerMethod.getBeanType().getAnnotation(Tag.class);
            Operation operationAnnotation = handlerMethod.getMethodAnnotation(Operation.class);
            String operateModule = accessLogAnnotation != null && StrUtil.isNotBlank(accessLogAnnotation.operateModule()) ?
                    accessLogAnnotation.operateModule() :
                    tagAnnotation != null ? StrUtil.nullToDefault(tagAnnotation.name(), tagAnnotation.description()) : null;
            String operateName = accessLogAnnotation != null && StrUtil.isNotBlank(accessLogAnnotation.operateName()) ?
                    accessLogAnnotation.operateName() :
                    operationAnnotation != null ? operationAnnotation.summary() : null;
            OperateTypeEnum operateType = accessLogAnnotation != null && accessLogAnnotation.operateType().length > 0 ?
                    accessLogAnnotation.operateType()[0] : parseOperateLogType(request);
            accessLog.setOperateModule(operateModule).setOperateName(operateName).setOperateType(operateType.getType());
        }
        return true;
    }

    private static OperateTypeEnum parseOperateLogType(HttpServletRequest request) {
        RequestMethod requestMethod = ArrayUtil.firstMatch(method ->
                StrUtil.equalsAnyIgnoreCase(method.name(), request.getMethod()), RequestMethod.values());
        if (requestMethod == null) {
            return OperateTypeEnum.OTHER;
        }
        switch (requestMethod) {
            case GET:
                return OperateTypeEnum.GET;
            case POST:
                return OperateTypeEnum.CREATE;
            case PUT:
                return OperateTypeEnum.UPDATE;
            case DELETE:
                return OperateTypeEnum.DELETE;
            default:
                return OperateTypeEnum.OTHER;
        }
    }

    private static String sanitizeMap(Map<String, ?> map, String[] sanitizeKeys) {
        if (CollUtil.isEmpty(map)) {
            return null;
        }
        if (sanitizeKeys != null) {
            MapUtil.removeAny(map, sanitizeKeys);
        }
        MapUtil.removeAny(map, SANITIZE_KEYS);
        return JsonUtil.toJsonString(map);
    }

    private static String sanitizeJson(String jsonString, String[] sanitizeKeys) {
        if (StrUtil.isEmpty(jsonString)) {
            return null;
        }
        try {
            JsonNode rootNode = JsonUtil.parseTree(jsonString);
            sanitizeJson(rootNode, sanitizeKeys);
            return JsonUtil.toJsonString(rootNode);
        } catch (Exception e) {
            // 脱敏失败的情况下，直接忽略异常，避免影响用户请求
            log.error("[sanitizeJson][脱敏({}) 发生异常]", jsonString, e);
            return jsonString;
        }
    }

    private static String sanitizeJson(R<?> commonResult, String[] sanitizeKeys) {
        if (commonResult == null) {
            return null;
        }
        String jsonString = JsonUtil.toJsonString(commonResult);
        try {
            JsonNode rootNode = JsonUtil.parseTree(jsonString);
            sanitizeJson(rootNode.get("data"), sanitizeKeys);
            return JsonUtil.toJsonString(rootNode);
        } catch (Exception e) {
            log.error("[sanitizeJson][脱敏({}) 发生异常]", jsonString, e);
            return jsonString;
        }
    }

    private static void sanitizeJson(JsonNode node, String[] sanitizeKeys) {
        if (node.isArray()) {
            for (JsonNode childNode : node) {
                sanitizeJson(childNode, sanitizeKeys);
            }
            return;
        }
        if (!node.isObject()) {
            return;
        }
        Iterator<Map.Entry<String, JsonNode>> iterator = node.fields();
        while (iterator.hasNext()) {
            Map.Entry<String, JsonNode> entry = iterator.next();
            if (ArrayUtil.contains(sanitizeKeys, entry.getKey())
                    || ArrayUtil.contains(SANITIZE_KEYS, entry.getKey())) {
                iterator.remove();
                continue;
            }
            sanitizeJson(entry.getValue(), sanitizeKeys);
        }
    }

}
