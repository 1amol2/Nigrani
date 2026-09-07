package com.dev.nigrani.risk_assignment.repository;

import com.dev.nigrani.risk_assignment.models.InspectionAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InspectionAssignmentRepository extends JpaRepository<InspectionAssignment, Long> {
    List<InspectionAssignment> findByInspectorIdOrderByAssignedAtDesc(Long inspectorId);
}
