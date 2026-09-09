package com.dev.nigrani.alerts.dtos;

import com.dev.nigrani.alerts.models.AlertSeverity;
import com.dev.nigrani.alerts.models.AlertStatus;
import com.dev.nigrani.alerts.models.AlertType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlertResponse {

    private Long id;
    private String title;
    private String description;
    private Long instituteId;
    private String instituteName;
    private String location;
    private AlertType type;
    private AlertSeverity severity;
    private Integer confidenceScore;
    private Integer riskScore;
    private AlertStatus status;
    private String evidenceReference;
    private Integer reportedValue;
    private Integer detectedValue;
    private Integer variance;
    private LocalDateTime detectedAt;
}