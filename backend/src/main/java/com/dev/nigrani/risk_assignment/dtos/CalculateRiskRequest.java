package com.dev.nigrani.risk_assignment.dtos;

import com.dev.nigrani.risk_assignment.models.RiskAssessment;
import lombok.*;

/** Monitoring values sent to the risk rule. */
@Getter @Setter @NoArgsConstructor
public class CalculateRiskRequest {
    private Long instituteId;
    private String instituteName;
    private Integer attendanceVariance;
    private boolean cctvOffline;
    private boolean unusualInactivity;
    private boolean restrictedAreaMovement;

    /** Converts API input into a model. The service calculates score, level, and reason. */
    public RiskAssessment toModel() {
        return RiskAssessment.builder()
                .instituteId(instituteId)
                .instituteName(instituteName)
                .attendanceVariance(attendanceVariance)
                .cctvOffline(cctvOffline)
                .unusualInactivity(unusualInactivity)
                .restrictedAreaMovement(restrictedAreaMovement)
                .build();
    }
}
