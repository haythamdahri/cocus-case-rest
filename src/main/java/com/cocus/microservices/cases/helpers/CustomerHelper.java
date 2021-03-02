package com.cocus.microservices.cases.helpers;

import com.cocus.microservices.cases.clients.CustomerClient;
import com.cocus.microservices.cases.constants.CustomerConstants;
import com.cocus.microservices.cases.dto.CustomerDTO;
import com.cocus.microservices.label.exceptions.BusinessException;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

/**
 * @author Haytham DAHRI
 */
@Component
public class CustomerHelper {

    private final CustomerClient customerClient;
    private static final HttpHeaders HTTP_HEADERS = new HttpHeaders();

    public CustomerHelper(CustomerClient customerClient) {
        this.customerClient = customerClient;
    }

    public CustomerDTO getCustomer(HttpHeaders httpHeaders, String username) {
        if( httpHeaders == null || !httpHeaders.containsKey(CustomerConstants.AUTHORIZATION_HEADER_KEY) ) {
            throw new BusinessException("Cannot process request to customer without Authorization header");
        }
        HTTP_HEADERS.add(CustomerConstants.AUTHORIZATION_HEADER_KEY, httpHeaders.getFirst(CustomerConstants.AUTHORIZATION_HEADER_KEY));
        return this.customerClient.getCustomer(HTTP_HEADERS, username).getBody();
    }

}
