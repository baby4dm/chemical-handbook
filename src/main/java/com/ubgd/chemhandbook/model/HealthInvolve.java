package com.ubgd.chemhandbook.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class HealthInvolve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer lethal;
    private Double limitConcentration;
    private String involveWays;
    private String symptoms;
    private String organImpacts;
}
