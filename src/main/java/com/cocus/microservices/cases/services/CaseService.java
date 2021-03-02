package com.cocus.microservices.cases.services;

import com.cocus.microservices.bo.entities.CaseBO;
import com.cocus.microservices.cases.dto.CaseDTO;
import com.cocus.microservices.cases.dto.CaseRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;

import java.util.List;

/**
 * @author Haytham DAHRI
 */
public interface CaseService {

    CaseBO saveCase(HttpHeaders httpHeaders, CaseRequestDTO caseRequest, String username);

    CaseBO getCase(Long id);

    void deleteCase(Long id);

    List<CaseDTO> getCases();

    Page<CaseDTO> getUserCases(String username, String search, int page, int size);

}
