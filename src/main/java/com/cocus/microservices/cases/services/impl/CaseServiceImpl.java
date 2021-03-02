package com.cocus.microservices.cases.services.impl;

import com.cocus.microservices.bo.entities.CaseBO;
import com.cocus.microservices.cases.clients.CustomerClient;
import com.cocus.microservices.cases.dto.CaseDTO;
import com.cocus.microservices.cases.dto.CaseRequestDTO;
import com.cocus.microservices.cases.dto.CustomerDTO;
import com.cocus.microservices.cases.repositories.CaseRepository;
import com.cocus.microservices.cases.services.CaseService;
import com.cocus.microservices.label.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Haytham DAHRI
 */
@Service
public class CaseServiceImpl implements CaseService {

    private final CaseRepository caseRepository;
    private final CustomerClient customerClient;

    public CaseServiceImpl(CaseRepository caseRepository, CustomerClient customerClient) {
        this.caseRepository = caseRepository;
        this.customerClient = customerClient;
    }

    @Override
    public CaseBO saveCase(CaseRequestDTO caseRequest, String username) {
        CaseBO applicationCase;
        if( caseRequest.getId() == null ) {
            applicationCase = new CaseBO();
        } else {
            applicationCase = this.getCase(caseRequest.getId());
        }
        applicationCase.setContent(caseRequest.getContent());
        applicationCase = this.caseRepository.save(applicationCase);
        // Retrieve Customer
        CustomerDTO customer = this.customerClient.getCustomer(username).getBody();
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
        return this.caseRepository.findAll().stream().map(caseBO -> new CaseDTO(caseBO.getId(), caseBO.getContent()))
                .collect(Collectors.toList());
    }

    @Override
    public List<CaseDTO> getUserCases(String username) {
        // Retrieve Customer Cases
        return this.caseRepository.findByCustomer_Username(username).stream().map(caseBO ->
                new CaseDTO(caseBO.getId(), caseBO.getContent())).collect(Collectors.toList());
    }
}
