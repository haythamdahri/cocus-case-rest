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
public class UnreviewedCasesCounterDTO implements Serializable {

    private static final long serialVersionUID = -6340119293034064615L;

    @JsonProperty("unreviewedCases")
    private int unreviewedCases;

}
