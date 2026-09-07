package com.dev.nigrani.reports.dtos;

import com.dev.nigrani.reports.models.ReportStatus;
import com.dev.nigrani.reports.models.Report;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportResponse {
    private Long id;
    private Long inspectionId;
    private Long instituteId;
    private String instituteName;
    private String title;
    private String summary;
    private String findings;
    private String recommendations;
    private Integer evidenceCount;
    private String preparedBy;
    private ReportStatus status;
    private LocalDateTime submittedAt;
    private LocalDateTime createdAt;

    public static ReportResponse fromModel(Report report) {
        return ReportResponse.builder()
                .id(report.getId())
                .inspectionId(report.getInspectionId())
                .instituteId(report.getInstituteId())
                .instituteName(report.getInstituteName())
                .title(report.getTitle())
                .summary(report.getSummary())
                .findings(report.getFindings())
                .recommendations(report.getRecommendations())
                .evidenceCount(report.getEvidenceCount())
                .preparedBy(report.getPreparedBy())
                .status(report.getStatus())
                .submittedAt(report.getSubmittedAt())
                .createdAt(report.getCreatedAt())
                .build();
    }
}
