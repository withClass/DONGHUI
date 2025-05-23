package test.business.domain.business.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import test.business.domain.business.entity.Business;

public interface BusinessRepository extends JpaRepository<Business, Long> {
    @Query("""
        select b from Business b
        where (:keyword is null or b.businessName like %:keyword%) 
                """ )
    Page<Business> searchBusiness(String keyword, Pageable pageable);
}
