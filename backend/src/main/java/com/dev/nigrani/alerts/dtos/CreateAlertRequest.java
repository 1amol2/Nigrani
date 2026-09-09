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

    private String evidenceReference;//image primarily

    private Integer reportedValue;
    private Integer detectedValue;
    private Integer variance;

    private LocalDateTime detectedAt;
}