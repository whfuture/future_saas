package wh.future.framework.web.api_log;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author Administrator
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "future.web")
@Validated
public class WebProperties {

    @NotNull(message = "API 不能为空")
    private Api webApi = new Api("/api", "**.controller.**");

    @Setter
    @Getter
    @Valid
    public static class Api {

        @NotEmpty(message = "API 前缀不能为空")
        private String prefix;

        @NotEmpty(message = "Controller 所在包不能为空")
        private String path;

        public Api(String prefix, String path) {
            this.prefix = prefix;
            this.path = path;
        }

    }

}
