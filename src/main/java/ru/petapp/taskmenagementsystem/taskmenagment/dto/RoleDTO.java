package ru.petapp.taskmenagementsystem.taskmenagment.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


public class RoleDTO
        extends GenericDTO {

    private Long id;

    private String title;

    private String description;
    
}
