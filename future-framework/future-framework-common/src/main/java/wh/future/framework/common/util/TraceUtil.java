package wh.future.framework.common.util;

import org.apache.skywalking.apm.toolkit.trace.TraceContext;

public class TraceUtil {


    private TraceUtil() {
    }


    public static String getTraceId() {
        return TraceContext.traceId();
    }

}
