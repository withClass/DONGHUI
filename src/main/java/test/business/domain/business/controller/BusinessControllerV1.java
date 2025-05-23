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
import test.business.domain.business.service.BusinessServiceV1Impl;

@RestController
@RequestMapping("/api/v1/businesses")
@RequiredArgsConstructor
public class BusinessControllerV1 {
    private final BusinessServiceV1Impl businessServiceV1Impl;
    private final PerLogger perLogger;

    @GetMapping
    public ResponseEntity<Page<BusinessRes>> getBusinesses(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        perLogger.start();
        Page<BusinessRes> test = businessServiceV1Impl.getBusinesses(page,size,keyword);
        perLogger.endAndLog("NOCACHE");
        return ResponseEntity.ok(test);
    }
}
