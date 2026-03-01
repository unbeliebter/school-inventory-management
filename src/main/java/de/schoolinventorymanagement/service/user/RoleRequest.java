package de.schoolinventorymanagement.service.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RoleRequest {

    private String id;
    private String name;
    private String frontendName;
}
