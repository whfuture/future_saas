package wh.future.framework.common.util.object;

import cn.hutool.core.util.ReflectUtil;

import java.lang.reflect.Field;
import java.util.function.Consumer;

public class ObjectUtil {

    public static <T> T cloneIgnoreId(T object, Consumer<T> consumer) {
        T result = cn.hutool.core.util.ObjectUtil.clone(object);
        // 忽略 id 编号
        Field field = ReflectUtil.getField(object.getClass(), "id");
        if (field != null) {
            ReflectUtil.setFieldValue(result, field, null);
        }
        // 二次编辑
        if (result != null) {
            consumer.accept(result);
        }
        return result;
    }
}
