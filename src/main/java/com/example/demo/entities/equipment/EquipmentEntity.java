package com.example.demo.entities.equipment;

import com.example.demo.entities.*;
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
    @Column(name = "state")
    private EquipmentState equipmentState;

    @JoinColumn(name = "organizational_unit_id")
    @OneToOne(cascade = CascadeType.ALL)
    private OrganizationalUnitEntity organizationalUnit;
    @JoinColumn(name = "organizational_group_id")
    @OneToOne(cascade = CascadeType.ALL)
    private OrganizationalGroupEntity organizationalGroup;

    @JoinColumn(name = "subject_id")
    @OneToOne(cascade = CascadeType.ALL)
    private SubjectEntity subject;
    @JoinColumn(name = "position_id")
    @OneToOne(cascade = CascadeType.ALL)
    private PositionEntity position;
    @JoinColumn(name = "responsibility_id")
    @OneToOne(cascade = CascadeType.ALL)
    private ResponsibilityEntity responsibility;


}
