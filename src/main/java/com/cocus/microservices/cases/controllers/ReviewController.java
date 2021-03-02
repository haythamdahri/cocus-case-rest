package com.cocus.microservices.cases.controllers;

import com.cocus.microservices.cases.dto.ReviewDTO;
import com.cocus.microservices.cases.facades.IAuthenticationFacade;
import com.cocus.microservices.cases.services.CaseService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(path = "/{id}")
    public ResponseEntity<ReviewDTO> getReview(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(this.caseService.getReview(this.authenticationFacade.extractUsernameFromAuthentication(), id));
    }

}
