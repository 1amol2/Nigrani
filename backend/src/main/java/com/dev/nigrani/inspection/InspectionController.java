package com.dev.nigrani.inspection;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inspections")
@RequiredArgsConstructor
public class InspectionController {

    private final InspectionService inspectionService;



    @GetMapping
    public ResponseEntity<List<InspectionResponse>> getAllInspections() {
        return ResponseEntity.ok(
                inspectionService.getAllInspections()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<InspectionResponse> getInspectionById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                inspectionService.getInspectionById(id)
        );
    }

    @PostMapping
    public ResponseEntity<InspectionResponse> createInspection(
            @Valid @RequestBody InspectionRequest request) {

        InspectionResponse response =
                inspectionService.createInspection(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InspectionResponse> updateInspection(
            @PathVariable Long id,
            @Valid @RequestBody InspectionRequest request) {

        return ResponseEntity.ok(
                inspectionService.updateInspection(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInspection(
            @PathVariable Long id) {

        inspectionService.deleteInspection(id);

        return ResponseEntity.noContent().build();
    }
}