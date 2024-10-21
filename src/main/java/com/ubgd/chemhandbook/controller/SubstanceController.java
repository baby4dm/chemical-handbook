package com.ubgd.chemhandbook.controller;

import com.ubgd.chemhandbook.model.Substance;
import com.ubgd.chemhandbook.model.SubstanceDTO;
import com.ubgd.chemhandbook.service.SubstanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/substances")
@RequiredArgsConstructor
public class SubstanceController {
    private final SubstanceService substanceService;
    @GetMapping("/{name}")
    public ResponseEntity<SubstanceDTO> getSubstanceByName(@PathVariable(name = "name") String name){
        SubstanceDTO substance = substanceService.findByName(name);
        return ResponseEntity.ok(substance);
    }

    @GetMapping("/oon-number/{oon-number}")
    public ResponseEntity<SubstanceDTO> getSubstanceByOonNumber(@PathVariable("oon-number") Integer oonNumber){
        SubstanceDTO substance = substanceService.findById(oonNumber);
        return  ResponseEntity.ok(substance);
    }

    @GetMapping("/imdg/{imdg}")
    public ResponseEntity<SubstanceDTO> getSubstanceByImdgCode(@PathVariable("imdg") String imdgCode){
        SubstanceDTO substance = substanceService.findByImdgCode(imdgCode);
        return  ResponseEntity.ok(substance);
    }
    @GetMapping("/haz/{haz}")
    public ResponseEntity<SubstanceDTO> getSubstanceByHazCode(@PathVariable("haz") String hazCode){
        SubstanceDTO substance = substanceService.findByHazCode(hazCode);
        return  ResponseEntity.ok(substance);
    }

    @GetMapping("/formula/{formula}")
    public ResponseEntity<SubstanceDTO> getSubstanceByFormula(@PathVariable("formula") String formula){
        SubstanceDTO substance = substanceService.findByFormula(formula);
        return  ResponseEntity.ok(substance);
    }

    @PostMapping(value = "/extract-text", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SubstanceDTO> getByImage(@RequestParam("file") MultipartFile file) {
        SubstanceDTO substance = substanceService.findByImage(file);
        return ResponseEntity.ok(substance);
    }
}
