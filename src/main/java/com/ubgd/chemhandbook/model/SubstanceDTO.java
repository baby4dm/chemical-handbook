package com.ubgd.chemhandbook.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Builder
@Getter
@Setter
public class SubstanceDTO {
    private int oonNumber;
    private int dangerousNumber;
    private String formula;
    private String name;
    private String description;
    private String aggregationState;
    private String densityAir;
    private String densityWater;
    private String solubility;
    private String generalDanger;
    private String waterDanger;
    private String healthDanger;
    private Double molecularWeight;
    private String flammabilityClass;
    private TemperatureProperties temperatureProperties;
    private DangerSquare dangerSquare;
    private String haz;
    private String imdg;
    private String container;
    private String respirationRecommendation;
    private String skinDefenseRecommendation;
    private HealthInvolve healthInvolve;
    private FirstAid firstAid;
}
