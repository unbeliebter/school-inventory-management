package com.example.demo.entities.user;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String username;
    private String password;
    @Column(name = "e_mail")
    private String email;
    private String firstname;
    private String lastname;
    @Enumerated(EnumType.STRING)
    @Column(name = "usertype")
    private UserType userType;

}
