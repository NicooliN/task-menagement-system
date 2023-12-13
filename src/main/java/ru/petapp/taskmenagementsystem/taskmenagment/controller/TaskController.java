package ru.petapp.taskmenagementsystem.taskmenagment.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.petapp.taskmenagementsystem.taskmenagment.dto.TaskDTO;
import ru.petapp.taskmenagementsystem.taskmenagment.dto.UserDTO;
import ru.petapp.taskmenagementsystem.taskmenagment.model.Task;
import ru.petapp.taskmenagementsystem.taskmenagment.service.GenericService;
import ru.petapp.taskmenagementsystem.taskmenagment.service.TaskService;

import java.util.List;
@Tag(name = "Задания",
        description = "контроллер для работы с заданиями")
@Slf4j
@SecurityRequirement(name = "Bearer Authentication")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/tasks")
public class TaskController
        extends GenericController<Task, TaskDTO> {

    private TaskService taskService;
    public TaskController(TaskService taskService) {
        super(taskService);

    }

    @GetMapping(value = "/getUserTasks")
    public ResponseEntity<Page<TaskDTO>> getUserTasks(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "id") Long id) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.ASC, "taskTitle"));
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getUserTask(id, pageRequest));
    }
}
