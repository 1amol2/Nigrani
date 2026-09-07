package com.dev.nigrani.inspection;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class InspectionRequest {

    @NotNull
    @Positive
    private Long instituteId;

    @NotNull
    private Instant inspectionDate;

    @NotNull
    @Positive
    private Long inspectorId;

    @NotBlank
    private String type;

    @NotBlank
    private String priority;

    @NotBlank
    private String reason;

    @NotNull
    @Positive
    private Integer allowedRadiusMeters;

    @NotNull
    private String status;

    private String overallRemarks;

    private Double latitude;

    private Double longitude;

    @NotNull
    private Boolean locationVerified;

    private Instant startedAt;

    private Instant completedAt;
}