package com.cocus.microservices.cases.services.impl;

import com.cocus.microservices.bo.entities.CaseBO;
import com.cocus.microservices.bo.entities.LabelBO;
import com.cocus.microservices.cases.clients.CustomerClient;
import com.cocus.microservices.cases.dto.*;
import com.cocus.microservices.cases.helpers.CustomerHelper;
import com.cocus.microservices.cases.helpers.LabelHelper;
import com.cocus.microservices.cases.repositories.CaseRepository;
import com.cocus.microservices.cases.services.CaseService;
import com.cocus.microservices.label.exceptions.BusinessException;
import com.cocus.microservices.label.exceptions.NotFoundException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Haytham DAHRI
 */
@Service
public class CaseServiceImpl implements CaseService {

    private final CaseRepository caseRepository;
    private final CustomerHelper customerHelper;
    private final LabelHelper labelHelper;

    public CaseServiceImpl(CaseRepository caseRepository, CustomerHelper customerHelper, LabelHelper labelHelper) {
        this.caseRepository = caseRepository;
        this.customerHelper = customerHelper;
        this.labelHelper = labelHelper;
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
    public ReviewDTO getReview(String username, long id) {
        CaseBO caseBO = this.getCase(id);
        // Check Owner Is The Provided User
        if(caseBO.getCustomer() == null || !caseBO.getCustomer().getUsername().equals(username) ) {
            throw new BusinessException("Review does not belong to the provided user!");
        }
        // Map CaseBO To ReviewDTO
        return this.mapCaseBOToReviewBO(caseBO);
    }

    @Override
    public ReviewDTO reviewCase(HttpHeaders httpHeaders, String username, CaseReviewDTO caseReview) {
        CaseBO caseBO = this.getCase(caseReview.getId());
        // Init Case Labels
        caseBO.setConditions(new HashSet<>());
        if(ArrayUtils.isNotEmpty(caseReview.getLabels()) ) {
            for( long labelId : caseReview.getLabels() ) {
                LabelBO label = this.labelHelper.getLabel(labelId);
                if( label != null ) {
                    caseBO.getConditions().add(label);
                }
            }
        }
        // Set case as reviewed
        caseBO.setReviewed(true);
        caseBO.setLastReviewDate(LocalDateTime.now());
        // Save Case
        caseBO = this.caseRepository.save(caseBO);
        // Map And Return
        return this.mapCaseBOToReviewBO(caseBO);
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
            casesPage = this.caseRepository.findCustomerCases(username, PageRequest.of(page, size, Sort.Direction.DESC, "timestamp"));
        } else {
            casesPage = this.caseRepository.findCustomerCases(username, search.toLowerCase(), PageRequest.of(page, size, Sort.Direction.DESC, "timestamp"));
        }
        // Map CaseBO content to CaseDTO
        List<CaseDTO> content = casesPage.getContent().stream().map(caseBO -> new CaseDTO(caseBO.getId(),
                caseBO.getContent(), caseBO.isReviewed())).collect(Collectors.toList());
        return new PageImpl<>(content, casesPage.getPageable(), casesPage.getTotalElements());
    }

    @Override
    public UnreviewedCasesCounterDTO getUnreviewedCasesCounter(String username) {
        return new UnreviewedCasesCounterDTO(this.caseRepository.countUserUnreviewedCases(username));
    }

    @Override
    public ReviewDTO getUserFirstUnreviewedReview(String username) {
        Page<CaseBO> firstCaseToReviewPage = this.caseRepository.findUserFirstUnreviewedCase(username, PageRequest.of(0, 1));
        if( firstCaseToReviewPage.getContent().isEmpty() ) {
            return null;
        }
        CaseBO caseBO = firstCaseToReviewPage.getContent().get(0);
        return this.mapCaseBOToReviewBO(caseBO);
    }

    @Override
    public Page<ReviewDTO> getUserReviews(String username, String label, int page, int size) {
        Page<CaseBO> casesPage = this.caseRepository.findCustomerCases(username, PageRequest.of(page, size, Sort.Direction.DESC, "timestamp"));;
        List<CaseBO> content;
        if( !StringUtils.isEmpty(label) ) {
            content = casesPage.getContent().stream().filter(caseBO ->
                    caseBO.getConditions().stream().anyMatch(labelBO -> labelBO.getId() == Long.parseLong(label))
            ).collect(Collectors.toList());
        } else {
            content = casesPage.getContent();
        }
        // Map CaseBO to ReviewDTO
        List<ReviewDTO> cases = new ArrayList<>();
        if( content != null ) {
            for( CaseBO caseBO : content ) {
                cases.add(this.mapCaseBOToReviewBO(caseBO));
            }
        }
        // Return Page
        return new PageImpl<>(cases, casesPage.getPageable(), casesPage.getTotalElements());
    }

    private ReviewDTO mapCaseBOToReviewBO(CaseBO caseBO) {
        ReviewDTO review = new ReviewDTO();
        review.setId(caseBO.getId());
        review.setContent(caseBO.getContent());
        review.setLastReviewDate(caseBO.getLastReviewDate());
        review.setReviewed(caseBO.isReviewed());
        // Map LabelBO to LabelDTO
        if( caseBO.getConditions() != null ) {
            for( LabelBO label : caseBO.getConditions() ) {
                review.addLabel(this.mapLabelBOToLabelDTO(label));
            }
        }
        // Return Review
        return review;
    }

    private LabelDTO mapLabelBOToLabelDTO(LabelBO labelBO) {
        return new LabelDTO(labelBO.getId(), labelBO.getDescription());
    }
}
