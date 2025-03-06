package com.ubgd.chemhandbook;

import com.ubgd.chemhandbook.controller.SubstanceController;
import com.ubgd.chemhandbook.model.SubstanceDTO;
import com.ubgd.chemhandbook.service.SubstanceService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubstanceControllerTest {

    @Mock
    private SubstanceService substanceService;

    @InjectMocks
    private SubstanceController substanceController;

    private SubstanceDTO testSubstanceDTO;

    @BeforeEach
    void setUp() {
        testSubstanceDTO = SubstanceDTO.builder()
                .name("Test Substance")
                .formula("H2O")
                .haz("HAZ123")
                .imdg("IMDG123")
                .oonNumber(1234)
                .dangerousNumber(5678)
                .build();
    }

    @Test
    void getSubstanceByName_Success() {
        // Arrange
        when(substanceService.findByName("Test Substance")).thenReturn(testSubstanceDTO);

        // Act
        ResponseEntity<SubstanceDTO> response = substanceController.getSubstanceByName("Test Substance");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testSubstanceDTO, response.getBody());
        verify(substanceService).findByName("Test Substance");
    }

    @Test
    void getSubstanceByOonNumber_Success() {
        // Arrange
        when(substanceService.findById(1234)).thenReturn(testSubstanceDTO);

        // Act
        ResponseEntity<SubstanceDTO> response = substanceController.getSubstanceByOonNumber(1234);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testSubstanceDTO, response.getBody());
        verify(substanceService).findById(1234);
    }

    @Test
    void getSubstanceByImdgCode_Success() {
        // Arrange
        when(substanceService.findByImdgCode("IMDG123")).thenReturn(testSubstanceDTO);

        // Act
        ResponseEntity<SubstanceDTO> response = substanceController.getSubstanceByImdgCode("IMDG123");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testSubstanceDTO, response.getBody());
        verify(substanceService).findByImdgCode("IMDG123");
    }

    @Test
    void getSubstanceByHazCode_Success() {
        // Arrange
        when(substanceService.findByHazCode("HAZ123")).thenReturn(testSubstanceDTO);

        // Act
        ResponseEntity<SubstanceDTO> response = substanceController.getSubstanceByHazCode("HAZ123");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testSubstanceDTO, response.getBody());
        verify(substanceService).findByHazCode("HAZ123");
    }

    @Test
    void getSubstanceByFormula_Success() {
        // Arrange
        when(substanceService.findByFormula("H2O")).thenReturn(testSubstanceDTO);

        // Act
        ResponseEntity<SubstanceDTO> response = substanceController.getSubstanceByFormula("H2O");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testSubstanceDTO, response.getBody());
        verify(substanceService).findByFormula("H2O");
    }

    @Test
    void getByImage_Success() {
        // Arrange
        MultipartFile file = new MockMultipartFile(
                "file",
                "test.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "test image content".getBytes()
        );
        when(substanceService.findByImage(any(MultipartFile.class))).thenReturn(testSubstanceDTO);

        // Act
        ResponseEntity<SubstanceDTO> response = substanceController.getByImage(file);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testSubstanceDTO, response.getBody());
        verify(substanceService).findByImage(file);
    }

    @Test
    void getAllIds_Success() {
        // Arrange
        List<Integer> expectedIds = Arrays.asList(1, 2, 3);
        when(substanceService.getAllSubstancesId()).thenReturn(expectedIds);

        // Act
        ResponseEntity<List<Integer>> response = substanceController.getAllIds();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedIds, response.getBody());
        verify(substanceService).getAllSubstancesId();
    }

    @Test
    void getAll_Success() {
        // Arrange
        List<SubstanceDTO> expectedSubstances = Arrays.asList(testSubstanceDTO);
        when(substanceService.findAll()).thenReturn(expectedSubstances);

        // Act
        ResponseEntity<List<SubstanceDTO>> response = substanceController.getAll();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedSubstances, response.getBody());
        verify(substanceService).findAll();
    }

    @Test
    void searchSubstances_Success() {
        // Arrange
        Page<SubstanceDTO> expectedPage = new PageImpl<>(Arrays.asList(testSubstanceDTO));
        when(substanceService.search(
                eq("test"), eq("5678"), eq("SOLID"), eq("HEAVIER"),
                eq("HEAVIER"), eq("INSOLUBLE"), eq("FIRE"), eq("LOW"),
                any(PageRequest.class)
        )).thenReturn(expectedPage);

        // Act
        ResponseEntity<Page<SubstanceDTO>> response = substanceController.searchSubstances(
                0, 5, "test", "5678", "SOLID", "HEAVIER",
                "HEAVIER", "INSOLUBLE", "FIRE", "LOW"
        );

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedPage, response.getBody());
        verify(substanceService).search(
                eq("test"), eq("5678"), eq("SOLID"), eq("HEAVIER"),
                eq("HEAVIER"), eq("INSOLUBLE"), eq("FIRE"), eq("LOW"),
                any(PageRequest.class)
        );
    }

    @Test
    void getSubstanceByName_NotFound() {
        // Arrange
        when(substanceService.findByName("Nonexistent"))
                .thenThrow(new EntityNotFoundException("Речовини з назвою Nonexistent не знайдено"));

        // Act & Assert
        assertThrows(EntityNotFoundException.class,
                () -> substanceController.getSubstanceByName("Nonexistent"));
        verify(substanceService).findByName("Nonexistent");
    }

    @Test
    void searchSubstances_WithDefaultValues() {
        // Arrange
        Page<SubstanceDTO> expectedPage = new PageImpl<>(Arrays.asList(testSubstanceDTO));
        when(substanceService.search(
                null, null, null, null,
                null, null, null, null,
                PageRequest.of(0, 5)
        )).thenReturn(expectedPage);

        // Act
        ResponseEntity<Page<SubstanceDTO>> response = substanceController.searchSubstances(
                0, 5, null, null, null, null,
                null, null, null, null
        );

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedPage, response.getBody());
        verify(substanceService).search(
                null, null, null, null,
                null, null, null, null,
                PageRequest.of(0, 5)
        );
    }

    @Test
    void getByImage_WithInvalidFile() {
        // Arrange
        MultipartFile file = new MockMultipartFile(
                "file",
                "test.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "invalid content".getBytes()
        );
        when(substanceService.findByImage(any(MultipartFile.class)))
                .thenThrow(new IllegalArgumentException("Invalid file format"));

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> substanceController.getByImage(file));
        verify(substanceService).findByImage(file);
    }
}