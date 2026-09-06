package com.dev.nigrani.alerts.dtos;

import com.dev.nigrani.alerts.models.AlertSeverity;
import com.dev.nigrani.alerts.models.AlertType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Data received by the backend when a new alert is created.
 * It deliberately does not contain database-generated fields such as id,
 * createdAt, updatedAt, status, source, or riskScore.
 */
@Getter
@Setter
@NoArgsConstructor
public class CreateAlertRequest {

    @NotBlank(message = "Alert title is required")
    private String title;

    @NotBlank(message = "Alert description is required")
    private String description;

    @NotNull(message = "Institute ID is required")
    private Long instituteId;

    @NotBlank(message = "Institute name is required")
    private String instituteName;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Alert type is required")
    private AlertType type;

    @NotNull(message = "Alert severity is required")
    private AlertSeverity severity;

    @NotNull(message = "Confidence score is required")
    @Min(value = 0, message = "Confidence score cannot be less than 0")
    @Max(value = 100, message = "Confidence score cannot be greater than 100")
    private Integer confidenceScore;

    /** Optional permanent evidence URL/key/reference. */
    private String evidenceReference;

    /** These three values are used only for an attendance-anomaly dashboard card. */
    private Integer reportedValue;
    private Integer detectedValue;
    private Integer variance;

    /** Optional: if absent, the service will use the current date and time. */
    private LocalDateTime detectedAt;
}