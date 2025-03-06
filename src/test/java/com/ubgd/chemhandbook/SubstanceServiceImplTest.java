package com.ubgd.chemhandbook;

import com.ubgd.chemhandbook.client.ImageClient;
import com.ubgd.chemhandbook.model.FirstAid;
import com.ubgd.chemhandbook.model.HealthInvolve;
import com.ubgd.chemhandbook.model.Substance;
import com.ubgd.chemhandbook.model.SubstanceDTO;
import com.ubgd.chemhandbook.model.SubstanceProperties;
import com.ubgd.chemhandbook.model.TemperatureProperties;
import com.ubgd.chemhandbook.model.enums.*;
import com.ubgd.chemhandbook.repo.SubstanceRepo;
import com.ubgd.chemhandbook.service.impl.SubstanceServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubstanceServiceImplTest {

    @Mock
    private SubstanceRepo substanceRepo;

    @Mock
    private ImageClient imageClient;

    @InjectMocks
    private SubstanceServiceImpl substanceService;

    private Substance testSubstance;
    private SubstanceProperties testProperties;

    @BeforeEach
    void setUp() {
        testProperties = new SubstanceProperties();
        testProperties.setAggregationState(AggregationState.SOLID);
        testProperties.setDensityWater(DensityWater.HEAVIER);
        testProperties.setDensityAir(DensityAir.HEAVIER);
        testProperties.setSolubility(Solubility.SOLUBLE);
        testProperties.setGeneralDanger(GeneralDanger.FLAMMABLE);
        testProperties.setWaterDanger(WaterDanger.FORBIDDEN);
        testProperties.setHealthDanger(HealthDanger.HARMFUL);
        testProperties.setFlammabilityClass(FlammabilityClass.FIRST);
        testProperties.setMolecularWeight(100.0);
        testProperties.setTemperatureProperties(new TemperatureProperties());

        testSubstance = new Substance();
        testSubstance.setOonNumber(1);
        testSubstance.setName("Test Substance");
        testSubstance.setFormula("H2O");
        testSubstance.setHaz("HAZ123");
        testSubstance.setImdg("IMDG123");
        testSubstance.setOonNumber(1234);
        testSubstance.setDangerousNumber(5678);
        testSubstance.setSubstanceProperties(testProperties);
        testSubstance.setContainer("Test container");
        testSubstance.setFirstAid(new FirstAid());
        testSubstance.setHealthInvolve(new HealthInvolve());
        testSubstance.setRespirationRecommendation("Test respiration");
        testSubstance.setSkinDefenseRecommendation("Test skin defense");
    }

    @Test
    void findByName_WhenExists_ShouldReturnSubstanceDTO() {
        // Arrange
        when(substanceRepo.findByName("Test Substance")).thenReturn(Optional.of(testSubstance));

        // Act
        SubstanceDTO result = substanceService.findByName("Test Substance");

        // Assert
        assertNotNull(result);
        assertEquals("Test Substance", result.getName());
        assertEquals("H2O", result.getFormula());
        verify(substanceRepo).findByName("Test Substance");
    }

    @Test
    void findByName_WhenNotExists_ShouldThrowEntityNotFoundException() {
        // Arrange
        when(substanceRepo.findByName("Nonexistent")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class,
                () -> substanceService.findByName("Nonexistent"));
    }

    @Test
    void findById_WhenExists_ShouldReturnSubstanceDTO() {
        // Arrange
        when(substanceRepo.findById(1)).thenReturn(Optional.of(testSubstance));

        // Act
        SubstanceDTO result = substanceService.findById(1);

        // Assert
        assertNotNull(result);
        assertEquals("Test Substance", result.getName());
        verify(substanceRepo).findById(1);
    }

    @Test
    void findByImage_ShouldReturnSubstanceDTO() {
        // Arrange
        MultipartFile mockFile = mock(MultipartFile.class);
        when(imageClient.sendImage(mockFile)).thenReturn("some text");
        when(imageClient.parseResponseToIntegers("some text")).thenReturn(1);
        when(substanceRepo.findById(1)).thenReturn(Optional.of(testSubstance));

        // Act
        SubstanceDTO result = substanceService.findByImage(mockFile);

        // Assert
        assertNotNull(result);
        assertEquals("Test Substance", result.getName());
        verify(imageClient).sendImage(mockFile);
        verify(imageClient).parseResponseToIntegers("some text");
        verify(substanceRepo).findById(1);
    }

    @Test
    void findByHazCode_WhenExists_ShouldReturnSubstanceDTO() {
        // Arrange
        when(substanceRepo.findByHaz("HAZ123")).thenReturn(Optional.of(testSubstance));

        // Act
        SubstanceDTO result = substanceService.findByHazCode("HAZ123");

        // Assert
        assertNotNull(result);
        assertEquals("Test Substance", result.getName());
        assertEquals("HAZ123", result.getHaz());
        verify(substanceRepo).findByHaz("HAZ123");
    }

    @Test
    void findAll_ShouldReturnListOfSubstanceDTOs() {
        // Arrange
        List<Substance> substances = Arrays.asList(testSubstance);
        when(substanceRepo.findAll(any(Sort.class))).thenReturn(substances);

        // Act
        List<SubstanceDTO> results = substanceService.findAll();

        // Assert
        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals("Test Substance", results.get(0).getName());
        verify(substanceRepo).findAll(any(Sort.class));
    }

    @Test
    void search_ShouldReturnPageOfSubstanceDTOs() {
        // Arrange
        Pageable pageable = mock(Pageable.class);
        Page<Substance> substancePage = new PageImpl<>(List.of(testSubstance));
        when(substanceRepo.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(substancePage);

        // Act
        Page<SubstanceDTO> result = substanceService.search(
                "test", "5678", "SOLID", "HEAVIER",
                "HEAVIER", "INSOLUBLE", "FIRE", "LOW", pageable
        );

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Test Substance", result.getContent().get(0).getName());
        verify(substanceRepo).findAll(any(Specification.class), any(Pageable.class));
    }

    @Test
    void getAllSubstancesId_ShouldReturnListOfIds() {
        // Arrange
        List<Integer> expectedIds = Arrays.asList(1, 2, 3);
        when(substanceRepo.getAllIds()).thenReturn(expectedIds);

        // Act
        List<Integer> result = substanceService.getAllSubstancesId();

        // Assert
        assertNotNull(result);
        assertEquals(expectedIds, result);
        verify(substanceRepo).getAllIds();
    }

    @Test
    void findByFormula_WhenExists_ShouldReturnSubstanceDTO() {
        // Arrange
        when(substanceRepo.findByFormula("H2O")).thenReturn(Optional.of(testSubstance));

        // Act
        SubstanceDTO result = substanceService.findByFormula("H2O");

        // Assert
        assertNotNull(result);
        assertEquals("H2O", result.getFormula());
        verify(substanceRepo).findByFormula("H2O");
    }

    @Test
    void findByImdgCode_WhenExists_ShouldReturnSubstanceDTO() {
        // Arrange
        when(substanceRepo.findByImdg("IMDG123")).thenReturn(Optional.of(testSubstance));

        // Act
        SubstanceDTO result = substanceService.findByImdgCode("IMDG123");

        // Assert
        assertNotNull(result);
        assertEquals("IMDG123", result.getImdg());
        verify(substanceRepo).findByImdg("IMDG123");
    }
}