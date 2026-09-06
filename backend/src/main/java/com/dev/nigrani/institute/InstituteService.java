package com.dev.nigrani.institute;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstituteService {

    private final InstituteRepository instituteRepository;

    public InstituteService(InstituteRepository instituteRepository) {
        this.instituteRepository = instituteRepository;
    }

    public List<InstituteResponse> getAllInstitutes() {
        return instituteRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public InstituteResponse getInstituteById(Long id) {
        Institute institute = instituteRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Institute not found with id: " + id)
                );

        return toResponse(institute);
    }

    public InstituteResponse createInstitute(InstituteRequest request) {

        Institute institute = Institute.builder()
                .name(request.getName())
                .location(request.getLocation())
                .type(request.getType())
                .beneficiaries(request.getBeneficiaries())
                .attendancePercentage(request.getAttendancePercentage())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .cctvOnline(request.getCctvOnline())
                .status(request.getStatus())
                .build();

        Institute savedInstitute = instituteRepository.save(institute);

        return toResponse(savedInstitute);
    }

    public InstituteResponse updateInstitute(Long id, InstituteRequest request) {

        Institute institute = instituteRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Institute not found with id: " + id)
                );

        institute.setName(request.getName());
        institute.setLocation(request.getLocation());
        institute.setType(request.getType());
        institute.setBeneficiaries(request.getBeneficiaries());
        institute.setAttendancePercentage(request.getAttendancePercentage());
        institute.setLatitude(request.getLatitude());
        institute.setLongitude(request.getLongitude());
        institute.setCctvOnline(request.getCctvOnline());
        institute.setStatus(request.getStatus());

        Institute updatedInstitute = instituteRepository.save(institute);

        return toResponse(updatedInstitute);
    }

    public void deleteInstitute(Long id) {

        if (!instituteRepository.existsById(id)) {
            throw new RuntimeException(
                    "Institute not found with id: " + id
            );
        }

        instituteRepository.deleteById(id);
    }

    private InstituteResponse toResponse(Institute institute) {

        return InstituteResponse.builder()
                .id(institute.getId())
                .name(institute.getName())
                .location(institute.getLocation())
                .type(institute.getType())
                .beneficiaries(institute.getBeneficiaries())
                .attendancePercentage(institute.getAttendancePercentage())
                .latitude(institute.getLatitude())
                .longitude(institute.getLongitude())
                .cctvOnline(institute.getCctvOnline())
                .status(institute.getStatus())
                .lastChecked(institute.getLastChecked())
                .createdAt(institute.getCreatedAt())
                .updatedAt(institute.getUpdatedAt())
                .build();
    }
}