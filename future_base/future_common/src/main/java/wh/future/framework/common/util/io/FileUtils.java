package wh.future.framework.common.util.io;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import lombok.SneakyThrows;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {

    public static File createTempFile(String data) {
        File tempFile = createTempFile();
        FileUtil.writeUtf8String(data, tempFile);
        return tempFile;
    }

    @SneakyThrows
    public static File createTempFile() {
        // 创建文件，通过 UUID 保证唯一
        File file = File.createTempFile(IdUtil.simpleUUID(), null);
        // 标记 JVM 退出时，自动删除
        file.deleteOnExit();
        return file;
    }

    public static File createTempFile(byte[] data) {
        File tempFile = createTempFile();
        FileUtil.writeBytes(data, tempFile);
        return tempFile;
    }

    public static String generatePath(byte[] content, String originalName) {
        String sha256Hex = DigestUtil.sha256Hex(content);
        if (StrUtil.isNotBlank(originalName)) {
            String extName = FileNameUtil.extName(originalName);
            return StrUtil.isBlank(extName) ? sha256Hex : sha256Hex + "." + extName;
        }
        return sha256Hex + '.' + FileTypeUtil.getType(new ByteArrayInputStream(content));
    }

    public static String readUtf8(InputStream in, boolean isClose) throws IORuntimeException {
        return StrUtil.utf8Str(IoUtil.read(in, isClose));
    }
}
