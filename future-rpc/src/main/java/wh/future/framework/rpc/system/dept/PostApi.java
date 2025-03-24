package wh.future.framework.rpc.system.dept;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wh.future.framework.rpc.system.dept.req.PostResp;
import wh.future.framework.common.pojo.R;
import wh.future.framework.common.util.CollectionUtil;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@FeignClient(name = "system-server")
@Tag(name = "RPC 服务 - 岗位")
public interface PostApi {

    String PREFIX = "/rpc-api_log/system-server/post";

    @GetMapping(PREFIX + "/valid")
    @Operation(summary = "校验岗位是否合法")
    @Parameter(name = "ids", description = "岗位编号数组", example = "1,2", required = true)
    R<Boolean> validPostList(@RequestParam("ids") Collection<Long> ids);

    @GetMapping(PREFIX + "/list")
    @Operation(summary = "获得岗位列表")
    @Parameter(name = "ids", description = "岗位编号数组", example = "1,2", required = true)
    R<List<PostResp>> getPostList(@RequestParam("ids") Collection<Long> ids);

    default Map<Long, PostResp> getPostMap(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return MapUtil.empty();
        }
        List<PostResp> list = getPostList(ids).getData();
        return CollectionUtil.convertMap(list, PostResp::getId);
    }

}
