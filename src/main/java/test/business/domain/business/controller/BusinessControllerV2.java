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
import test.business.domain.business.service.BusinessServiceV2Impl;
import test.business.domain.business.service.BusinessServiceV3Impl;

@RestController
@RequestMapping("/api/v2/businesses")
@RequiredArgsConstructor
public class BusinessControllerV2 {
    private final BusinessServiceV2Impl businessServiceV2Impl;
    private final PerLogger perLogger;
    @GetMapping
    public ResponseEntity<Page<BusinessRes>> getBusinessesWithInMemoryCache(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        perLogger.start();
        Page<BusinessRes> test = businessServiceV2Impl.getBusinesses(page,size,keyword);
        perLogger.endAndLog("HIT");
        return ResponseEntity.ok(test);
    }

}
