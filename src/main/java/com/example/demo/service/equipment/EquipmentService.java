package com.example.demo.service.equipment;

import com.example.demo.daos.EquipmentDao;
import com.example.demo.entities.equipment.EquipmentEntity;
import com.example.demo.entities.equipment.EquipmentState;
import com.example.demo.service.equipment.exceptions.EquipmentCouldNotBeSavedException;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
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
    @Autowired
    private EquipmentMapper mapper;

    public List<EquipmentEntity> getAll() {
        return dao.findAll();
    }

    public Page<EquipmentEntity> getFilteredEquipment(
            Optional<String> state,
            Optional<String> organizationalUnitName,
            Optional<String> organizationalGroupName,
            Optional<String> subject,
            Optional<String> responsibleUser,
            Pageable pageable) {

        Optional<Specification<EquipmentEntity>> finalSpec = calculateActiveSpec(state, organizationalUnitName, organizationalGroupName, subject, responsibleUser);

        return finalSpec.map(equipmentEntitySpecification -> dao.findAll(equipmentEntitySpecification, pageable)).orElseGet(() -> dao.findAll(pageable));
    }

    public List<EquipmentEntity> getFilteredEquipmentAsList(
            Optional<String> state,
            Optional<String> organizationalUnitName,
            Optional<String> organizationalGroupName,
            Optional<String> subject,
            Optional<String> responsibleUser) {

        Optional<Specification<EquipmentEntity>> finalSpec = calculateActiveSpec(state, organizationalUnitName, organizationalGroupName, subject, responsibleUser);

        return finalSpec.map(equipmentEntitySpecification -> dao.findAll(equipmentEntitySpecification)).orElseGet(() -> dao.findAll());
    }

    private Optional<Specification<EquipmentEntity>> calculateActiveSpec(Optional<String> state, Optional<String> organizationalUnitName, Optional<String> organizationalGroupName, Optional<String> subject, Optional<String> responsibleUser) {
        Stream<Specification<EquipmentEntity>> activeSpecs = Stream.of(
                        state.map(EquipmentSpecification::hasState),
                        organizationalUnitName.map(EquipmentSpecification::hasOrganizationalUnitName),
                        organizationalGroupName.map(EquipmentSpecification::hasOrganizationalGroupName),
                        subject.map(EquipmentSpecification::hasSubjectName),
                        responsibleUser.map(EquipmentSpecification::hasResponsibility)
                ).filter(Optional::isPresent)
                .map(Optional::get);

        return activeSpecs.reduce(Specification::and);
    }

    @Transactional
    public EquipmentEntity create(EquipmentRequest request) {
        var entity = mapper.mapToEntity(request, new EquipmentEntity());
        checkOnSave(entity);
        return dao.save(entity);
    }

    @Transactional
    public EquipmentEntity save(EquipmentEntity entity) {
        checkOnSave(entity);
        return dao.save(entity);
    }

    public EquipmentEntity getById(String id) {
        return dao.findById(id).orElse(null);
    }

    @Transactional
    public void deleteById(String id) {
        var toDelete = dao.findById(id);

        if (toDelete.isPresent()) {
            dao.deleteById(id);
        }
    }

    public void writeToCsv(List<EquipmentEntity> entites, PrintWriter writer) {
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
            sb.append(equipment.getResponsibleUser() != null ? equipment.getResponsibleUser().getFirstname() +
                    equipment.getResponsibleUser().getLastname() : "");

            writer.println(sb);
        }
    }

    @SneakyThrows
    public void checkOnSave(EquipmentEntity entity) {
        if (entity.getEquipmentState().equals(EquipmentState.ON_LOAN)) {
            if (entity.getPosition() == null) {
                return;
            }
            throw new EquipmentCouldNotBeSavedException("Es darf keinen Ort bei Equipmentstatus = Ausgeliehen geben.");
        }
    }
}
