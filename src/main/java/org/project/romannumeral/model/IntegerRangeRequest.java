package org.project.romannumeral.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class IntegerRangeRequest {

    Integer from;
    Integer to;

}
