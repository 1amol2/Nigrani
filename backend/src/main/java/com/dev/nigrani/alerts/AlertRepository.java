
package com.dev.nigrani.alerts;

import com.dev.nigrani.alerts.models.Alert;
import com.dev.nigrani.alerts.models.AlertSeverity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository extends JpaRepository<Alert, Long> {

    long countBySeverity(AlertSeverity severity);
}