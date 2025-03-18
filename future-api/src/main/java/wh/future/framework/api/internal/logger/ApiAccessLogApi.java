package wh.future.framework.api.internal.logger;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import wh.future.framework.api.internal.logger.dto.ApiAccessLogCreateReq;
import wh.future.framework.api.internal.logger.enums.ApiConstants;
import wh.future.framework.common.pojo.R;

import javax.validation.Valid;

/**
 * @author Administrator
 */
@FeignClient(name = ApiConstants.NAME)
@Tag(name = "RPC 服务 - API 访问日志")
public interface ApiAccessLogApi {

    String PREFIX = ApiConstants.PREFIX + "/api-access-api_log";

    @PostMapping(PREFIX + "/create")
    @Operation(summary = "创建 API 访问日志")
    R<Boolean> createApiAccessLog(@Valid @RequestBody ApiAccessLogCreateReq req);

    @Async
    default void createApiAccessLogAsync(ApiAccessLogCreateReq req) {
        createApiAccessLog(req);
    }

}
