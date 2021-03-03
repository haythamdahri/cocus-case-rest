package com.cocus.microservices.cases.controllers;

import com.cocus.microservices.cases.dto.CaseReviewDTO;
import com.cocus.microservices.cases.dto.ReviewDTO;
import com.cocus.microservices.cases.facades.IAuthenticationFacade;
import com.cocus.microservices.cases.services.CaseService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Haytham DAHRI
 */
@RestController
@RequestMapping(path = "/api/v1/reviews")
public class ReviewController {

    private final CaseService caseService;
    private final IAuthenticationFacade authenticationFacade;

    public ReviewController(CaseService caseService, IAuthenticationFacade authenticationFacade) {
        this.caseService = caseService;
        this.authenticationFacade = authenticationFacade;
    }

    @GetMapping(path = "/")
    public ResponseEntity<Page<ReviewDTO>> getUserReviews(@RequestParam(value = "label", required = false, defaultValue = "") String label,
                                                          @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                          @RequestParam(value = "size", required = false, defaultValue = "${page.default-size}") int size) {
        return ResponseEntity.ok(this.caseService.getUserReviews(this.authenticationFacade.extractUsernameFromAuthentication(), label, page, size));
    }

    @PostMapping(path = "/")
    public ResponseEntity<ReviewDTO> reviewCase(@RequestHeader HttpHeaders httpHeaders, @Valid @RequestBody CaseReviewDTO caseReview) {
        return ResponseEntity.ok(this.caseService.reviewCase(httpHeaders, this.authenticationFacade.extractUsernameFromAuthentication(), caseReview));
    }

    @GetMapping(path = "/unreviewed/first")
    public ResponseEntity<ReviewDTO> getUserFirstUnreviewedReview() {
        return ResponseEntity.ok(this.caseService.getUserFirstUnreviewedReview(this.authenticationFacade.extractUsernameFromAuthentication()));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ReviewDTO> getReview(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(this.caseService.getReview(this.authenticationFacade.extractUsernameFromAuthentication(), id));
    }

}
