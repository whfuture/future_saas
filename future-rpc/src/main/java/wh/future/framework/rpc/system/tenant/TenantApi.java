package wh.future.framework.rpc.system.tenant;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wh.future.framework.common.pojo.R;
import java.util.List;

@FeignClient(name = "system-server")
@Tag(name = "RPC 服务 - 多租户")
public interface TenantApi {

    String PREFIX = "/rpc-rpc/system-server/tenant";

    @GetMapping(PREFIX + "/id-list")
    @Operation(summary = "获得所有租户编号")
    R<List<Long>> getTenantIdList();

    @GetMapping(PREFIX + "/valid")
    @Operation(summary = "校验租户是否合法")
    @Parameter(name = "id", description = "租户编号", required = true, example = "1024")
    R<Boolean> validTenant(@RequestParam("id") Long id);

}
