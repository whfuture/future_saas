package wh.future.framework.rpc.system.oauth2;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import wh.future.framework.rpc.system.oauth2.req.OAuth2AccessTokenCreateReq;
import wh.future.framework.rpc.system.oauth2.resp.OAuth2AccessTokenCheckResp;
import wh.future.framework.rpc.system.oauth2.resp.OAuth2AccessTokenResp;
import wh.future.framework.common.pojo.R;
import javax.validation.Valid;

@FeignClient(name = "system-server")
@Tag(name = "RPC 服务 - OAuth2.0 令牌")
public interface OAuth2TokenApi {

    String PREFIX = "/rpc-rpc/system-server/oauth2/token";

    @PostMapping(PREFIX + "/create")
    @Operation(summary = "创建访问令牌")
    R<OAuth2AccessTokenResp> createAccessToken(@Valid @RequestBody OAuth2AccessTokenCreateReq req);

    @GetMapping(PREFIX + "/check")
    @Operation(summary = "校验访问令牌")
    @Parameter(name = "accessToken", description = "访问令牌", required = true, example = "tudou")
    R<OAuth2AccessTokenCheckResp> checkAccessToken(@RequestParam("accessToken") String accessToken);

    @DeleteMapping(PREFIX + "/remove")
    @Operation(summary = "移除访问令牌")
    @Parameter(name = "accessToken", description = "访问令牌", required = true, example = "tudou")
    R<OAuth2AccessTokenResp> removeAccessToken(@RequestParam("accessToken") String accessToken);

    @PutMapping(PREFIX + "/refresh")
    @Operation(summary = "刷新访问令牌")
    @Parameters({
        @Parameter(name = "refreshToken", description = "刷新令牌", required = true, example = "haha"),
        @Parameter(name = "clientId", description = "客户端编号", required = true, example = "yudaoyuanma")
    })
    R<OAuth2AccessTokenResp> refreshAccessToken(@RequestParam("refreshToken") String refreshToken,
                                                              @RequestParam("clientId") String clientId);

}
