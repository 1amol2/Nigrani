package com.dev.nigrani.dashboard.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** Plain model used to carry Dashboard numbers; it is not a database table. */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardSummary {
    private Long activeInstitutes;
    private Long cctvOnline;
    private Long pendingInspections;
    private Long criticalAlerts;
    private boolean monitoringOperational;
}
