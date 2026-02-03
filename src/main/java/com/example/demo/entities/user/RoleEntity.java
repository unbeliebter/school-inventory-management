package com.example.demo.entities.user;

import com.example.demo.entities.IHasId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "roles")
@Data
public class RoleEntity implements IHasId {
    @Id
    private String id;
    private UserType name;
}
