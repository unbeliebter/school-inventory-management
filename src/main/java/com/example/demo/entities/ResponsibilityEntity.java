package com.example.demo.entities;

import com.example.demo.entities.user.UserEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "responsibility")
@Data
public class ResponsibilityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @JoinColumn(name = "user_id")
    @OneToOne(cascade = CascadeType.ALL)
    private UserEntity user;

}
