package wh.future.framework.web.xss;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.Collections;
import java.util.List;

/**
 * Xss 配置属性
 */
@ConfigurationProperties(prefix = "future.xss")
@Validated
@Data
public class XssProperties {

    private boolean enable = true;

    private List<String> excludeUrls = Collections.emptyList();

}
