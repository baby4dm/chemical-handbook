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
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
    public List<SubstanceDTO> findAll() {
        List<Substance> substances = substanceRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
        return substances.stream()
                .map(this::substanceToSubstanceDto)
                .toList();
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

    public Page<SubstanceDTO> search(
            String search,
            String dangerousNumber,
            String aggregationState,
            String densityWater,
            String densityAir,
            String solubility,
            String generalDanger,
            String waterDanger,
            Pageable pageable
    ) {
        Specification<Substance> spec = Specification.where(null);

        // Пошук по основним полям
        if (search != null && !search.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.or(
                            cb.like(cb.lower(root.get("name")), "%" + search.toLowerCase() + "%"),
                            cb.like(cb.lower(root.get("formula")), "%" + search.toLowerCase() + "%"),
                            cb.like(root.get("oonNumber").as(String.class), "%" + search + "%"),
                            cb.like(root.get("dangerousNumber").as(String.class), "%" + search + "%")
                    )
            );
        }

        // Фільтр по номеру небезпеки
        if (dangerousNumber != null && !dangerousNumber.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(root.get("dangerousNumber").as(String.class), "%" + dangerousNumber + "%")
            );
        }

        // Використовуємо join для фільтрів по властивостях
        spec = spec.and((root, query, cb) -> {
            Join<Object, Object> propertiesJoin = root.join("substanceProperties", JoinType.LEFT);

            Predicate predicate = cb.conjunction(); // Початковий "порожній" фільтр

            if (aggregationState != null && !aggregationState.isEmpty()) {
                predicate = cb.and(predicate, cb.like(cb.lower(propertiesJoin.get("aggregationState")), "%" + aggregationState.toLowerCase() + "%"));
            }

            if (densityWater != null && !densityWater.isEmpty()) {
                predicate = cb.and(predicate, cb.like(cb.lower(propertiesJoin.get("densityWater").as(String.class)), "%" + densityWater.toLowerCase() + "%"));
            }

            if (densityAir != null && !densityAir.isEmpty()) {
                predicate = cb.and(predicate, cb.like(cb.lower(propertiesJoin.get("densityAir").as(String.class)), "%" + densityAir.toLowerCase() + "%"));
            }

            if (solubility != null && !solubility.isEmpty()) {
                predicate = cb.and(predicate, cb.like(cb.lower(propertiesJoin.get("solubility")), "%" + solubility.toLowerCase() + "%"));
            }

            if (generalDanger != null && !generalDanger.isEmpty()) {
                predicate = cb.and(predicate, cb.like(cb.lower(propertiesJoin.get("generalDanger")), "%" + generalDanger.toLowerCase() + "%"));
            }

            if (waterDanger != null && !waterDanger.isEmpty()) {
                predicate = cb.and(predicate, cb.like(cb.lower(propertiesJoin.get("waterDanger")), "%" + waterDanger.toLowerCase() + "%"));
            }

            return predicate;
        });

        // Виконуємо запит з усіма специфікаціями та пагінацією
        Page<Substance> substancesPage = substanceRepo.findAll(spec, pageable);

        // Конвертуємо результати в DTO
        return substancesPage.map(this::substanceToSubstanceDto);
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
                .container(substance.getContainer())
                .firstAid(substance.getFirstAid())
                .healthInvolve(substance.getHealthInvolve())
                .respirationRecommendation(substance.getRespirationRecommendation())
                .skinDefenseRecommendation(substance.getSkinDefenseRecommendation())
                .flammabilityClass(substance.getSubstanceProperties().getFlammabilityClass().getDescription())
                .molecularWeight(substance.getSubstanceProperties().getMolecularWeight())
                .temperatureProperties(substance.getSubstanceProperties().getTemperatureProperties())
                .build();
    }

}
