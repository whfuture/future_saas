package wh.future.framework.redis.event;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractRedisMessage {

    private Map<String,String> headers = new HashMap<>();

    public String getHeader(String key) {
        return headers.get(key);
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

}
