package ru.petapp.taskmenagementsystem.taskmenagment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.petapp.taskmenagementsystem.taskmenagment.model.Priority;
import ru.petapp.taskmenagementsystem.taskmenagment.model.Status;
import ru.petapp.taskmenagementsystem.taskmenagment.model.User;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO
        extends GenericDTO{
    

    private String taskTitle;

    private String description;

    private Status status;

    private Priority priority;

    private String author;

    private UserDTO performer;

    private UserDTO user;
}
