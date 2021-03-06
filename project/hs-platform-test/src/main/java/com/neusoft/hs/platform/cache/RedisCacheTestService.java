package com.neusoft.hs.platform.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.neusoft.hs.platform.log.LogUtil;

@Service
public class RedisCacheTestService {

	@Autowired
	private StringRedisTemplate redisTemplate;

	public void testRedis() {
		
		redisTemplate.delete("test");
		
		redisTemplate.opsForValue().append("test", "test");
		
		LogUtil.log(EhCacheCacheTestService.class, "add test to redis cache");
	}
}
