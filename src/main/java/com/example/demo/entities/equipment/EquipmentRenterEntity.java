package com.example.demo.entities.equipment;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "equipment_renter")
@Data
public class EquipmentRenterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @JoinColumn(name = "equipment_id")
    @OneToOne
    private EquipmentEntity equipment;
    private String renter;
}
