package com.cocus.microservices.cases.clients;

import com.cocus.microservices.bo.entities.LabelBO;
import com.cocus.microservices.cases.config.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author Haytham DAHRI
 */
@FeignClient(name = "label-client", url = "${com.cocus.label.uri}", configuration = FeignConfiguration.class)
public interface LabelClient {

    @GetMapping(path = "/api/v1/labels/{id}")
    ResponseEntity<LabelBO> getLabel(@RequestHeader HttpHeaders httpHeaders, @PathVariable(name = "id") Long id);

}
