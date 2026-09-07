package com.dev.nigrani.reports.service;

import com.dev.nigrani.reports.models.Report;

import java.util.List;

public interface ReportService {
    List<Report> getReports(Long instituteId);
    Report getReportById(Long reportId);
    Report createReport(Report report);
    Report submitReport(Long reportId);
}
