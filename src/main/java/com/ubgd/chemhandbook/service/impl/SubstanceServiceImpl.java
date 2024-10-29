package com.ubgd.chemhandbook.service.impl;

import com.ubgd.chemhandbook.client.ImageClient;
import com.ubgd.chemhandbook.model.Substance;
import com.ubgd.chemhandbook.model.SubstanceDTO;
import com.ubgd.chemhandbook.model.SubstanceProperties;
import com.ubgd.chemhandbook.model.enums.AggregationState;
import com.ubgd.chemhandbook.model.enums.DensityAir;
import com.ubgd.chemhandbook.model.enums.DensityWater;
import com.ubgd.chemhandbook.model.enums.GeneralDanger;
import com.ubgd.chemhandbook.model.enums.HealthDanger;
import com.ubgd.chemhandbook.model.enums.Solubility;
import com.ubgd.chemhandbook.model.enums.WaterDanger;
import com.ubgd.chemhandbook.repo.SubstanceRepo;
import com.ubgd.chemhandbook.service.SubstanceService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubstanceServiceImpl implements SubstanceService {
    private final SubstanceRepo substanceRepo;
    private final ImageClient imageClient;

    @Override
    public SubstanceDTO findByName(String name) {
        Substance substance = substanceRepo.findByName(name).orElseThrow(
                () -> new EntityNotFoundException(String.format("Речовини з назвою %s не знайдено", name)));
        return substanceToSubstanceDto(substance);
    }

    @Override
    public SubstanceDTO findById(Integer id) {
        Substance substance = substanceRepo.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        String.format("Речовини зі значенням %s не знайдено ", id)));
        return substanceToSubstanceDto(substance);
    }

    @Override
    public SubstanceDTO findByImage(MultipartFile file) {
        String textFromImage = imageClient.sendImage(file);
        Integer result = imageClient.parseResponseToIntegers(textFromImage);
        return findById(result);
    }

    @Override
    public SubstanceDTO findByHazCode(String hazCode) {
        Substance substance = substanceRepo.findByHaz(hazCode).orElseThrow(
                () -> new EntityNotFoundException(
                        String.format("Речовини з HAZ кодом %s не знайдено ", hazCode)));
        return substanceToSubstanceDto(substance);
    }

    @Override
    public SubstanceDTO findByImdgCode(String imdgCode) {
        Substance substance = substanceRepo.findByImdg(imdgCode).orElseThrow(
                () -> new EntityNotFoundException(
                        String.format("Речовини з IMDG кодом %s не знайдено ", imdgCode)));
        return substanceToSubstanceDto(substance);
    }

    @Override
    public SubstanceDTO findByFormula(String formula) {
        Substance substance = substanceRepo.findByFormula(formula).orElseThrow(
                () -> new EntityNotFoundException(
                        String.format("Речовини з HAZ кодом %s не знайдено ", formula)));
        return substanceToSubstanceDto(substance);
    }

    @Override
    public List<Integer> getAllSubstancesId() {
       return substanceRepo.getAllIds();
    }


    private SubstanceDTO substanceToSubstanceDto(Substance substance) {
        SubstanceProperties properties = substance.getSubstanceProperties();

        return SubstanceDTO.builder()
                .densityWater(properties.getDensityWater().map(DensityWater::getDisplayName).orElse(null))
                .description(substance.getDescription())
                .generalDanger(properties.getGeneralDanger().map(GeneralDanger::getDisplayName).orElse(null))
                .densityAir(properties.getDensityAir().map(DensityAir::getDisplayName).orElse(null))
                .healthDanger(properties.getHealthDanger().map(HealthDanger::getDisplayName).orElse(null))
                .aggregationState(properties.getAggregationState().map(AggregationState::getDisplayName).orElse(null))
                .dangerSquare(substance.getDangerSquare())
                .formula(substance.getFormula())
                .dangerousNumber(substance.getDangerousNumber())
                .solubility(properties.getSolubility().map(Solubility::getDisplayName).orElse(null))
                .oonNumber(substance.getOonNumber())
                .waterDanger(properties.getWaterDanger().map(WaterDanger::getDisplayName).orElse(null))
                .name(substance.getName())
                .haz(substance.getHaz())
                .imdg(substance.getImdg())
                .lethal(substance.getLethal())
                .limitConcentration(substance.getLimitConcentration())
                .container(substance.getContainer())
                .build();
    }

}
