package com.dev.nigrani.risk_assignment.controller;

import com.dev.nigrani.risk_assignment.dtos.*;
import com.dev.nigrani.risk_assignment.service.RiskAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/risk-assignments")
@RequiredArgsConstructor
public class RiskAssignmentController {
    private final RiskAssignmentService riskAssignmentService;

    /** POST /api/risk-assignments/risks calculates and saves a risk score. */
    @PostMapping("/risks")
    @ResponseStatus(HttpStatus.CREATED)
    public RiskAssessmentResponse calculateRisk(@RequestBody CalculateRiskRequest request) {
        return RiskAssessmentResponse.fromModel(
                riskAssignmentService.calculateRisk(request.toModel())
        );
    }

    /** GET /api/risk-assignments/risks or ?instituteId=1 */
    @GetMapping("/risks")
    public List<RiskAssessmentResponse> getRiskAssessments(@RequestParam(required = false) Long instituteId) {
        return riskAssignmentService.getRiskAssessments(instituteId).stream()
                .map(RiskAssessmentResponse::fromModel)
                .toList();
    }

    /** POST /api/risk-assignments/assignments assigns an inspection to an inspector. */
    @PostMapping("/assignments")
    @ResponseStatus(HttpStatus.CREATED)
    public InspectionAssignmentResponse createAssignment(@RequestBody CreateInspectionAssignmentRequest request) {
        return InspectionAssignmentResponse.fromModel(
                riskAssignmentService.createAssignment(request.toModel())
        );
    }

    /** GET /api/risk-assignments/assignments or ?inspectorId=1 */
    @GetMapping("/assignments")
    public List<InspectionAssignmentResponse> getAssignments(@RequestParam(required = false) Long inspectorId) {
        return riskAssignmentService.getAssignments(inspectorId).stream()
                .map(InspectionAssignmentResponse::fromModel)
                .toList();
    }
}
