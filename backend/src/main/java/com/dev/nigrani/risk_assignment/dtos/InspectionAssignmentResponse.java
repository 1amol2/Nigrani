package com.dev.nigrani.risk_assignment.dtos;

import com.dev.nigrani.risk_assignment.models.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter @NoArgsConstructor @AllArgsConstructor @Builder
public class InspectionAssignmentResponse {
    private Long id;
    private Long inspectionId;
    private Long instituteId;
    private Long riskAssessmentId;
    private Long inspectorId;
    private String inspectorName;
    private AssignmentType assignmentType;
    private AssignmentStatus status;
    private LocalDateTime deadline;
    private LocalDateTime assignedAt;

    /** Converts the saved assignment model into the response returned to Android. */
    public static InspectionAssignmentResponse fromModel(InspectionAssignment assignment) {
        return InspectionAssignmentResponse.builder()
                .id(assignment.getId())
                .inspectionId(assignment.getInspectionId())
                .instituteId(assignment.getInstituteId())
                .riskAssessmentId(assignment.getRiskAssessmentId())
                .inspectorId(assignment.getInspectorId())
                .inspectorName(assignment.getInspectorName())
                .assignmentType(assignment.getAssignmentType())
                .status(assignment.getStatus())
                .deadline(assignment.getDeadline())
                .assignedAt(assignment.getAssignedAt())
                .build();
    }
}
