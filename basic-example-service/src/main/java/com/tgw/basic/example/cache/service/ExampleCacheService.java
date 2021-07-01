package com.tgw.basic.example.cache.service;


import com.tgw.basic.framework.service.BaseService;

public interface ExampleCacheService extends BaseService {

    public String cacheable(String param);

    public String cacheable1ParamAsKey(String param1,String param2);

    public String cacheable2ParamAsKey(String param1,String param2);

    public String cacheableSync(String param);

    public String cacheableCondition(String param);

    public String cacheableUnless(String param);

    public String cachePut(String param);

    public void cacheEvict(String param);

    public String caching(String param);
}
