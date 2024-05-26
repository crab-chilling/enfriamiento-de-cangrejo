package cpe.asi.cardforge.security;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

public class UserCache {

    private CacheManager cacheManager;
    public UserCache(){}

    public void createCache(int size){
        cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("userCache", CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, String.class,
                        ResourcePoolsBuilder.heap(size))
                        .build())
                .build(true);
    }

    public Cache<String, String> getCache() {
        return cacheManager.getCache("userCache", String.class, String.class);
    }
}
