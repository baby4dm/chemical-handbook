package com.ubgd.chemhandbook.controller;

import com.ubgd.chemhandbook.model.SubstanceDTO;
import com.ubgd.chemhandbook.service.SubstanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/substances")
@RequiredArgsConstructor
@Tag(name = "Substances", description = "API для роботи з хімічними речовинами")
public class SubstanceController {
    private final SubstanceService substanceService;

    @Operation(summary = "Отримати речовину за назвою",
            description = "Повертає інформацію про хімічну речовину за її назвою")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Речовину знайдено",
                    content = @Content(schema = @Schema(implementation = SubstanceDTO.class))),
            @ApiResponse(responseCode = "404", description = "Речовину не знайдено")
    })
    @GetMapping("/{name}")
    public ResponseEntity<SubstanceDTO> getSubstanceByName(
            @Parameter(description = "Назва речовини") @PathVariable(name = "name") String name) {
        SubstanceDTO substance = substanceService.findByName(name);
        return ResponseEntity.ok(substance);
    }

    @Operation(summary = "Отримати речовину за OON номером",
            description = "Повертає інформацію про хімічну речовину за її OON номером")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Речовину знайдено",
                    content = @Content(schema = @Schema(implementation = SubstanceDTO.class))),
            @ApiResponse(responseCode = "404", description = "Речовину не знайдено")
    })
    @GetMapping("/oon-number/{oon-number}")
    public ResponseEntity<SubstanceDTO> getSubstanceByOonNumber(
            @Parameter(description = "OON номер речовини") @PathVariable("oon-number") Integer oonNumber) {
        SubstanceDTO substance = substanceService.findById(oonNumber);
        return ResponseEntity.ok(substance);
    }

    @Operation(summary = "Отримати речовину за IMDG кодом",
            description = "Повертає інформацію про хімічну речовину за її IMDG кодом")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Речовину знайдено",
                    content = @Content(schema = @Schema(implementation = SubstanceDTO.class))),
            @ApiResponse(responseCode = "404", description = "Речовину не знайдено")
    })
    @GetMapping("/imdg/{imdg}")
    public ResponseEntity<SubstanceDTO> getSubstanceByImdgCode(
            @Parameter(description = "IMDG код речовини") @PathVariable("imdg") String imdgCode) {
        SubstanceDTO substance = substanceService.findByImdgCode(imdgCode);
        return ResponseEntity.ok(substance);
    }

    @Operation(summary = "Отримати речовину за HAZ кодом",
            description = "Повертає інформацію про хімічну речовину за її HAZ кодом")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Речовину знайдено",
                    content = @Content(schema = @Schema(implementation = SubstanceDTO.class))),
            @ApiResponse(responseCode = "404", description = "Речовину не знайдено")
    })
    @GetMapping("/haz/{haz}")
    public ResponseEntity<SubstanceDTO> getSubstanceByHazCode(
            @Parameter(description = "HAZ код речовини") @PathVariable("haz") String hazCode) {
        SubstanceDTO substance = substanceService.findByHazCode(hazCode);
        return ResponseEntity.ok(substance);
    }

    @Operation(summary = "Отримати речовину за хімічною формулою",
            description = "Повертає інформацію про хімічну речовину за її хімічною формулою")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Речовину знайдено",
                    content = @Content(schema = @Schema(implementation = SubstanceDTO.class))),
            @ApiResponse(responseCode = "404", description = "Речовину не знайдено")
    })
    @GetMapping("/formula/{formula}")
    public ResponseEntity<SubstanceDTO> getSubstanceByFormula(
            @Parameter(description = "Хімічна формула речовини") @PathVariable("formula") String formula) {
        SubstanceDTO substance = substanceService.findByFormula(formula);
        return ResponseEntity.ok(substance);
    }

    @Operation(summary = "Отримати речовину за зображенням",
            description = "Аналізує завантажене зображення та повертає інформацію про знайдену хімічну речовину")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Речовину знайдено",
                    content = @Content(schema = @Schema(implementation = SubstanceDTO.class))),
            @ApiResponse(responseCode = "400", description = "Некоректний файл зображення")
    })
    @PostMapping(value = "/extract-text", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SubstanceDTO> getByImage(
            @Parameter(description = "Файл зображення") @RequestParam("file") MultipartFile file) {
        SubstanceDTO substance = substanceService.findByImage(file);
        return ResponseEntity.ok(substance);
    }

    @Operation(summary = "Отримати список всіх ID речовин",
            description = "Повертає список ідентифікаторів усіх наявних хімічних речовин")
    @ApiResponse(responseCode = "200", description = "Успішне виконання",
            content = @Content(schema = @Schema(implementation = List.class)))
    @GetMapping("/ids")
    public ResponseEntity<List<Integer>> getAllIds() {
        List<Integer> ids = substanceService.getAllSubstancesId();
        return ResponseEntity.ok(ids);
    }

    @Operation(summary = "Отримати список всіх речовин",
            description = "Повертає список усіх наявних хімічних речовин")
    @ApiResponse(responseCode = "200", description = "Успішне виконання",
            content = @Content(schema = @Schema(implementation = List.class)))
    @GetMapping
    public ResponseEntity<List<SubstanceDTO>> getAll() {
        List<SubstanceDTO> substances = substanceService.findAll();
        return ResponseEntity.ok(substances);
    }

    @Operation(summary = "Пошук речовин",
            description = "Пошук речовин за різними критеріями з пагінацією")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успішне виконання",
                    content = @Content(schema = @Schema(implementation = Page.class)))
    })
    @GetMapping("/search")
    public ResponseEntity<Page<SubstanceDTO>> searchSubstances(
            @Parameter(description = "Номер сторінки (починається з 0)")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Кількість елементів на сторінці")
            @RequestParam(defaultValue = "5") int limit,

            @Parameter(description = "Пошуковий запит")
            @RequestParam(required = false) String search,

            @Parameter(description = "Номер небезпеки")
            @RequestParam(required = false) String dangerousNumber,

            @Parameter(description = "Агрегатний стан")
            @RequestParam(required = false) String aggregationState,

            @Parameter(description = "Щільність відносно води")
            @RequestParam(required = false) String densityWater,

            @Parameter(description = "Щільність відносно повітря")
            @RequestParam(required = false) String densityAir,

            @Parameter(description = "Розчинність")
            @RequestParam(required = false) String solubility,

            @Parameter(description = "Загальна небезпека")
            @RequestParam(required = false) String generalDanger,

            @Parameter(description = "Небезпека при контакті з водою")
            @RequestParam(required = false) String waterDanger
    ) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<SubstanceDTO> substances = substanceService.search(
                search,
                dangerousNumber,
                aggregationState,
                densityWater,
                densityAir,
                solubility,
                generalDanger,
                waterDanger,
                pageable
        );
        return ResponseEntity.ok(substances);
    }
}