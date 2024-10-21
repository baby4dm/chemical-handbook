package com.ubgd.chemhandbook.service.impl;

import com.ubgd.chemhandbook.client.ImageClient;
import com.ubgd.chemhandbook.model.Substance;
import com.ubgd.chemhandbook.model.SubstanceDTO;
import com.ubgd.chemhandbook.repo.SubstanceRepo;
import com.ubgd.chemhandbook.service.SubstanceService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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


    private SubstanceDTO substanceToSubstanceDto(Substance substance) {
        return SubstanceDTO.builder()
                .densityWater(substance.getSubstanceProperties().getDensityWater().getDisplayName())
                .description(substance.getDescription())
                .generalDanger(substance.getSubstanceProperties().getGeneralDanger().getDisplayName())
                .densityAir(substance.getSubstanceProperties().getDensityAir().getDisplayName())
                .healthDanger(substance.getSubstanceProperties().getHealthDanger().getDisplayName())
                .aggregationState(substance.getSubstanceProperties().getAggregationState().getDisplayName())
                .dangerSquare(substance.getDangerSquare())
                .formula(substance.getFormula())
                .dangerousNumber(substance.getDangerousNumber())
                .solubility(substance.getSubstanceProperties().getSolubility().getDisplayName())
                .oonNumber(substance.getOonNumber())
                .waterDanger(substance.getSubstanceProperties().getWaterDanger().getDisplayName())
                .name(substance.getName())
                .haz(substance.getHaz())
                .imdg(substance.getImdg())
                .lethal(substance.getLethal())
                .build();
    }
}
