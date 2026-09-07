package com.dev.nigrani.reports.repository;

import com.dev.nigrani.reports.models.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByInstituteIdOrderByCreatedAtDesc(Long instituteId);
}