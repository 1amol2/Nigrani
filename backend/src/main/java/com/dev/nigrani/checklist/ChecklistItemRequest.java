package com.dev.nigrani.checklist;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChecklistItemRequest {

    @NotBlank
    private String itemKey;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String result;

    private String remarks;
}