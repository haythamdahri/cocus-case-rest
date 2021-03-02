package com.cocus.microservices.cases.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Haytham DAHRI
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaseRequestDTO implements Serializable {

    private static final long serialVersionUID = 3609408843315524505L;

    @JsonIgnore
    private Long id;

    @JsonProperty("content")
    @NotNull
    @NotEmpty
    private String content;

}
