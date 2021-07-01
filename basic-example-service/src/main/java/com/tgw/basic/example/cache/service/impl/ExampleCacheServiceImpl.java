package com.tgw.basic.example.cache.service.impl;

import com.tgw.basic.example.cache.service.ExampleCacheService;
import com.tgw.basic.framework.service.impl.BaseServiceImpl;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service("exampleCacheService")
@CacheConfig(cacheNames = "defaultCacheName")// 缓存注解公共配置项，此类中方法上的注解默认会使用CacheConfig配置的属性值
public class ExampleCacheServiceImpl extends BaseServiceImpl implements ExampleCacheService {
    private static final Logger LOG = LoggerFactory.getLogger(ExampleCacheServiceImpl.class);

    /**
     * @param param
     * @return
     */
    @Cacheable
    public String cacheable(String param){
        LOG.info("invoke cacheable, param: "+param);
        return param+" value";
    }

    /**
     * 单个参数作为key。key格式：cacheable2ParamAsKey::param1
     * @param param1
     * @param param2
     * @return
     */
    @Cacheable(cacheNames = "cacheable1ParamAsKey",key = "#param1")
    public String cacheable1ParamAsKey(String param1,String param2) {
        LOG.info("invoke cacheable1ParamAsKey, param1: "+param1+" ,param2: "+param2);
        return param1+" , "+param2;
    }


    /**
     * 多个参数作为key。key格式：cacheable1ParamAsKey::SimpleKey [param1,param2]
     * @param param1
     * @param param2
     * @return
     */
    @Cacheable(cacheNames = "cacheable2ParamAsKey")
    public String cacheable2ParamAsKey(String param1,String param2) {
        LOG.info("invoke cacheable2ParamAsKey, param1: "+param1+" ,param2: "+param2);
        return param1+" , "+param2;
    }

    /**
     * 线程同步控制。
     * @param param
     * @return
     */
    @Cacheable(value = "cacheableSync",sync = true)
    public String cacheableSync(String param){
        LOG.info("invoke cacheableSync, param: "+param);
        return param+" value";
    }

    /**
     * 参数长度大于等于2的才会被缓存
     * @param param
     * @return
     */
    @Cacheable(value = "cacheableCondition",condition="#param.length() >= 2")
    public String cacheableCondition(String param){
        LOG.info("invoke cacheableCondition, param: "+param);
        return param+" value";
    }

    /**
     * 使用unless属性示例
     * @param param
     * @return
     */
    @Cacheable(value = "cacheableUnless",condition="#param.length() >= 2",unless="#result.length() >= 20")
    public String cacheableUnless(String param){
        LOG.info("invoke cacheableUnless, param: "+param);
        return param+" value";
    }

    /**
     * 每次都会更新缓存。另注意同一个方法上不要同时使用CachePut和Cacheable注解，两个注解的作用有些相矛盾。
     * @param param
     * @return
     */
    @CachePut("cachePut")
    public String cachePut(String param){
        String str = param+" , "+ RandomStringUtils.randomAlphanumeric(5);
        LOG.info("invoke cachePut, param: "+param+" ,return value: "+str);
        return str;
    }

    /**
     * 删除指定缓存。allEntries代表是否删除指定的全部缓存数据
     * @param param
     */
    @CacheEvict(cacheNames = "cacheable1ParamAsKey",allEntries = true,beforeInvocation = false)
    public void cacheEvict(String param){
        LOG.info("invoke cacheEvict, param: "+param);
    }

    /**
     * 可以组合多个其它缓存注解
     * @param param
     * @return
     */
    @Caching(evict = { @CacheEvict(cacheNames="cacheable1ParamAsKey",allEntries = true), @CacheEvict(cacheNames="cacheable2ParamAsKey",allEntries = true) }, put = { @CachePut("cachingCachePut")},cacheable = {@Cacheable("cachingCacheable")})
    public String caching(String param){
        String str = param+" , "+ RandomStringUtils.randomAlphanumeric(5);
        LOG.info("invoke caching, param: "+param+" ,return value: "+str);
        return str;
    }
}
