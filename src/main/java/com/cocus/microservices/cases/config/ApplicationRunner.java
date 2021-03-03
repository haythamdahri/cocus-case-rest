package com.cocus.microservices.cases.config;

import com.cocus.microservices.bo.entities.CaseBO;
import com.cocus.microservices.cases.clients.CustomerClient;
import com.cocus.microservices.cases.dto.CustomerDTO;
import com.cocus.microservices.cases.repositories.CaseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

/**
 * @author Haytham DAHRI
 */
@Configuration
public class ApplicationRunner implements CommandLineRunner {

    private final CaseRepository caseRepository;
    private final CustomerClient customerClient;

    public ApplicationRunner(CaseRepository caseRepository, CustomerClient customerClient) {
        this.caseRepository = caseRepository;
        this.customerClient = customerClient;
    }

    /**
     * Populate Testing DATA if environment is not production
     *
     * @param args Input Arguments
     */
    @Override
    public void run(String... args) {
        if (this.caseRepository.count() == 0) {
            // Mock Cases
            final String username = "haytham";
            // Get Customer From customer-rest service
            CustomerDTO customer = this.customerClient.getCustomer(username).getBody();
            CaseBO case1 = new CaseBO("Label Description", null, null, false, null);
            case1 = this.caseRepository.save(case1);
            // Set Customer
            this.caseRepository.updateCaseCustomer(case1.getId(), customer.getId());
        }
    }
}
