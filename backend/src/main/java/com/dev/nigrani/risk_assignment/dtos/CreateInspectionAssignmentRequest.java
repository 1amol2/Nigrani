package com.dev.nigrani.risk_assignment.dtos;

import com.dev.nigrani.risk_assignment.models.AssignmentType;
import com.dev.nigrani.risk_assignment.models.AssignmentStatus;
import com.dev.nigrani.risk_assignment.models.InspectionAssignment;
import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor
public class CreateInspectionAssignmentRequest {
    private Long inspectionId;
    private Long instituteId;
    private Long riskAssessmentId;
    private Long inspectorId;
    private String inspectorName;
    private AssignmentType assignmentType;
    private LocalDateTime deadline;

    /** Converts assignment input into the model which will be saved by the service. */
    public InspectionAssignment toModel() {
        return InspectionAssignment.builder()
                .inspectionId(inspectionId)
                .instituteId(instituteId)
                .riskAssessmentId(riskAssessmentId)
                .inspectorId(inspectorId)
                .inspectorName(inspectorName)
                .assignmentType(assignmentType)
                .status(AssignmentStatus.ASSIGNED)
                .deadline(deadline)
                .build();
    }
}
