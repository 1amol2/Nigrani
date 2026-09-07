package com.dev.nigrani.inspection;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class InspectionResponse {

    private Long id;

    private Long instituteId;

    private String instituteName;

    private Instant inspectionDate;

    private Long inspectorId;

    private String type;
    private String priority;
    private String reason;
    private Integer allowedRadiusMeters;

    private String status;

    private String overallRemarks;

    private Double latitude;

    private Double longitude;

    private Boolean locationVerified;

    private Instant startedAt;

    private Instant completedAt;

    private Instant createdAt;

    private Instant updatedAt;
}