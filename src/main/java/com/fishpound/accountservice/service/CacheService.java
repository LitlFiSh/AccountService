package com.fishpound.accountservice.service;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Service;

/**
 * @author Litl_FiSh
 * @Date 2021/2/5 14:25
 */
@Service
public class CacheService {
    @Autowired
    EhCacheCacheManager ehCacheCacheManager;

    private Cache getCache(String cacheName){
        CacheManager cacheManager = ehCacheCacheManager.getCacheManager();
        return cacheManager.getCache(cacheName);
    }

    public void setCacheValue(String cacheName, String key, Object value){
        this.getCache(cacheName).put(new Element(key, value));
    }

    public Element getCacheElement(String cacheName, String key){
        return this.getCache(cacheName).get(key);
    }

    public void invalidateCache(String cacheName, String key){
        this.getCache(cacheName).remove(key);
    }
}
