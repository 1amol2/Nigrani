package com.dev.nigrani.evidence;

import com.dev.nigrani.inspection.Inspection;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "evidence")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Evidence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "inspection_id", nullable = false)
    private Inspection inspection;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String storageReference;

    @Column(nullable = false)
    private String contentType;

    @Column(nullable = false)
    private Instant capturedAt;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    protected void onCreate() {
        if (capturedAt == null) {
            capturedAt = Instant.now();
        }

        createdAt = Instant.now();
    }
}