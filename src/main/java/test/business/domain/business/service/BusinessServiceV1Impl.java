package test.business.domain.business.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import test.business.domain.business.config.PerLogger;
import test.business.domain.business.dto.BusinessRes;
import test.business.domain.business.entity.Business;
import test.business.domain.business.repository.BusinessRepository;
@Log4j2
@Service
@RequiredArgsConstructor
public class BusinessServiceV1Impl implements BusinessService{
    private final BusinessRepository businessRepository;
    @Override
    public Page<BusinessRes> getBusinesses(int page, int size, String keyword) {
        Pageable pageable = PageRequest.of(page -1 , size);

        Page<Business> businesses =
                businessRepository.searchBusiness(keyword,pageable);
        return businesses.map(BusinessRes::from);
    }
}
