package com.dev.nigrani.risk_assignment.dtos;

import com.dev.nigrani.risk_assignment.models.RiskAssessment;
import com.dev.nigrani.risk_assignment.models.RiskLevel;
import lombok.*;
import java.time.LocalDateTime;

@Getter @NoArgsConstructor @AllArgsConstructor @Builder
public class RiskAssessmentResponse {
    private Long id;
    private Long instituteId;
    private String instituteName;
    private Integer riskScore;
    private RiskLevel riskLevel;
    private String reason;
    private LocalDateTime calculatedAt;

    /** Converts a service/database model into the response returned to Android. */
    public static RiskAssessmentResponse fromModel(RiskAssessment assessment) {
        return RiskAssessmentResponse.builder()
                .id(assessment.getId())
                .instituteId(assessment.getInstituteId())
                .instituteName(assessment.getInstituteName())
                .riskScore(assessment.getRiskScore())
                .riskLevel(assessment.getRiskLevel())
                .reason(assessment.getReason())
                .calculatedAt(assessment.getCalculatedAt())
                .build();
    }
}
