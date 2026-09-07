package com.dev.nigrani.checklist;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class ChecklistItemResponse {

    private Long id;
    private Long inspectionId;
    private String itemKey;
    private String title;
    private String description;
    private String result;
    private String remarks;
    private Instant createdAt;
    private Instant updatedAt;
}