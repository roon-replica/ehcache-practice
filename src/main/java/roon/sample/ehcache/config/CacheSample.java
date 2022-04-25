package roon.sample.ehcache.config;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

public class CacheSample {
    public static void main(String[] args) {
        // 캐시 매니저에 등록한 다음에 꺼내쓰는 방식
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()    // static method
                .withCache("myFirstCache",
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.heap(10))
                )
                .build();

        cacheManager.init();

        Cache<Long, String> myFirstCache = cacheManager.getCache("myFirstCache", Long.class, String.class);

        // 캐시 매니저를 통해 바로 캐시만들어서 사용하는 방식
        Cache<Long, String> mySecondCache = cacheManager.createCache("mySecondCache",
                CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.heap(10))
        );

        mySecondCache.put(1L, "this is cached value!!");
        String value = mySecondCache.get(1L);
        System.out.println(value);

        cacheManager.removeCache("myFirstCache");

        cacheManager.close();

    }
}
