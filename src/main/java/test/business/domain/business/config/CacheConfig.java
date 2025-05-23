package test.business.domain.business.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {
    // ConcurrentMap + Caffeine 캐시 많이 씀

    @Bean
    public CacheManager caffeineCacheManager() {
        // 심플 써야 세팅 편함
        SimpleCacheManager cacheManager = new SimpleCacheManager();

        // 키워드를 카운팅하는 캐시
        CaffeineCache keywordCountMap = new CaffeineCache(
                "keywordCountMap",
                Caffeine.newBuilder()
                        .maximumSize(10000)
                        .build()
        );

        // 인기 검색어를 조회하는 캐시
        CaffeineCache popularKeywords = new CaffeineCache(
                "popularKeywords",
                Caffeine.newBuilder()
                        .maximumSize(10) // days 값 기준으로 최대 10개
                        .expireAfterWrite(5, TimeUnit.MINUTES) // TTL 5분
                        .build()
        );

        // 현재 캐시에 저장된 키워드 목록
        CaffeineCache keywordKeySet = new CaffeineCache(
                "keywordKeySet",
                Caffeine.newBuilder()
                        .maximumSize(1)
                        .build()
        );


        CaffeineCache businessSearchCacheInMemory = new CaffeineCache(
                "businessSearchCacheInMemory",
                Caffeine.newBuilder()
                        .maximumSize(10000)
                        .expireAfterWrite(30, TimeUnit.MINUTES) // 예: 30분 TTL
                        .build()
        );



        cacheManager.setCaches(Arrays.asList(keywordCountMap, popularKeywords, keywordKeySet, businessSearchCacheInMemory));
        return cacheManager;
    }
}
