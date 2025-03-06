package com.ubgd.chemhandbook.service;

import com.ubgd.chemhandbook.model.SubstanceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SubstanceService {
    SubstanceDTO findByName(String name);
    SubstanceDTO findById(Integer id);
   List<SubstanceDTO> findAll();

    SubstanceDTO findByImage(MultipartFile file);

    SubstanceDTO findByHazCode(String hazCode);

    SubstanceDTO findByImdgCode(String imdgCode);

    SubstanceDTO findByFormula(String formula);

    List<Integer> getAllSubstancesId();

    Page<SubstanceDTO> search(String search, String dangerousNumber, String aggregationState, String densityWater, String densityAir, String solubility, String generalDanger, String waterDanger, Pageable pageable);
}
