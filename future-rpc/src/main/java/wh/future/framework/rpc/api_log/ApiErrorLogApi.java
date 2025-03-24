package wh.future.framework.rpc.api_log;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import wh.future.framework.rpc.api_log.req.ApiErrorLogCreateReq;
import wh.future.framework.common.pojo.R;

import javax.validation.Valid;

@FeignClient(name = "infra-server")
@Tag(name = "RPC 服务 - API 异常日志")
public interface ApiErrorLogApi {

    String PREFIX = "/rpc-api_log/infra/api_log-error-api_log";

    @PostMapping(PREFIX + "/create")
    @Operation(summary = "创建 API 异常日志")
    R<Boolean> createApiErrorLog(@Valid @RequestBody ApiErrorLogCreateReq createDTO);

    @Async
    default void createApiErrorLogAsync(ApiErrorLogCreateReq createDTO) {
        createApiErrorLog(createDTO);
    }

}
