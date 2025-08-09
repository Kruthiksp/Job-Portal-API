package com.kruthik.dtos;

import com.kruthik.enums.ApplicationStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationSummaryDTO {
    private int id;
    private String resume;
    private ApplicationStatus status;
}

