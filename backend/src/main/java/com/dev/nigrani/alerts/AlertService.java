package com.dev.nigrani.alerts;

import com.dev.nigrani.alerts.dtos.AlertResponse;
import com.dev.nigrani.alerts.dtos.SummaryAlertResponse;
import com.dev.nigrani.alerts.dtos.CreateAlertRequest;
import com.dev.nigrani.alerts.dtos.SummaryAlertResponse;import com.dev.nigrani.alerts.models.AlertSeverity;

import java.util.List;


public interface AlertService {

    List<AlertResponse> getAlerts(AlertSeverity severity);

    AlertResponse getAlertById(Long alertId);

    SummaryAlertResponse getAlertSummary();

    AlertResponse createAlert(CreateAlertRequest request);
}
