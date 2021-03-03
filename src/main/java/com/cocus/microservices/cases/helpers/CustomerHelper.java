package com.cocus.microservices.cases.helpers;

import com.cocus.microservices.cases.clients.CustomerClient;
import com.cocus.microservices.cases.dto.CustomerDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

/**
 * @author Haytham DAHRI
 */
@Component
public class CustomerHelper {

    private static final HttpHeaders HTTP_HEADERS = new HttpHeaders();
    private final CustomerClient customerClient;

    public CustomerHelper(CustomerClient customerClient) {
        this.customerClient = customerClient;
    }

    public CustomerDTO getCustomer(HttpHeaders httpHeaders, String username) {
        return this.customerClient.getCustomer(username).getBody();
    }

}
