package wh.future.framework.db.mybatis.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fhs.core.trans.vo.TransPojo;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(value = "transMap")
public abstract class BaseDO implements Serializable, TransPojo {

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT, jdbcType = JdbcType.VARCHAR)
    private String creator;

    @TableField(fill = FieldFill.INSERT_UPDATE, jdbcType = JdbcType.VARCHAR)
    private String updater;

    private Boolean deleted;

}