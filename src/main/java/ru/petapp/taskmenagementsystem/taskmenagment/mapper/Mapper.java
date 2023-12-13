package ru.petapp.taskmenagementsystem.taskmenagment.mapper;


import ru.petapp.taskmenagementsystem.taskmenagment.dto.GenericDTO;
import ru.petapp.taskmenagementsystem.taskmenagment.model.GenericModel;

import java.util.List;

public interface Mapper<E extends GenericModel, D extends GenericDTO> {
    E toEntity(D dto);
    
    List<E> toEntities(List<D> dtos);
    
    D toDTO(E entity);
    
    List<D> toDTOs(List<E> entities);
}
