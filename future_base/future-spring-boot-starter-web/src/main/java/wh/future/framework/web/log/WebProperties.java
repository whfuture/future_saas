package wh.future.framework.web.log;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author Administrator
 */
@ConfigurationProperties(prefix = "future.web")
@Validated
public class WebProperties {

    @NotNull(message = "API 不能为空")
    private Api controllerApi = new Api("/api", "**.controller.**");

    public Api getControllerApi() {
        return controllerApi;
    }

    public void setControllerApi(Api controllerApi) {
        this.controllerApi = controllerApi;
    }

    @Valid
    public static class Api {

        @NotEmpty(message = "API 前缀不能为空")
        private String prefix;

        @NotEmpty(message = "Controller 所在包不能为空")
        private String controller;

        public Api(String prefix, String controller) {
            this.prefix = prefix;
            this.controller = controller;
        }

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        public String getController() {
            return controller;
        }

        public void setController(String controller) {
            this.controller = controller;
        }
    }

}
