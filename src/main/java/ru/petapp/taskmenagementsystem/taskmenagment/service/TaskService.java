package ru.petapp.taskmenagementsystem.taskmenagment.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.petapp.taskmenagementsystem.taskmenagment.dto.TaskDTO;
import ru.petapp.taskmenagementsystem.taskmenagment.dto.UserDTO;
import ru.petapp.taskmenagementsystem.taskmenagment.mapper.GenericMapper;
import ru.petapp.taskmenagementsystem.taskmenagment.mapper.TaskMapper;
import ru.petapp.taskmenagementsystem.taskmenagment.model.Task;
import ru.petapp.taskmenagementsystem.taskmenagment.repository.GenericRepository;
import ru.petapp.taskmenagementsystem.taskmenagment.repository.TaskRepository;

import java.util.List;

@Service
public class TaskService extends GenericService<Task, TaskDTO> {

    private  final TaskRepository taskRepository;
    protected TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        super(taskRepository, taskMapper);
        this.taskRepository = taskRepository;
    }

    public Page<TaskDTO> getUserTask(Long id, Pageable pageable) {
       Page<Task> TaskPaginated = taskRepository.getTaskByUserId(id, pageable );
        List<TaskDTO> result = mapper.toDTOs(TaskPaginated.getContent());
        return new PageImpl<>(result, pageable, TaskPaginated.getTotalElements());
    }
}
