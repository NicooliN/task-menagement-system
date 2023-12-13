package ru.petapp.taskmenagementsystem.taskmenagment.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO
      extends GenericDTO {
    private String login;
    private String password;
    private String email;
    private String birthDate;
    private String firstName;
    private String lastName;
    private String middleName;
    private String phone;
    private String address;
    private RoleDTO role;
    private String changePasswordToken;
    private Set<Long> userTasks;
    private boolean isDeleted;
}
