package com.ubgd.chemhandbook.service;

import com.ubgd.chemhandbook.model.Substance;
import com.ubgd.chemhandbook.model.SubstanceDTO;
import org.springframework.web.multipart.MultipartFile;

public interface SubstanceService {
    SubstanceDTO findByName(String name);
    SubstanceDTO findById(Integer id);

    SubstanceDTO findByImage(MultipartFile file);

    SubstanceDTO findByHazCode(String hazCode);

    SubstanceDTO findByImdgCode(String imdgCode);

    SubstanceDTO findByFormula(String formula);
}
