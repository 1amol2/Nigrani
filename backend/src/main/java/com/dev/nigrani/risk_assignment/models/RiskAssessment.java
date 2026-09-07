package com.dev.nigrani.risk_assignment.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/** One simple rule-based risk calculation for an institute. */
@Entity
@Table(name = "risk_assessments")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RiskAssessment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long instituteId;
    @Column(nullable = false, length = 200)
    private String instituteName;
    private Integer attendanceVariance;
    private boolean cctvOffline;
    private boolean unusualInactivity;
    private boolean restrictedAreaMovement;
    @Column(nullable = false)
    private Integer riskScore;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20)
    private RiskLevel riskLevel;
    @Column(nullable = false, length = 1000)
    private String reason;
    @Column(nullable = false, updatable = false)
    private LocalDateTime calculatedAt;
    @PrePersist
    private void setCalculatedAt() { calculatedAt = LocalDateTime.now(); }
}
