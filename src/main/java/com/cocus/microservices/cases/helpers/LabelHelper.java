package com.cocus.microservices.cases.helpers;

import com.cocus.microservices.bo.entities.LabelBO;
import com.cocus.microservices.cases.clients.CustomerClient;
import com.cocus.microservices.cases.clients.LabelClient;
import com.cocus.microservices.cases.constants.CustomerConstants;
import com.cocus.microservices.cases.dto.CustomerDTO;
import com.cocus.microservices.label.exceptions.BusinessException;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

/**
 * @author Haytham DAHRI
 */
@Component
public class LabelHelper {

    private final LabelClient labelClient;
    private static final HttpHeaders HTTP_HEADERS = new HttpHeaders();

    public LabelHelper(LabelClient labelClient) {
        this.labelClient = labelClient;
    }

    public LabelBO getLabel(HttpHeaders httpHeaders, long label) {
        if( httpHeaders == null || !httpHeaders.containsKey(CustomerConstants.AUTHORIZATION_HEADER_KEY) ) {
            throw new BusinessException("Cannot process request to customer without Authorization header");
        }
        HTTP_HEADERS.add(CustomerConstants.AUTHORIZATION_HEADER_KEY, httpHeaders.getFirst(CustomerConstants.AUTHORIZATION_HEADER_KEY));
        return this.labelClient.getLabel(HTTP_HEADERS, label).getBody();
    }

}
