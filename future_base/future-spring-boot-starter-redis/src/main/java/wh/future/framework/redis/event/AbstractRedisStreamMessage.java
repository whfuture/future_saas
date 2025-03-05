package wh.future.framework.redis.event;

import com.fasterxml.jackson.annotation.JsonIgnore;


public abstract class AbstractRedisStreamMessage extends AbstractRedisMessage {

    @JsonIgnore // 避免序列化
    public String getStreamKey() {
        return getClass().getSimpleName();
    }

}
