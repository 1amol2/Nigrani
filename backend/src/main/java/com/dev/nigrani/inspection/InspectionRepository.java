package com.dev.nigrani.inspection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface InspectionRepository extends JpaRepository<Inspection, Long> {
}