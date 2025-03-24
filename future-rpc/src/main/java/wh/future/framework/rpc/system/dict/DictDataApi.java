package wh.future.framework.rpc.system.dict;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wh.future.framework.rpc.system.dict.resp.DictDataResp;
import wh.future.framework.common.pojo.R;
import java.util.Collection;
import java.util.List;
import static wh.future.framework.common.util.CollectionUtil.convertList;


@FeignClient(name = "system-server")
@Tag(name = "RPC 服务 - 字典数据")
public interface DictDataApi {

    String PREFIX = "/rpc-api_log/system-server/dict-data";

    @GetMapping(PREFIX + "/valid")
    @Operation(summary = "校验字典数据们是否有效")
    @Parameters({
        @Parameter(name = "dictType", description = "字典类型", example = "SEX", required = true),
        @Parameter(name = "descriptions", description = "字典数据值的数组", example = "1,2", required = true)
    })
    R<Boolean> validateDictDataList(@RequestParam("dictType") String dictType,
                                               @RequestParam("values") Collection<String> values);

    @GetMapping(PREFIX + "/get")
    @Operation(summary = "获得指定的字典数据")
    @Parameters({
            @Parameter(name = "dictType", description = "字典类型", example = "SEX", required = true),
            @Parameter(name = "description", description = "字典数据值", example = "1", required = true)
    })
    R<DictDataResp> getDictData(@RequestParam("dictType") String dictType,
                                @RequestParam("value") String value);

    /**
     * 获得指定的字典标签，从缓存中
     *
     * @param type  字典类型
     * @param value 字典数据值
     * @return 字典标签
     */
    default String getDictDataLabel(String type, Integer value) {
        DictDataResp dictData = getDictData(type, String.valueOf(value)).getData();
        if (ObjUtil.isNull(dictData)) {
            return StrUtil.EMPTY;
        }
        return dictData.getLabel();
    }

    @GetMapping(PREFIX + "/parse")
    @Operation(summary = "解析获得指定的字典数据")
    @Parameters({
            @Parameter(name = "dictType", description = "字典类型", example = "SEX", required = true),
            @Parameter(name = "label", description = "字典标签", example = "男", required = true)
    })
    R<DictDataResp> parseDictData(@RequestParam("dictType") String dictType,
                                                @RequestParam("label") String label);

    @GetMapping(PREFIX + "/list")
    @Operation(summary = "获得指定字典类型的字典数据列表")
    @Parameter(name = "dictType", description = "字典类型", example = "SEX", required = true)
    R<List<DictDataResp>> getDictDataList(@RequestParam("dictType") String dictType);

    /**
     * 获得字典数据标签列表
     *
     * @param dictType 字典类型
     * @return 字典数据标签列表
     */
    default List<String> getDictDataLabelList(String dictType) {
        List<DictDataResp> list = getDictDataList(dictType).getData();
        return convertList(list, DictDataResp::getLabel);
    }

}
