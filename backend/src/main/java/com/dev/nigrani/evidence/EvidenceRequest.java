package com.dev.nigrani.evidence;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class EvidenceRequest {

    @NotBlank
    private String category;

    private Instant capturedAt;

    private Double latitude;

    private Double longitude;
}