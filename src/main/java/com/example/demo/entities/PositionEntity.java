package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "position")
@Data
public class PositionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String school;
    private String room;
    private String description;
}
