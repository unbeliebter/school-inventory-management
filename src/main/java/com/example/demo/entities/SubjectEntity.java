package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "subject")
@Data
public class SubjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String abbreviation;
}
