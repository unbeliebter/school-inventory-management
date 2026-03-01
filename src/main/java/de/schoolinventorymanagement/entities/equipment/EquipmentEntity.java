package de.schoolinventorymanagement.entities.equipment;

import de.schoolinventorymanagement.entities.OrganizationalGroupEntity;
import de.schoolinventorymanagement.entities.OrganizationalUnitEntity;
import de.schoolinventorymanagement.entities.PositionEntity;
import de.schoolinventorymanagement.entities.SubjectEntity;
import de.schoolinventorymanagement.entities.user.UserEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "equipment")
@Data
public class EquipmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "inventory_number")
    private String inventoryNumber;
    @Column(name = "equipment_name")
    private String equipmentName;
    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private EquipmentState equipmentState;

    @JoinColumn(name = "organizational_unit_id")
    @OneToOne
    private OrganizationalUnitEntity organizationalUnit;
    @JoinColumn(name = "organizational_group_id")
    @OneToOne
    private OrganizationalGroupEntity organizationalGroup;

    @JoinColumn(name = "subject_id")
    @OneToOne
    private SubjectEntity subject;
    @JoinColumn(name = "position_id")
    @OneToOne
    private PositionEntity position;
    @JoinColumn(name = "responsible_user_id")
    @OneToOne
    private UserEntity responsibleUser;


}
