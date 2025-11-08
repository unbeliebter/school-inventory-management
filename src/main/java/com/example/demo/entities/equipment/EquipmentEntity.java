package com.example.demo.entities.equipment;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "equipment")
@Data
public class EquipmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

}
