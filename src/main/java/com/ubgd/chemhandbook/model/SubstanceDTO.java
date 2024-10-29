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
    private DangerSquare dangerSquare;
    private String haz;
    private String imdg;
    private Integer lethal;
    private Double limitConcentration;
    private String container;
}
