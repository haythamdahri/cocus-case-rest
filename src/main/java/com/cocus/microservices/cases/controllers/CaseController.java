package com.cocus.microservices.cases.controllers;

import com.cocus.microservices.bo.entities.CaseBO;
import com.cocus.microservices.cases.dto.CaseDTO;
import com.cocus.microservices.cases.dto.CaseRequestDTO;
import com.cocus.microservices.cases.facades.IAuthenticationFacade;
import com.cocus.microservices.cases.services.CaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Haytham DAHRI
 */
@RestController
@RequestMapping(path = "/api/v1/cases")
public class CaseController {

    private final CaseService caseService;
    private final IAuthenticationFacade authenticationFacade;

    public CaseController(CaseService caseService, IAuthenticationFacade authenticationFacade) {
        this.caseService = caseService;
        this.authenticationFacade = authenticationFacade;
    }

    @GetMapping(path = "/")
    public ResponseEntity<List<CaseDTO>> getCases() {
        return ResponseEntity.ok(this.caseService.getCases());
    }

    @PostMapping(path = "/")
    public ResponseEntity<CaseBO> saveCase(@RequestBody CaseRequestDTO caseRequest) {
        return ResponseEntity.ok(this.caseService.saveCase(caseRequest, this.authenticationFacade.extractUsernameFromAuthentication()));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<CaseBO> updateCase(@PathVariable(name = "id") Long id, @RequestBody CaseRequestDTO caseRequest) {
        caseRequest.setId(id);
        return ResponseEntity.ok(this.caseService.saveCase(caseRequest, this.authenticationFacade.extractUsernameFromAuthentication()));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CaseBO> getCase(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(this.caseService.getCase(id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteCase(@PathVariable(name = "id") Long id) {
        this.caseService.deleteCase(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/users/current")
    public ResponseEntity<List<CaseDTO>> getUsersCases() {
        // Retrieve Authenticated User Assigned Cases
        return ResponseEntity.ok(this.caseService.getUserCases(authenticationFacade.extractUsernameFromAuthentication()));
    }

}
