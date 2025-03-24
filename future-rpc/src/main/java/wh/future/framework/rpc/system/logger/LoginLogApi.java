package wh.future.framework.rpc.system.logger;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import wh.future.framework.rpc.system.logger.req.LoginLogCreateReq;
import wh.future.framework.common.pojo.R;
import javax.validation.Valid;

@FeignClient(name = "system-server")
@Tag(name = "RPC 服务 - 登录日志")
public interface LoginLogApi {

    String PREFIX = "/rpc-api_log/system-server/login-log";

    @PostMapping(PREFIX + "/create")
    @Operation(summary = "创建登录日志")
    R<Boolean> createLoginLog(@Valid @RequestBody LoginLogCreateReq reqDTO);

}
