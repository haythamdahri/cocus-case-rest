package com.cocus.microservices.cases.clients;

import com.cocus.microservices.cases.config.FeignConfiguration;
import com.cocus.microservices.cases.dto.CustomerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Haytham DAHRI
 */
@FeignClient(name = "customer-client", url = "${com.cocus.customer.uri}", configuration = FeignConfiguration.class)
public interface CustomerClient {

    @GetMapping(path = "/api/v1/customers/usernames/{username}")
    ResponseEntity<CustomerDTO> getCustomer(@PathVariable(name = "username") String username);

}
