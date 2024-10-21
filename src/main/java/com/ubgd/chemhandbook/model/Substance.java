package com.ubgd.chemhandbook.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Entity
@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Substance {
    @Id
    private int oonNumber;
    private int dangerousNumber;
    private String formula;
    private String name;
    private String description;
    @OneToOne
    @JoinColumn(name = "substance_properites_id")
    private SubstanceProperties substanceProperties;
    @OneToOne
    @JoinColumn(name = "danger_square_id")
    private DangerSquare dangerSquare;
    private String imdg;
    private String haz;
    private Integer lethal;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Substance substance = (Substance) o;
        return dangerousNumber == substance.dangerousNumber &&
                oonNumber == substance.oonNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dangerousNumber, oonNumber);
    }
}
