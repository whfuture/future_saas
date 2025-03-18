package wh.future.framework.redis.event;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class AbstractRedisStreamMessage extends AbstractRedisMessage {

    @JsonIgnore
    public String getStreamKey() {
        return getClass().getSimpleName();
    }

}