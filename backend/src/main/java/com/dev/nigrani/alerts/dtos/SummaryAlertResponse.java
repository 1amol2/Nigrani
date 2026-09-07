/*this is needed when u open alerts the three things at top */
package com.dev.nigrani.alerts.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SummaryAlertResponse {

    private long totalAlerts;
    private long criticalAlerts;
    private long warningAlerts;
}
