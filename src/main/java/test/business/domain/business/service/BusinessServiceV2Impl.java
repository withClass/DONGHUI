package test.business.domain.business.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import test.business.domain.business.config.CachedPage;
import test.business.domain.business.config.PerLogger;
import test.business.domain.business.dto.BusinessRes;
import test.business.domain.business.entity.Business;
import test.business.domain.business.repository.BusinessRepository;

@Service
@Log4j2
@RequiredArgsConstructor
public class BusinessServiceV2Impl implements BusinessService {
    private final BusinessRepository businessRepository;

    @Cacheable(
            value = "businessSearchCacheInMemory",
            key = "#keyword + ':' + #page + ':' + #size",
            condition = "#keyword != null and !#keyword.isBlank()",
            cacheManager = "caffeineCacheManager"
    )
    @Override
    public Page<BusinessRes> getBusinesses(int page, int size, String keyword) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Business> businesses = businessRepository.searchBusiness(keyword, pageable);
        return businesses.map(BusinessRes::from);
    }
}
