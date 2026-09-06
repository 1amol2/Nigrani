package com.dev.nigrani.institute;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class InstituteResponse {

    private Long id;

    private String name;

    private String location;

    private String type;

    private Integer beneficiaries;

    private Double attendancePercentage;

    private Double latitude;

    private Double longitude;

    private Boolean cctvOnline;

    private String status;

    private Instant lastChecked;

    private Instant createdAt;

    private Instant updatedAt;
}