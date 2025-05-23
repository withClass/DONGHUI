package test.business.domain.business.service;

import org.springframework.data.domain.Page;
import test.business.domain.business.dto.BusinessRes;

public interface BusinessService {
    Page<BusinessRes> getBusinesses(int page, int size, String keyword);

}
