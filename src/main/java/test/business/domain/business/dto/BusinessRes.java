package test.business.domain.business.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import test.business.domain.business.entity.Business;

import java.io.Serial;
import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BusinessRes implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    private String businessName;

    public static BusinessRes from(Business entity) {
        return new BusinessRes(entity.getId(), entity.getBusinessName());
    }
}
