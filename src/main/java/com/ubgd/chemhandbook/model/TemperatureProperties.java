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

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TemperatureProperties {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double boilingPoint;
    private double freezePoint;
    private double meltingPoint;
    private double flashPoint;
}
