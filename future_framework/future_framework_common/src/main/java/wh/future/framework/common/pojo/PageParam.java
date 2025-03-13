package wh.future.framework.common.pojo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Schema(description = "分页参数")
public class PageParam {

    private static final Integer PAGE_NO = 1;

    private static final Integer PAGE_SIZE = 10;

    @Schema(description = "页码，从 1 开始", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码最小值为 1")
    private Integer pageNo;

    @Schema(description = "每页条数，最大值为 1000", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    @NotNull(message = "每页条数不能为空")
    @Min(value = 1, message = "每页条数最小值为 1")
    @Max(value = 1000, message = "每页条数最大值为 1000")
    private Integer pageSize;

    private Boolean needPage;

    public PageParam() {
        pageNo = PAGE_NO;
        needPage = Boolean.TRUE;
        pageSize = PAGE_SIZE;
    }

    public PageParam(int pageNo, int pageSize, boolean needPage) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.needPage = needPage;
    }

}
