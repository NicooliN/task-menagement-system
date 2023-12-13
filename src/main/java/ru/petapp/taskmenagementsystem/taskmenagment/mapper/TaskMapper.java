package ru.petapp.taskmenagementsystem.taskmenagment.mapper;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.petapp.taskmenagementsystem.taskmenagment.dto.TaskDTO;
import ru.petapp.taskmenagementsystem.taskmenagment.model.Task;
import ru.petapp.taskmenagementsystem.taskmenagment.repository.TaskRepository;

import java.util.Set;

@Component
public class TaskMapper
        extends GenericMapper<Task, TaskDTO> {

    private final TaskRepository taskRepository;
    public TaskMapper(ModelMapper mapper,
                      TaskRepository taskRepository) {
        super(mapper, Task.class, TaskDTO.class);
        this.taskRepository = taskRepository;
    }

    @PostConstruct
    @Override
    protected void setupMapper() {

    }
    @Override
    protected void mapSpecificFields(TaskDTO source, Task destination) {

    }

    @Override
    protected void mapSpecificFields(Task source, TaskDTO destination) {

    }

    @Override
    protected Set<Long> getIds(Task entity) {
        return null;
    }

}
