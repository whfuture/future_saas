package wh.future.framework.rpc.system.notify;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import wh.future.framework.rpc.system.notify.req.NotifySendSingleToUserReq;
import wh.future.framework.common.pojo.R;
import javax.validation.Valid;

@FeignClient(name = "system-server")
@Tag(name = "RPC 服务 - 站内信发送")
public interface NotifyMessageSendApi {

    String PREFIX ="/rpc-rpc/system-server/notify/send";

    @PostMapping(PREFIX + "/send-single-admin")
    @Operation(summary = "发送单条站内信给 Admin 用户")
    R<Long> sendSingleMessageToAdmin(@Valid @RequestBody NotifySendSingleToUserReq req);

    @PostMapping(PREFIX + "/send-single-member")
    @Operation(summary = "发送单条站内信给 Member 用户")
    R<Long> sendSingleMessageToMember(@Valid @RequestBody NotifySendSingleToUserReq req);

}
