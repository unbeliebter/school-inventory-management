package de.schoolinventorymanagement.service.position;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PositionRequest {

    private String id;
    private String school;
    private String room;
    private String description;
}
