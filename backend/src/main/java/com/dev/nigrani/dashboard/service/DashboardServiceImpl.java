package com.dev.nigrani.dashboard.service;

import com.dev.nigrani.alerts.AlertRepository;
import com.dev.nigrani.alerts.models.AlertSeverity;
import com.dev.nigrani.dashboard.models.DashboardSummary;
import com.dev.nigrani.institute.InstituteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {
    private final InstituteRepository instituteRepository;
    private final AlertRepository alertRepository;

    @Override
    public DashboardSummary getDashboardSummary() {
        long totalInstitutes = instituteRepository.count();
        long onlineCctv = instituteRepository.countByCctvOnlineTrue();
        long criticalAlerts = alertRepository.countBySeverity(AlertSeverity.CRITICAL);

        return DashboardSummary.builder()
                .activeInstitutes(totalInstitutes)
                .cctvOnline(onlineCctv)
                .pendingInspections(0L)
                .criticalAlerts(criticalAlerts)
                .monitoringOperational(true)
                .build();
    }
}
