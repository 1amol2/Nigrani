package com.dev.nigrani.reports.service;

import com.dev.nigrani.reports.models.Report;
import com.dev.nigrani.reports.models.ReportStatus;
import com.dev.nigrani.reports.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Report> getReports(Long instituteId) {

        if (instituteId == null) {
            return reportRepository.findAll();
        }

        return reportRepository.findByInstituteIdOrderByCreatedAtDesc(instituteId);
    }

    @Override
    @Transactional(readOnly = true)
    public Report getReportById(Long reportId) {

        return reportRepository.findById(reportId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Report not found: " + reportId)
                );
    }

    @Override
    public Report createReport(Report report) {

        if (report.getInspectionId() == null
                || report.getInstituteId() == null
                || isBlank(report.getInstituteName())
                || isBlank(report.getTitle())
                || isBlank(report.getSummary())
                || isBlank(report.getPreparedBy())) {

            throw new IllegalArgumentException(
                    "Inspection, institute, title, summary, and preparer are required"
            );
        }

        return reportRepository.save(report);
    }

    @Override
    public Report submitReport(Long reportId) {

        Report report = getReportById(reportId);

        report.setStatus(ReportStatus.SUBMITTED);
        report.setSubmittedAt(LocalDateTime.now());

        return reportRepository.save(report);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}