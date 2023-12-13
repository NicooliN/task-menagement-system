package ru.petapp.taskmenagementsystem.taskmenagment.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginDTO {
    private String login;
    private String password;
}
