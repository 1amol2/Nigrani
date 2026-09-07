package com.dev.nigrani.evidence;

import com.dev.nigrani.inspection.Inspection;
import com.dev.nigrani.inspection.InspectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EvidenceService {

    private final EvidenceRepository evidenceRepository;
    private final InspectionRepository inspectionRepository;

    private final Path storageDirectory =
            Paths.get("uploads/evidence");

    public List<EvidenceResponse> getEvidenceByInspectionId(
            Long inspectionId) {

        if (!inspectionRepository.existsById(inspectionId)) {
            throw new RuntimeException(
                    "Inspection not found with id: " + inspectionId
            );
        }

        return evidenceRepository.findByInspectionId(inspectionId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public EvidenceResponse getEvidenceById(Long id) {

        Evidence evidence = evidenceRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Evidence not found with id: " + id
                        )
                );

        return toResponse(evidence);
    }

    public EvidenceResponse uploadEvidence(
            Long inspectionId,
            EvidenceRequest request,
            MultipartFile file) {

        Inspection inspection = inspectionRepository.findById(inspectionId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Inspection not found with id: "
                                        + inspectionId
                        )
                );

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException(
                    "Evidence file cannot be empty"
            );
        }

        try {
            Files.createDirectories(storageDirectory);

            String originalFileName = file.getOriginalFilename();

            String safeFileName =
                    originalFileName == null
                            ? "evidence"
                            : Paths.get(originalFileName)
                            .getFileName()
                            .toString();

            String storedFileName =
                    UUID.randomUUID() + "_" + safeFileName;

            Path targetPath =
                    storageDirectory.resolve(storedFileName);

            Files.copy(
                    file.getInputStream(),
                    targetPath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            Evidence evidence = Evidence.builder()
                    .inspection(inspection)
                    .category(request.getCategory())
                    .fileName(safeFileName)
                    .storageReference(targetPath.toString())
                    .contentType(file.getContentType())
                    .capturedAt(
                            request.getCapturedAt() != null
                                    ? request.getCapturedAt()
                                    : Instant.now()
                    )
                    .latitude(request.getLatitude())
                    .longitude(request.getLongitude())
                    .build();

            Evidence savedEvidence =
                    evidenceRepository.save(evidence);

            return toResponse(savedEvidence);

        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to store evidence file",
                    e
            );
        }
    }

    public void deleteEvidence(Long id) {

        Evidence evidence = evidenceRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Evidence not found with id: " + id
                        )
                );

        try {
            Files.deleteIfExists(
                    Paths.get(evidence.getStorageReference())
            );
        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to delete evidence file",
                    e
            );
        }

        evidenceRepository.delete(evidence);
    }

    private EvidenceResponse toResponse(Evidence evidence) {

        return EvidenceResponse.builder()
                .id(evidence.getId())
                .inspectionId(evidence.getInspection().getId())
                .category(evidence.getCategory())
                .fileName(evidence.getFileName())
                .storageReference(evidence.getStorageReference())
                .contentType(evidence.getContentType())
                .capturedAt(evidence.getCapturedAt())
                .latitude(evidence.getLatitude())
                .longitude(evidence.getLongitude())
                .createdAt(evidence.getCreatedAt())
                .build();
    }
}