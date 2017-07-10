package com.neusoft.hs.platform.cache;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class EhCacheCacheTestService {

	@Cacheable(value = "testCache", key = "#testId")
	public CacheVO testEhcache(String testId) {

		CacheVO cacheVO = new CacheVO();

		cacheVO.setId("test");
		cacheVO.setName("测试");

		return cacheVO;
	}

	@CacheEvict(value = "testCache", key = "#testId")
	public void testDeleteEhcache(String testId) {

	}

}