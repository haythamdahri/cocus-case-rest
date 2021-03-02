package com.cocus.microservices.cases.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Haytham DAHRI
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO implements Serializable {
    private static final long serialVersionUID = 6877170971444613769L;

    @JsonProperty("id")
    private long id;

    @JsonProperty("content")
    private String content;

    @JsonProperty("conditions")
    private List<LabelDTO> conditions;

    @JsonProperty("reviewed")
    private boolean reviewed;

    @JsonProperty("lastReviewDate")
    private LocalDateTime lastReviewDate;

    public void addLabel(LabelDTO label) {
        if( this.conditions == null ) {
            this.conditions = new ArrayList<>();
        }
        this.conditions.add(label);
    }

}
