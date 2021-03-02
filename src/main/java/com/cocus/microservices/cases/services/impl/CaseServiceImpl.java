package com.cocus.microservices.cases.services.impl;

import com.cocus.microservices.bo.entities.CaseBO;
import com.cocus.microservices.cases.clients.CustomerClient;
import com.cocus.microservices.cases.dto.CaseDTO;
import com.cocus.microservices.cases.dto.CaseRequestDTO;
import com.cocus.microservices.cases.dto.CustomerDTO;
import com.cocus.microservices.cases.helpers.CustomerHelper;
import com.cocus.microservices.cases.repositories.CaseRepository;
import com.cocus.microservices.cases.services.CaseService;
import com.cocus.microservices.label.exceptions.NotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Haytham DAHRI
 */
@Service
public class CaseServiceImpl implements CaseService {

    private final CaseRepository caseRepository;
    private final CustomerHelper customerHelper;

    public CaseServiceImpl(CaseRepository caseRepository, CustomerHelper customerHelper) {
        this.caseRepository = caseRepository;
        this.customerHelper = customerHelper;
    }

    @Override
    public CaseBO saveCase(HttpHeaders httpHeaders, CaseRequestDTO caseRequest, String username) {
        CaseBO applicationCase;
        if( caseRequest.getId() == null ) {
            applicationCase = new CaseBO();
        } else {
            applicationCase = this.getCase(caseRequest.getId());
        }
        applicationCase.setContent(caseRequest.getContent());
        applicationCase = this.caseRepository.save(applicationCase);
        // Retrieve Customer
        CustomerDTO customer = this.customerHelper.getCustomer(httpHeaders, username);
        // Assign Case To The Customer
        this.caseRepository.updateCaseCustomer(applicationCase.getId(), customer.getId());
        // Return Case
        return this.getCase(applicationCase.getId());
    }

    @Override
    public CaseBO getCase(Long id) {
        return this.caseRepository.findById(id).orElseThrow(() -> new NotFoundException("No Case Found With ID " + id));
    }

    @Override
    public void deleteCase(Long id) {
        this.caseRepository.deleteById(id);
    }

    @Override
    public List<CaseDTO> getCases() {
        return this.caseRepository.findAll().stream().map(caseBO -> new CaseDTO(caseBO.getId(), caseBO.getContent(), caseBO.isReviewed()))
                .collect(Collectors.toList());
    }

    @Override
    public Page<CaseDTO> getUserCases(String username, String search, int page, int size) {
        // Retrieve Customer Cases
        Page<CaseBO> casesPage;
        if(StringUtils.isEmpty(search)) {
            casesPage = this.caseRepository.findCustomerCases(username, PageRequest.of(page, size));
        } else {
            casesPage = this.caseRepository.findCustomerCases(username, search, PageRequest.of(page, size));
        }
        // Map CaseBO content to CaseDTO
        List<CaseDTO> content = casesPage.getContent().stream().map(caseBO -> new CaseDTO(caseBO.getId(),
                caseBO.getContent(), caseBO.isReviewed())).collect(Collectors.toList());
        return new PageImpl<>(content, casesPage.getPageable(), casesPage.getTotalElements());
    }
}
