package wh.future.framework.rpc.system.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wh.future.framework.rpc.system.user.resp.AdminUserResp;
import wh.future.framework.common.pojo.R;
import wh.future.framework.common.util.CollectionUtil;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@FeignClient(name = "system-server")
@Tag(name = "RPC 服务 - 管理员用户")
public interface AdminUserApi{

    String PREFIX = "/rpc-rpc/system-server/user";

    @GetMapping(PREFIX + "/get")
    @Operation(summary = "通过用户 ID 查询用户")
    @Parameter(name = "id", description = "用户编号", example = "1", required = true)
    R<AdminUserResp> getUser(@RequestParam("id") Long id);

    @GetMapping(PREFIX + "/list-by-subordinate")
    @Operation(summary = "通过用户 ID 查询用户下属")
    @Parameter(name = "id", description = "用户编号", example = "1", required = true)
    R<List<AdminUserResp>> getUserListBySubordinate(@RequestParam("id") Long id);

    @GetMapping(PREFIX + "/list")
    @Operation(summary = "通过用户 ID 查询用户们")
    @Parameter(name = "ids", description = "部门编号数组", example = "1,2", required = true)
    R<List<AdminUserResp>> getUserList(@RequestParam("ids") Collection<Long> ids);

    @GetMapping(PREFIX + "/list-by-dept-id")
    @Operation(summary = "获得指定部门的用户数组")
    @Parameter(name = "deptIds", description = "部门编号数组", example = "1,2", required = true)
    R<List<AdminUserResp>> getUserListByDeptIds(@RequestParam("deptIds") Collection<Long> deptIds);

    @GetMapping(PREFIX + "/list-by-post-id")
    @Operation(summary = "获得指定岗位的用户数组")
    @Parameter(name = "postIds", description = "岗位编号数组", example = "2,3", required = true)
    R<List<AdminUserResp>> getUserListByPostIds(@RequestParam("postIds") Collection<Long> postIds);

    /**
     * 获得用户 Map
     *
     * @param ids 用户编号数组
     * @return 用户 Map
     */
    default Map<Long, AdminUserResp> getUserMap(Collection<Long> ids) {
        List<AdminUserResp> users = getUserList(ids).getData();
        return CollectionUtil.convertMap(users, AdminUserResp::getId);
    }

    /**
     * 校验用户是否有效。如下情况，视为无效：
     * 1. 用户编号不存在
     * 2. 用户被禁用
     *
     * @param id 用户编号
     */
    default void validateUser(Long id) {
        validateUserList(Collections.singleton(id));
    }

    @GetMapping(PREFIX + "/valid")
    @Operation(summary = "校验用户们是否有效")
    @Parameter(name = "ids", description = "用户编号数组", example = "3,5", required = true)
    R<Boolean> validateUserList(@RequestParam("ids") Collection<Long> ids);

}
