package com.example.demo.service.equipment;

import com.example.demo.daos.EquipmentDao;
import com.example.demo.entities.equipment.EquipmentEntity;
import com.example.demo.entities.equipment.EquipmentState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.util.List;
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
            Pageable pageable) {

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

    public void writeEquipmentToCsv(List<EquipmentEntity> entites, PrintWriter writer) {
        writer.println("ID,Inventarnummer,Name,Zustand,Abteilung,Gruppe,Fach,Ort,Verantwortlicher");

        for (EquipmentEntity equipment : entites) {
            StringBuilder sb = new StringBuilder();

            sb.append(equipment.getId()).append(",");
            sb.append(equipment.getInventoryNumber()).append(",");
            sb.append(equipment.getEquipmentName()).append(",");
            sb.append(equipment.getEquipmentState().name()).append(","); // Enum-Name


            sb.append(equipment.getOrganizationalUnit() != null ? equipment.getOrganizationalUnit().getName() : "")
                    .append(",");
            sb.append(equipment.getOrganizationalGroup() != null ? equipment.getOrganizationalGroup().getName() : "")
                    .append(",");
            sb.append(equipment.getSubject() != null ? equipment.getSubject().getName() : "").append(",");
            sb.append(equipment.getPosition() != null ? equipment.getPosition().getSchool() + " - " +
                    equipment.getPosition().getRoom() : "")
                    .append(",");
            sb.append(equipment.getResponsibility() != null ? equipment.getResponsibility().getUser().getFirstname() +
                    equipment.getResponsibility().getUser().getLastname() : "");

            writer.println(sb);
        }
    }
}
