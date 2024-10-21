package com.ubgd.chemhandbook.model;

import com.ubgd.chemhandbook.model.enums.AggregationState;
import com.ubgd.chemhandbook.model.enums.DensityAir;
import com.ubgd.chemhandbook.model.enums.DensityWater;
import com.ubgd.chemhandbook.model.enums.GeneralDanger;
import com.ubgd.chemhandbook.model.enums.HealthDanger;
import com.ubgd.chemhandbook.model.enums.Solubility;
import com.ubgd.chemhandbook.model.enums.WaterDanger;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SubstanceProperties {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private AggregationState aggregationState;
    @Enumerated(EnumType.STRING)
    private DensityAir densityAir;
    @Enumerated(EnumType.STRING)
    private DensityWater densityWater;
    @Enumerated(EnumType.STRING)
    private Solubility solubility;
    @Enumerated(EnumType.STRING)
    private GeneralDanger generalDanger;
    @Enumerated(EnumType.STRING)
    private WaterDanger waterDanger;
    @Enumerated(EnumType.STRING)
    private HealthDanger healthDanger;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubstanceProperties)) return false;
        SubstanceProperties that = (SubstanceProperties) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
