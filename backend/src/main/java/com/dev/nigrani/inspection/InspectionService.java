package com.dev.nigrani.inspection;

import com.dev.nigrani.institute.Institute;
import com.dev.nigrani.institute.InstituteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InspectionService {

    private final InspectionRepository inspectionRepository;
    private final InstituteRepository instituteRepository;


    public List<InspectionResponse> getAllInspections() {

        return inspectionRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public InspectionResponse getInspectionById(Long id) {

        Inspection inspection = inspectionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Inspection not found with id: " + id
                        )
                );

        return toResponse(inspection);
    }

    public InspectionResponse createInspection(
            InspectionRequest request) {

        Institute institute = instituteRepository.findById(
                        request.getInstituteId()
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Institute not found with id: "
                                        + request.getInstituteId()
                        )
                );

        Inspection inspection = Inspection.builder()
                .institute(institute)
                .inspectionDate(request.getInspectionDate())
                .inspectorId(request.getInspectorId())
                .type(request.getType())
                .priority(request.getPriority())
                .reason(request.getReason())
                .allowedRadiusMeters(request.getAllowedRadiusMeters())
                .status(request.getStatus())
                .overallRemarks(request.getOverallRemarks())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .locationVerified(request.getLocationVerified())
                .startedAt(request.getStartedAt())
                .completedAt(request.getCompletedAt())
                .build();

        Inspection savedInspection =
                inspectionRepository.save(inspection);

        return toResponse(savedInspection);
    }

    public InspectionResponse updateInspection(
            Long id,
            InspectionRequest request) {

        Inspection inspection = inspectionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Inspection not found with id: " + id
                        )
                );

        Institute institute = instituteRepository.findById(
                        request.getInstituteId()
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Institute not found with id: "
                                        + request.getInstituteId()
                        )
                );

        inspection.setInstitute(institute);
        inspection.setInspectionDate(request.getInspectionDate());
        inspection.setInspectorId(request.getInspectorId());
        inspection.setType(request.getType());
        inspection.setPriority(request.getPriority());
        inspection.setReason(request.getReason());
        inspection.setAllowedRadiusMeters(request.getAllowedRadiusMeters());
        inspection.setStatus(request.getStatus());
        inspection.setOverallRemarks(request.getOverallRemarks());
        inspection.setLatitude(request.getLatitude());
        inspection.setLongitude(request.getLongitude());
        inspection.setLocationVerified(request.getLocationVerified());
        inspection.setStartedAt(request.getStartedAt());
        inspection.setCompletedAt(request.getCompletedAt());

        Inspection updatedInspection =
                inspectionRepository.save(inspection);

        return toResponse(updatedInspection);
    }

    public void deleteInspection(Long id) {

        if (!inspectionRepository.existsById(id)) {
            throw new RuntimeException(
                    "Inspection not found with id: " + id
            );
        }

        inspectionRepository.deleteById(id);
    }

    private InspectionResponse toResponse(
            Inspection inspection) {

        return InspectionResponse.builder()
                .id(inspection.getId())
                .instituteId(
                        inspection.getInstitute().getId()
                )
                .instituteName(
                        inspection.getInstitute().getName()
                )
                .inspectionDate(
                        inspection.getInspectionDate()
                )
                .inspectorId(
                        inspection.getInspectorId()
                )
                .type(inspection.getType())
                .priority(inspection.getPriority())
                .reason(inspection.getReason())
                .allowedRadiusMeters(inspection.getAllowedRadiusMeters())
                .status(
                        inspection.getStatus()
                )
                .overallRemarks(
                        inspection.getOverallRemarks()
                )
                .latitude(
                        inspection.getLatitude()
                )
                .longitude(
                        inspection.getLongitude()
                )
                .locationVerified(
                        inspection.getLocationVerified()
                )
                .startedAt(
                        inspection.getStartedAt()
                )
                .completedAt(
                        inspection.getCompletedAt()
                )
                .createdAt(
                        inspection.getCreatedAt()
                )
                .updatedAt(
                        inspection.getUpdatedAt()
                )
                .build();
    }
    @Transactional
    public InspectionResponse completeInspection(Long id) {

        Inspection inspection = inspectionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Inspection not found with id: " + id
                        )
                );

        Instant now = Instant.now();

        inspection.setStatus("COMPLETED");
        inspection.setCompletedAt(now);

        Institute institute = inspection.getInstitute();
        institute.setLastChecked(now);

        instituteRepository.save(institute);

        Inspection completedInspection =
                inspectionRepository.save(inspection);

        return toResponse(completedInspection);
    }
}