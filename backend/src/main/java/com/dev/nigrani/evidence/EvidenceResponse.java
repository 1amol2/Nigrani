package com.dev.nigrani.evidence;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class EvidenceResponse {

    private Long id;
    private Long inspectionId;
    private String category;
    private String fileName;
    private String storageReference;
    private String contentType;
    private Instant capturedAt;
    private Double latitude;
    private Double longitude;
    private Instant createdAt;
}