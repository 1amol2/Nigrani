package com.dev.nigrani.risk_assignment.service;

import com.dev.nigrani.risk_assignment.models.InspectionAssignment;
import com.dev.nigrani.risk_assignment.models.RiskAssessment;
import java.util.List;

public interface RiskAssignmentService {
    RiskAssessment calculateRisk(RiskAssessment assessment);
    List<RiskAssessment> getRiskAssessments(Long instituteId);
    InspectionAssignment createAssignment(InspectionAssignment assignment);
    List<InspectionAssignment> getAssignments(Long inspectorId);
}
