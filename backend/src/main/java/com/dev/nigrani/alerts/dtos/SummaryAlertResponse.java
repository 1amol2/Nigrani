/*this is needed when u open alerts the three things at top */
package com.dev.nigrani.alerts.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SummaryAlertResponse {
//we did not want for every alert these three things we only want it once on our alert page
    private long totalAlerts;
    private long criticalAlerts;
    private long warningAlerts;
}
