package test.business.domain.business.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String businessName;

    @Column
    private LocalDateTime createdAt;

    @Column
    private String industryName;

    @Column
    private String postCode;

    @Column
    private String registrationNumber;

    @Column
    private String roadAddress;

    @Column
    private LocalDateTime updatedAt;
}
