package com.dev.nigrani.alerts.models;

public enum AlertStatus {
    /** new alert and still needs attention. */
    OPEN,

    /** alert seen by officer but has not assigned an inspection yet. */
    ACKNOWLEDGED,

    /**inspection has been assigned from this alert. */
    INSPECTION_ASSIGNED,

    /** problem solved and alert is closed. */
    RESOLVED
}
