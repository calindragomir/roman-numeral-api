package org.project.romannumeral.model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Builder
@Data
public class IntegerRangeRequest {

    @NotNull
    @Range(min=1, max=3999)
    Integer from;

    @NotNull
    @Range(min=1, max=3999)
    Integer to;

}
