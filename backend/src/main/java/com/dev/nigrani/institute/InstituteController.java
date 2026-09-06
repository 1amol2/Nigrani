package com.dev.nigrani.institute;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/institutes")
public class InstituteController {

    private final InstituteService instituteService;

    public InstituteController(InstituteService instituteService) {
        this.instituteService = instituteService;
    }

    @GetMapping
    public ResponseEntity<List<InstituteResponse>> getAllInstitutes() {
        return ResponseEntity.ok(instituteService.getAllInstitutes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstituteResponse> getInstituteById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                instituteService.getInstituteById(id)
        );
    }

    @PostMapping
    public ResponseEntity<InstituteResponse> createInstitute(
            @Valid @RequestBody InstituteRequest request) {

        InstituteResponse response =
                instituteService.createInstitute(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InstituteResponse> updateInstitute(
            @PathVariable Long id,
            @Valid @RequestBody InstituteRequest request) {

        return ResponseEntity.ok(
                instituteService.updateInstitute(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstitute(
            @PathVariable Long id) {

        instituteService.deleteInstitute(id);

        return ResponseEntity.noContent().build();
    }
}