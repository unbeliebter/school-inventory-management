package com.example.demo.service.equipment;

import com.example.demo.daos.EquipmentDao;
import com.example.demo.entities.equipment.EquipmentEntity;
import com.example.demo.entities.equipment.EquipmentState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class EquipmentService {

    @Autowired
    private EquipmentDao dao;

    public Page<EquipmentEntity> getFilteredEquipment(
            Optional<EquipmentState> state,
            Optional<String> organizationalUnitName,
            Optional<String> organizationalGroupName,
            Optional<String> subject,
            Optional<String> responsibility,
            Pageable pageable)
    {
        // 1. Erstelle einen Stream von Optional-Spezifikationen
        Stream<Specification<EquipmentEntity>> activeSpecs = Stream.of(
                state.map(EquipmentSpecification::hasState),
                organizationalUnitName.map(EquipmentSpecification::hasOrganizationalUnitName),
                organizationalGroupName.map(EquipmentSpecification::hasOrganizationalGroupName),
                subject.map(EquipmentSpecification::hasSubjectName),
                responsibility.map(EquipmentSpecification::hasResponsibility)
        ).filter(Optional::isPresent)
                .map(Optional::get);

        Optional<Specification<EquipmentEntity>> finalSpec = activeSpecs.reduce(Specification::and);

        return finalSpec.map(equipmentEntitySpecification -> dao.findAll(equipmentEntitySpecification, pageable)).orElseGet(() -> dao.findAll(pageable));
    }

    public EquipmentEntity createOrUpdate(EquipmentEntity entity) {
        return dao.save(entity);
    }

    public EquipmentEntity getById(String id) {
        return dao.findById(id).orElse(null);
    }

    public void deleteById(String id) {
        var toDelete = dao.findById(id);

        if (toDelete.isPresent()) {
            dao.deleteById(id);
        }
    }
}
