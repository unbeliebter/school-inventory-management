package com.example.demo.service.equipment;

import com.example.demo.entities.equipment.EquipmentEntity;
import com.example.demo.entities.equipment.EquipmentState;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.JoinType;

public class EquipmentSpecification {

    public static Specification<EquipmentEntity> hasState(EquipmentState state) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("equipmentState"), state);
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
}