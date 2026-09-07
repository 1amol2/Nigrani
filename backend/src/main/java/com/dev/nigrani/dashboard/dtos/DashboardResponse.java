package com.dev.nigrani.dashboard.dtos;

import com.dev.nigrani.dashboard.models.DashboardSummary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponse {
    private Long activeInstitutes;
    private Long cctvOnline;
    private Long pendingInspections;
    private Long criticalAlerts;
    private boolean monitoringOperational;

    public static DashboardResponse fromModel(DashboardSummary summary) {
        return DashboardResponse.builder()
                .activeInstitutes(summary.getActiveInstitutes())
                .cctvOnline(summary.getCctvOnline())
                .pendingInspections(summary.getPendingInspections())
                .criticalAlerts(summary.getCriticalAlerts())
                .monitoringOperational(summary.isMonitoringOperational())
                .build();
    }
}
