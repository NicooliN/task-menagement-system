package ru.petapp.taskmenagementsystem.taskmenagment.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.petapp.taskmenagementsystem.taskmenagment.dto.UserDTO;
import ru.petapp.taskmenagementsystem.taskmenagment.model.User;
import ru.petapp.taskmenagementsystem.taskmenagment.repository.TaskRepository;
import ru.petapp.taskmenagementsystem.taskmenagment.utils.DateFormatter;
import ru.petapp.taskmenagementsystem.taskmenagment.model.GenericModel;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper
      extends GenericMapper<User, UserDTO> {

    private final TaskRepository taskRepository;


    protected UserMapper(ModelMapper modelMapper,
                         TaskRepository taskRepository) {
        super(modelMapper, User.class, UserDTO.class);

        this.taskRepository = taskRepository;
    }
    
    @Override
    protected void setupMapper() {
        modelMapper.createTypeMap(User.class, UserDTO.class)
              .addMappings(m -> m.skip(UserDTO::setUserTasks)).setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(UserDTO.class, User.class)
              .addMappings(m -> m.skip(User::setTasks)).setPostConverter(toEntityConverter())
              .addMappings(m -> m.skip(User::setBirthDate)).setPostConverter(toEntityConverter());
    }
    
    @Override
    protected void mapSpecificFields(UserDTO source, User destination) {
        if (!Objects.isNull(source.getUserTasks())) {
            destination.setTasks(new HashSet<>(taskRepository.findAllById(source.getUserTasks())));
        }
        else {
            destination.setTasks(Collections.emptySet());
        }
        destination.setBirthDate(DateFormatter.formatStringToDate(source.getBirthDate()));
    }
    
    @Override
    protected void mapSpecificFields(User source, UserDTO destination) {
        destination.setUserTasks(getIds(source));
    }
    
    @Override
    protected Set<Long> getIds(User entity) {
        return Objects.isNull(entity) || Objects.isNull(entity.getTasks())
               ? null
               : entity.getTasks().stream()
                     .map(GenericModel::getId)
                     .collect(Collectors.toSet());
    }
}
