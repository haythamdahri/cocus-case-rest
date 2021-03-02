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

    @GetMapping(path = "/currentuser")
    public ResponseEntity<List<CaseDTO>> getUsersCases() {
        // Retrieve Authenticated Username
        final String username = authenticationFacade.extractUsernameFromAuthentication();
        return ResponseEntity.ok(this.caseService.getUserCases(username));
    }

    @PostMapping(path = "/")
    public ResponseEntity<CaseBO> saveCase(@RequestBody CaseRequestDTO caseRequest) {
        return ResponseEntity.ok(this.caseService.saveCase(caseRequest));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<CaseBO> updateCase(@PathVariable(name = "id") Long id, @RequestBody CaseRequestDTO caseRequest) {
        caseRequest.setId(id);
        return ResponseEntity.ok(this.caseService.saveCase(caseRequest));
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

}
