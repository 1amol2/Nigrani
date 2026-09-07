package com.dev.nigrani.reports.controller;

import com.dev.nigrani.reports.dtos.CreateReportRequest;
import com.dev.nigrani.reports.dtos.ReportResponse;
import com.dev.nigrani.reports.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;


    @GetMapping
    public List<ReportResponse> getReports(@RequestParam(required = false) Long instituteId) {
        return reportService.getReports(instituteId).stream()
                .map(ReportResponse::fromModel)
                .toList();
    }


    @GetMapping("/{reportId}")
    public ReportResponse getReportById(@PathVariable Long reportId) {
        return ReportResponse.fromModel(reportService.getReportById(reportId));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReportResponse createReport(@RequestBody CreateReportRequest request) {
        return ReportResponse.fromModel(reportService.createReport(request.toModel()));
    }


    @PutMapping("/{reportId}/submit")
    public ReportResponse submitReport(@PathVariable Long reportId) {
        return ReportResponse.fromModel(reportService.submitReport(reportId));
    }
}
