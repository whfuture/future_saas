package wh.future.framework.common.util.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.time.Duration;
import java.util.concurrent.Executors;


public class BuildCacheUtil {

    public static <K, V> LoadingCache<K, V> buildAsyncCache(Duration duration, CacheLoader<K, V> loader) {
        return CacheBuilder.
                newBuilder().
                refreshAfterWrite(duration).
                build(CacheLoader.asyncReloading(loader, Executors.newCachedThreadPool()));
    }

    public static <K, V> LoadingCache<K, V> buildBlockCache(Duration duration, CacheLoader<K, V> loader) {
        return CacheBuilder.newBuilder().refreshAfterWrite(duration).build(loader);
    }

}
