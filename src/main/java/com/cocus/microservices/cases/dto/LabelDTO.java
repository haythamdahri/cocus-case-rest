package com.cocus.microservices.cases.dto;

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
public class LabelDTO implements Serializable {
    private static final long serialVersionUID = -5471595661126192078L;

    private long id;

    private String description;

}
