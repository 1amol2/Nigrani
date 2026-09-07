package com.dev.nigrani.risk_assignment.repository;

import com.dev.nigrani.risk_assignment.models.RiskAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RiskAssessmentRepository extends JpaRepository<RiskAssessment, Long> {
    List<RiskAssessment> findByInstituteIdOrderByCalculatedAtDesc(Long instituteId);
}
