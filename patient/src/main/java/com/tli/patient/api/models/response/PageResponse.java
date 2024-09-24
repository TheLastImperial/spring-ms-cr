package com.tli.patient.api.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
public class PageResponse {
    private List<PatientResponse> content;
    private Long totalElements;
}
