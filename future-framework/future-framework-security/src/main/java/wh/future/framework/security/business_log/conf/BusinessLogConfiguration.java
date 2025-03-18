package wh.future.framework.security.business_log.conf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import wh.future.framework.api.internal.business_logger.BusinessLogApi;

@AutoConfiguration
@Slf4j
@EnableFeignClients(clients = {BusinessLogApi.class}) // 主要是引入相关的 API 服务
public class BusinessLogConfiguration {
}
