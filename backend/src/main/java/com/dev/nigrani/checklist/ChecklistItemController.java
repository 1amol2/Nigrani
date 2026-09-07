package com.dev.nigrani.checklist;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inspections/{inspectionId}/checklist")
@RequiredArgsConstructor
public class ChecklistItemController {

    private final ChecklistItemService checklistItemService;

    @GetMapping
    public ResponseEntity<List<ChecklistItemResponse>> getChecklist(
            @PathVariable Long inspectionId) {

        return ResponseEntity.ok(
                checklistItemService.getChecklistByInspectionId(
                        inspectionId
                )
        );
    }

    @GetMapping("/items/{id}")
    public ResponseEntity<ChecklistItemResponse> getChecklistItem(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                checklistItemService.getChecklistItemById(id)
        );
    }

    @PostMapping
    public ResponseEntity<ChecklistItemResponse> createChecklistItem(
            @PathVariable Long inspectionId,
            @Valid @RequestBody ChecklistItemRequest request) {

        ChecklistItemResponse response =
                checklistItemService.createChecklistItem(
                        inspectionId,
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/items/{id}")
    public ResponseEntity<ChecklistItemResponse> updateChecklistItem(
            @PathVariable Long id,
            @Valid @RequestBody ChecklistItemRequest request) {

        return ResponseEntity.ok(
                checklistItemService.updateChecklistItem(
                        id,
                        request
                )
        );
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> deleteChecklistItem(
            @PathVariable Long id) {

        checklistItemService.deleteChecklistItem(id);

        return ResponseEntity.noContent().build();
    }
}