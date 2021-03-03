package com.cocus.microservices.cases.dto;

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
public class CaseReviewDTO implements Serializable {
    private static final long serialVersionUID = -8746015654285245410L;

    @JsonProperty("id")
    @NotNull
    private long id;

    @JsonProperty("labels")
    @NotNull
    @NotEmpty
    private long[] labels;

}
