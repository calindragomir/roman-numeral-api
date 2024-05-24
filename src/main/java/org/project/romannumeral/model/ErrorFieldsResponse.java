package org.project.romannumeral.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Builder
@Data
public class ErrorFieldsResponse {

    Integer errorCount;
    Map<String, String> errorFields;

}
