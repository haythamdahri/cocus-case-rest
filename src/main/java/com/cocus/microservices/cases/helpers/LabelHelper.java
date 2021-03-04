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

    public LabelHelper(LabelClient labelClient) {
        this.labelClient = labelClient;
    }

    public LabelBO getLabel(long label) {
        return this.labelClient.getLabel(label).getBody();
    }

}
