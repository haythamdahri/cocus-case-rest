package com.cocus.microservices.cases.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Haytham DAHRI
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaseDTO implements Serializable {

    private static final long serialVersionUID = 8627648588741998274L;

    @JsonProperty("id")
    private long id;

    @JsonProperty("content")
    private String content;

    private boolean reviewed;

}
