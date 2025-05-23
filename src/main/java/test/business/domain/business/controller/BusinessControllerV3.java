package test.business.domain.business.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import test.business.domain.business.config.PerLogger;
import test.business.domain.business.dto.BusinessRes;
import test.business.domain.business.dto.KeywordRes;
import test.business.domain.business.service.BusinessService;
import test.business.domain.business.service.BusinessServiceV3Impl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v3/businesses")
public class BusinessControllerV3 {
    private final BusinessServiceV3Impl businessServiceV3Impl;
    private final PerLogger perLogger;

    @GetMapping
    public ResponseEntity<Page<BusinessRes>> getBusinessesWithRedisCache(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        perLogger.start();
        Page<BusinessRes> test = businessServiceV3Impl.getBusinesses(page, size, keyword);
        perLogger.endAndLog("REDIS");
        return ResponseEntity.ok(test);
    }
    @GetMapping("/popularKeyword")
    public ResponseEntity<KeywordRes> getBusinessesWithPopularKeyword(){
        return ResponseEntity.ok(businessServiceV3Impl.getPopularKeywords());

    }
}
