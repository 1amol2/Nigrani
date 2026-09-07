package com.dev.nigrani.reports.dtos;

import com.dev.nigrani.reports.models.Report;
import com.dev.nigrani.reports.models.ReportStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateReportRequest {
    private Long inspectionId;
    private Long instituteId;
    private String instituteName;
    private String title;
    private String summary;
    private String findings;
    private String recommendations;
    private Integer evidenceCount;
    private String preparedBy;


    public Report toModel() {
        return Report.builder()
                .inspectionId(inspectionId)
                .instituteId(instituteId)
                .instituteName(instituteName)
                .title(title)
                .summary(summary)
                .findings(findings)
                .recommendations(recommendations)
                .evidenceCount(evidenceCount == null ? 0 : evidenceCount)
                .preparedBy(preparedBy)
                .status(ReportStatus.DRAFT)
                .build();
    }
}


