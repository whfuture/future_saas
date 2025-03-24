package wh.future.framework.rpc.operation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import wh.future.framework.rpc.operation.req.OperateLogCreateReq;
import wh.future.framework.rpc.operation.req.OperateLogPageReq;
import wh.future.framework.rpc.operation.resp.OperateLogResp;
import wh.future.framework.common.pojo.PageResult;
import wh.future.framework.common.pojo.R;
import javax.validation.Valid;

@FeignClient(name = "system-server")
@Tag(name = "RPC 服务 - 业务操作日志")
public interface OperationApi {

    String PREFIX = "/rpc-api_log/system/operate-log";

    @PostMapping(PREFIX + "/create")
    @Operation(summary = "创建操作日志")
    R<Boolean> createOperateLog(@Valid @RequestBody OperateLogCreateReq createReq);

    @Async
    default void createOperateLogAsync(OperateLogCreateReq createReq) {
        createOperateLog(createReq);
    }

    @GetMapping(PREFIX + "/page")
    @Operation(summary = "获取指定模块的指定数据的操作日志分页")
    R<PageResult<OperateLogResp>> getOperateLogPage(@SpringQueryMap OperateLogPageReq pageReq);

}
