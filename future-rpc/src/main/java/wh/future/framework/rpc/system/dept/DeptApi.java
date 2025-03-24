package wh.future.framework.rpc.system.dept;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wh.future.framework.rpc.system.dept.req.DeptResp;
import wh.future.framework.common.pojo.R;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import wh.future.framework.common.util.CollectionUtil;

@FeignClient(name = "system-server")
@Tag(name = "RPC 服务 - 部门")
public interface DeptApi {

    String PREFIX = "rpc-api_log/system-server/dept";

    @GetMapping(PREFIX + "/get")
    @Operation(summary = "获得部门信息")
    @Parameter(name = "id", description = "部门编号", example = "1024", required = true)
    R<DeptResp> getDept(@RequestParam("id") Long id);

    @GetMapping(PREFIX + "/list")
    @Operation(summary = "获得部门信息数组")
    @Parameter(name = "ids", description = "部门编号数组", example = "1,2", required = true)
    R<List<DeptResp>> getDeptList(@RequestParam("ids") Collection<Long> ids);

    @GetMapping(PREFIX + "/valid")
    @Operation(summary = "校验部门是否合法")
    @Parameter(name = "ids", description = "部门编号数组", example = "1,2", required = true)
    R<Boolean> validateDeptList(@RequestParam("ids") Collection<Long> ids);


    default Map<Long, DeptResp> getDeptMap(Collection<Long> ids) {
        List<DeptResp> list = getDeptList(ids).getData();
        return CollectionUtil.convertMap(list, DeptResp::getId);
    }

    @GetMapping(PREFIX + "/list-child")
    @Operation(summary = "获得指定部门的所有子部门")
    @Parameter(name = "id", description = "部门编号", example = "1024", required = true)
    R<List<DeptResp>> getChildDeptList(@RequestParam("id") Long id);

}
