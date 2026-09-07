package com.dev.nigrani.checklist;

import com.dev.nigrani.inspection.Inspection;
import com.dev.nigrani.inspection.InspectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChecklistItemService {

    private final ChecklistItemRepository checklistItemRepository;
    private final InspectionRepository inspectionRepository;

    public List<ChecklistItemResponse> getChecklistByInspectionId(
            Long inspectionId) {

        if (!inspectionRepository.existsById(inspectionId)) {
            throw new RuntimeException(
                    "Inspection not found with id: " + inspectionId
            );
        }

        return checklistItemRepository.findByInspectionId(inspectionId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ChecklistItemResponse getChecklistItemById(Long id) {

        ChecklistItem item = checklistItemRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Checklist item not found with id: " + id
                        )
                );

        return toResponse(item);
    }

    public ChecklistItemResponse createChecklistItem(
            Long inspectionId,
            ChecklistItemRequest request) {

        Inspection inspection = inspectionRepository.findById(inspectionId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Inspection not found with id: "
                                        + inspectionId
                        )
                );

        ChecklistItem item = ChecklistItem.builder()
                .inspection(inspection)
                .itemKey(request.getItemKey())
                .title(request.getTitle())
                .description(request.getDescription())
                .result(request.getResult())
                .remarks(request.getRemarks())
                .build();

        ChecklistItem savedItem =
                checklistItemRepository.save(item);

        return toResponse(savedItem);
    }

    public ChecklistItemResponse updateChecklistItem(
            Long id,
            ChecklistItemRequest request) {

        ChecklistItem item = checklistItemRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Checklist item not found with id: " + id
                        )
                );

        item.setItemKey(request.getItemKey());
        item.setTitle(request.getTitle());
        item.setDescription(request.getDescription());
        item.setResult(request.getResult());
        item.setRemarks(request.getRemarks());

        ChecklistItem updatedItem =
                checklistItemRepository.save(item);

        return toResponse(updatedItem);
    }

    public void deleteChecklistItem(Long id) {

        if (!checklistItemRepository.existsById(id)) {
            throw new RuntimeException(
                    "Checklist item not found with id: " + id
            );
        }

        checklistItemRepository.deleteById(id);
    }

    private ChecklistItemResponse toResponse(ChecklistItem item) {

        return ChecklistItemResponse.builder()
                .id(item.getId())
                .inspectionId(item.getInspection().getId())
                .itemKey(item.getItemKey())
                .title(item.getTitle())
                .description(item.getDescription())
                .result(item.getResult())
                .remarks(item.getRemarks())
                .createdAt(item.getCreatedAt())
                .updatedAt(item.getUpdatedAt())
                .build();
    }
}