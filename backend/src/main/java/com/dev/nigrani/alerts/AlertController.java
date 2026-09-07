package com.dev.nigrani.alerts;

import com.dev.nigrani.alerts.dtos.AlertResponse;
import com.dev.nigrani.alerts.dtos.SummaryAlertResponse;
import com.dev.nigrani.alerts.dtos.CreateAlertRequest;
import com.dev.nigrani.alerts.models.AlertSeverity;
import com.dev.nigrani.alerts.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
@RequiredArgsConstructor
public class AlertController {

    private final AlertService alertService;

    @GetMapping
    public List<AlertResponse> getAlerts(
            @RequestParam(required = false) AlertSeverity severity) {
        return alertService.getAlerts(severity);
    }

    @GetMapping("/summary")
    public SummaryAlertResponse getAlertSummary() {
        return alertService.getAlertSummary();
    }

    @GetMapping("/{alertId}")
    public AlertResponse getAlertById(@PathVariable Long alertId) {
        return alertService.getAlertById(alertId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlertResponse createAlert(@RequestBody CreateAlertRequest request) {
        return alertService.createAlert(request);
    }
}