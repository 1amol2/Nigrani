package com.dev.nigrani.evidence;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/inspections/{inspectionId}/evidence")
@RequiredArgsConstructor
public class EvidenceController {

    private final EvidenceService evidenceService;

    @GetMapping
    public ResponseEntity<List<EvidenceResponse>> getEvidence(
            @PathVariable Long inspectionId) {

        return ResponseEntity.ok(
                evidenceService.getEvidenceByInspectionId(inspectionId)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EvidenceResponse> getEvidenceById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                evidenceService.getEvidenceById(id)
        );
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EvidenceResponse> uploadEvidence(
            @PathVariable Long inspectionId,
            @Valid @RequestPart("metadata") EvidenceRequest request,
            @RequestPart("file") MultipartFile file) {

        EvidenceResponse response =
                evidenceService.uploadEvidence(
                        inspectionId,
                        request,
                        file
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvidence(
            @PathVariable Long id) {

        evidenceService.deleteEvidence(id);

        return ResponseEntity.noContent().build();
    }
}