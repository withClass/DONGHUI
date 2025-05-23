package test.business.domain.business.service;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import test.business.domain.business.config.CachedPage;
import test.business.domain.business.dto.BusinessRes;
import test.business.domain.business.dto.KeywordRes;
import test.business.domain.business.entity.Business;
import test.business.domain.business.repository.BusinessRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class BusinessServiceV3Impl implements BusinessService {
    private final BusinessRepository businessRepository;
    @Autowired
    private CacheManager redisCacheManager;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public Page<BusinessRes> getBusinesses(int page, int size, String keyword) {
        String searchCacheKey = keyword + ":" + (page) + ":" + size;

        Cache businessSearchCache = redisCacheManager.getCache("businessSearchCache");

        if (businessSearchCache != null && keyword != null && !keyword.isBlank()) {
            Cache.ValueWrapper cachedResult = businessSearchCache.get(searchCacheKey);
            if (cachedResult != null && cachedResult.get() instanceof CachedPage) {
                CachedPage<?> cached = (CachedPage<?>) cachedResult.get();

                // 캐시 hit 시에도 인기 키워드 등록 호출
//                registerPopularKeyword(keyword);

                return new PageImpl<>(
                        (List<BusinessRes>) cached.getContent(),
                        PageRequest.of(cached.getPage(), cached.getSize()),
                        cached.getTotalElements()
                );
            }
        }
        Pageable pageable = PageRequest.of(page -1 , size);
        Page<Business> businesses =
                businessRepository.searchBusiness(keyword,pageable);

        Page<BusinessRes> result = businesses.map(BusinessRes::from);
        CachedPage<BusinessRes> cachedPage = new CachedPage<>(
                result.getContent(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements()
        );

        if (businessSearchCache != null && keyword != null && !keyword.isBlank()) {
            businessSearchCache.put(searchCacheKey, cachedPage);
        }
        // 인기 키워드 등록 호출
//        registerPopularKeyword(keyword);
        return result;
    }



    public void registerPopularKeyword(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return;
        }
        keyword = keyword.replaceAll("^\"|\"$", "");

        String keywordCountKey = "keywordCount:" + keyword;

        Long count = redisTemplate.opsForValue().increment(keywordCountKey);

        redisTemplate.opsForZSet().add("popular:keywords", keyword, count.doubleValue());

        log.info("Keyword '{}' count updated to {}", keyword, count);

    }

    public KeywordRes getPopularKeywords() {
        Set<Object> popularKeywords = redisTemplate.opsForZSet()
                .reverseRange("popular:keywords", 0, 9);

        List<String> keywordList = popularKeywords.stream()
                .map(Object::toString)
                .collect(Collectors.toList());

        return new KeywordRes(keywordList);
    }


}
