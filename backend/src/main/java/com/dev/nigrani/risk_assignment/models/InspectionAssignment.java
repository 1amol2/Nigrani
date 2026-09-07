package com.dev.nigrani.risk_assignment.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/** Connects an inspection with the inspector assigned to do it. */
@Entity
@Table(name = "inspection_assignments")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class InspectionAssignment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long inspectionId;
    @Column(nullable = false)
    private Long instituteId;
    private Long riskAssessmentId;
    @Column(nullable = false)
    private Long inspectorId;
    @Column(nullable = false, length = 150)
    private String inspectorName;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20)
    private AssignmentType assignmentType;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20)
    private AssignmentStatus status;
    @Column(nullable = false)
    private LocalDateTime deadline;
    @Column(nullable = false, updatable = false)
    private LocalDateTime assignedAt;
    @PrePersist
    private void setAssignedAt() { assignedAt = LocalDateTime.now(); }
}
