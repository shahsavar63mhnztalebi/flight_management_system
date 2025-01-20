package ir.barook.flightSearchService.config;

import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;

public class CustomCacheErrorHandler implements CacheErrorHandler {

    @Override
    public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
        System.out.println("Error getting from cache" + exception);
    }

    @Override
    public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
        System.out.println("Error getting from cache" + exception);
    }

    @Override
    public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
        System.out.println("Error getting from cache" + exception);
    }

    @Override
    public void handleCacheClearError(RuntimeException exception, Cache cache) {
        System.out.println("Error getting from cache" + exception);
    }
}