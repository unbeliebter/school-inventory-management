package com.example.demo.entities;

import com.example.demo.entities.user.UserEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Table(name = "user_password_changes")
@Data
public class UserPasswordChangeEntity implements IHasId {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity userId;
    @Column(name = "password_before")
    private String passwordBefore;
    @Column(name = "password_after")
    private String passwordAfter;
    @Column(name = "changed_at")
    private Instant changedAt;
}
