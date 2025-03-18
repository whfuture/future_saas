package wh.future.framework.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SortField {

    private String field;

    private Integer order;
    /*true升序 false降序*/
    private Boolean asc;

}
