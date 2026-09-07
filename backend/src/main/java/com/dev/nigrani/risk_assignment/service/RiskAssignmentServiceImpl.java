package com.dev.nigrani.risk_assignment.service;

import com.dev.nigrani.risk_assignment.models.*;
import com.dev.nigrani.risk_assignment.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RiskAssignmentServiceImpl implements RiskAssignmentService {
    private final RiskAssessmentRepository riskAssessmentRepository;
    private final InspectionAssignmentRepository assignmentRepository;

    @Override
    public RiskAssessment calculateRisk(RiskAssessment assessment) {
        if (assessment.getInstituteId() == null || isBlank(assessment.getInstituteName())) {
            throw new IllegalArgumentException("Institute ID and institute name are required");
        }

        int score = 0;
        List<String> reasons = new ArrayList<>();
        int variance = assessment.getAttendanceVariance() == null ? 0 : assessment.getAttendanceVariance();
        if (variance >= 20) { score += 40; reasons.add("Attendance variance: " + variance + "%"); }
        else if (variance >= 10) { score += 25; reasons.add("Attendance variance: " + variance + "%"); }
        if (assessment.isCctvOffline()) { score += 30; reasons.add("CCTV offline"); }
        if (assessment.isUnusualInactivity()) { score += 15; reasons.add("Unusual inactivity"); }
        if (assessment.isRestrictedAreaMovement()) { score += 30; reasons.add("Restricted-area movement"); }
        score = Math.min(score, 100);

        assessment.setRiskScore(score);
        assessment.setRiskLevel(toRiskLevel(score));
        assessment.setReason(reasons.isEmpty() ? "No risk signals detected" : String.join("; ", reasons));
        return riskAssessmentRepository.save(assessment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RiskAssessment> getRiskAssessments(Long instituteId) {
        List<RiskAssessment> assessments = instituteId == null ? riskAssessmentRepository.findAll()
                : riskAssessmentRepository.findByInstituteIdOrderByCalculatedAtDesc(instituteId);
        return assessments;
    }

    @Override
    public InspectionAssignment createAssignment(InspectionAssignment assignment) {
        if (assignment.getInspectionId() == null || assignment.getInstituteId() == null
                || assignment.getInspectorId() == null || isBlank(assignment.getInspectorName())
                || assignment.getAssignmentType() == null || assignment.getDeadline() == null) {
            throw new IllegalArgumentException("Inspection, institute, inspector, type, and deadline are required");
        }
        return assignmentRepository.save(assignment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InspectionAssignment> getAssignments(Long inspectorId) {
        List<InspectionAssignment> assignments = inspectorId == null ? assignmentRepository.findAll()
                : assignmentRepository.findByInspectorIdOrderByAssignedAtDesc(inspectorId);
        return assignments;
    }

    private RiskLevel toRiskLevel(int score) {
        if (score >= 70) return RiskLevel.CRITICAL;
        if (score >= 50) return RiskLevel.HIGH;
        if (score >= 25) return RiskLevel.MEDIUM;
        return RiskLevel.LOW;
    }

    private boolean isBlank(String value) { return value == null || value.isBlank(); }

}
