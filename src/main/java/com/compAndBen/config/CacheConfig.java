package com.compAndBen.config;


	import com.example.demo.utils.GetUserDataKeyGeneration;
	import com.example.demo.utils.RequestKeyGenerator;
	import com.example.demo.utils.UpdateColumnsKeyGenerator;
	import com.example.demo.utils.UploadUserFileKey;
	import com.google.common.cache.CacheBuilder;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.cache.Cache;
	import org.springframework.cache.CacheManager;
	import org.springframework.cache.annotation.CachingConfigurerSupport;
	import org.springframework.cache.annotation.EnableCaching;
	import org.springframework.cache.concurrent.ConcurrentMapCache;
	import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
	import org.springframework.cache.interceptor.KeyGenerator;
	import org.springframework.context.annotation.Bean;
	import org.springframework.context.annotation.Configuration;
	import org.springframework.context.annotation.PropertySource;
	import org.springframework.core.env.Environment;

	import java.util.Arrays;
	import java.util.concurrent.TimeUnit;

	@EnableCaching
	@Configuration
	@PropertySource("classpath:security.properties")
	public class CacheConfig extends CachingConfigurerSupport {

	    @Autowired
	    private Environment env;

	    @Bean
	    @Override
	    public CacheManager cacheManager(){

	        ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager() {

	            @Override
	            protected Cache createConcurrentMapCache(final String name) {
	                return new ConcurrentMapCache(name, CacheBuilder.newBuilder()
	                        .expireAfterWrite(Integer.parseInt(env.getProperty("cache.expiry.time.inMinute")), TimeUnit.MINUTES)
	                        .maximumSize(Integer.parseInt(env.getProperty("cache.maximum.entries"))).build().asMap(), false);
	            }
	        };

	        cacheManager.setCacheNames(Arrays.asList("UserData", "UserListing", "FileList", "FileData"));
	        return cacheManager;


	    }
	    @Bean
	    public KeyGenerator keyGenerator() {
	        return new RequestKeyGenerator();
	    }
	    @Bean
	    public KeyGenerator GetUserDataKey() {
	        return new GetUserDataKeyGeneration();
	    }

	    @Bean
	    public KeyGenerator UserFileKey() {
	        return new UploadUserFileKey();
	    }

	    @Bean
	    public KeyGenerator updateColumnKey() {
	        return new UpdateColumnsKeyGenerator();
	    }
	
}
