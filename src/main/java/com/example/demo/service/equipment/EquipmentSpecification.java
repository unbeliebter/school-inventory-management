package com.example.demo.service.equipment;

import com.example.demo.entities.equipment.EquipmentEntity;
import com.example.demo.entities.equipment.EquipmentState;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.JoinType;

public class EquipmentSpecification {

    public static Specification<EquipmentEntity> hasItemName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(
                root.get("equipmentName"),
                name
        );
    }

    public static Specification<EquipmentEntity> hasState(String state) {
        var equipmentState = state == null ? null : EquipmentState.valueOf(state);
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("equipmentState"), equipmentState);
    }

    public static Specification<EquipmentEntity> hasOrganizationalUnitName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(
                root.join("organizationalUnit", JoinType.INNER).get("name"),
                name
        );
    }

    public static Specification<EquipmentEntity> hasOrganizationalGroupName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(
                root.join("organizationalGroup", JoinType.INNER).get("name"),
                name
        );
    }

    public static Specification<EquipmentEntity> hasSubjectName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(
                root.join("subject", JoinType.INNER).get("name"),
                name
        );
    }

    public static Specification<EquipmentEntity> hasResponsibility(String userId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(
                root.join("responsibleUser", JoinType.INNER).get("id"),
                userId
        );
    }

    public static Specification<EquipmentEntity> hasPosition(String positionId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(
                root.join("position", JoinType.INNER).get("id"),
                positionId
        );
    }
}