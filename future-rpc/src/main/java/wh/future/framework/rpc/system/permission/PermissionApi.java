package wh.future.framework.rpc.system.permission;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wh.future.framework.rpc.system.permission.resp.DeptDataPermissionResp;
import wh.future.framework.common.pojo.R;
import java.util.Collection;
import java.util.Set;

@FeignClient(name = "system-server")
@Tag(name = "RPC 服务 - 权限")
public interface PermissionApi {

    String PREFIX = "/rpc-rpc/system-server/permission";

    @GetMapping(PREFIX + "/user-role-id-list-by-role-id")
    @Operation(summary = "获得拥有多个角色的用户编号集合")
    @Parameter(name = "roleIds", description = "角色编号集合", example = "1,2", required = true)
    R<Set<Long>> getUserRoleIdListByRoleIds(@RequestParam("roleIds") Collection<Long> roleIds);

    @GetMapping(PREFIX + "/has-any-permissions")
    @Operation(summary = "判断是否有权限，任一一个即可")
    @Parameters({
            @Parameter(name = "userId", description = "用户编号", example = "1", required = true),
            @Parameter(name = "permissions", description = "权限", example = "read,write", required = true)
    })
    R<Boolean> hasAnyPermissions(@RequestParam("userId") Long userId,
                                 @RequestParam("permissions") String... permissions);

    @GetMapping(PREFIX + "/has-any-roles")
    @Operation(summary = "判断是否有角色，任一一个即可")
    @Parameters({
            @Parameter(name = "userId", description = "用户编号", example = "1", required = true),
            @Parameter(name = "roles", description = "角色数组", example = "2", required = true)
    })
    R<Boolean> hasAnyRoles(@RequestParam("userId") Long userId,
                                      @RequestParam("roles") String... roles);

    @GetMapping(PREFIX + "/get-dept-data-permission")
    @Operation(summary = "获得登陆用户的部门数据权限")
    @Parameter(name = "userId", description = "用户编号", example = "2", required = true)
    R<DeptDataPermissionResp> getDeptDataPermission(@RequestParam("userId") Long userId);

}