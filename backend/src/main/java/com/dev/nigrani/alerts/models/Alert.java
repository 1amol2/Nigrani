package com.dev.nigrani.alerts.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

    @Entity
    @Table(name = "alerts")/*table name is alerts*/
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class Alert {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, length = 150)
        private String title;

        @Column(nullable = false, length = 1000)
        private String description;

        @Column(nullable = false)
        private Long instituteId;


        @Column(nullable = false, length = 200)
        private String instituteName;

        @Column(nullable = false, length = 200)
        private String location;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false, length = 40)
        private AlertType type;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false, length = 20)
        private AlertSeverity severity;

        @Column(nullable = false)
        private Integer confidenceScore;

        @Column(nullable = false)
        private Integer riskScore;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false, length = 30)
        private AlertStatus status;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false, length = 20)
        private AlertSource source;

        @Column(length = 500)
        private String evidenceReference;

        private Integer reportedValue;
        private Integer detectedValue;
        private Integer variance;

        @Column(nullable = false, updatable = false)
        private LocalDateTime detectedAt;

        @Column(nullable = false, updatable = false)
        private LocalDateTime createdAt;

        private LocalDateTime updatedAt;

        @PrePersist
        private void setCreationTimestamps() {
            LocalDateTime now = LocalDateTime.now();
            if (detectedAt == null) {
                detectedAt = now;
            }
            createdAt = now;
            updatedAt = now;
        }

        @PreUpdate
        private void setUpdateTimestamp() {
            updatedAt = LocalDateTime.now();
        }
    }

