package com.dev.nigrani.institute;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstituteRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String location;

    @NotBlank
    private String type;

    @NotNull
    @PositiveOrZero
    private Integer beneficiaries;

    @PositiveOrZero
    private Double attendancePercentage;

    private Double latitude;

    private Double longitude;

    @NotNull
    private Boolean cctvOnline;

    @NotBlank
    private String status;
}