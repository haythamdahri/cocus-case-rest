package com.cocus.microservices.cases.services;

import com.cocus.microservices.bo.entities.CaseBO;
import com.cocus.microservices.cases.dto.CaseDTO;
import com.cocus.microservices.cases.dto.CaseRequestDTO;

import java.util.List;

/**
 * @author Haytham DAHRI
 */
public interface CaseService {

    CaseBO saveCase(CaseRequestDTO caseRequest, String username);

    CaseBO getCase(Long id);

    void deleteCase(Long id);

    List<CaseDTO> getCases();

    List<CaseDTO> getUserCases(String username);

}
