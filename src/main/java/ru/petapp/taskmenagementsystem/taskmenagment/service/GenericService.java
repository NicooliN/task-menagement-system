package ru.petapp.taskmenagementsystem.taskmenagment.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import ru.petapp.taskmenagementsystem.taskmenagment.dto.GenericDTO;
import ru.petapp.taskmenagementsystem.taskmenagment.exception.MyDeleteException;
import ru.petapp.taskmenagementsystem.taskmenagment.mapper.GenericMapper;
import ru.petapp.taskmenagementsystem.taskmenagment.model.GenericModel;
import ru.petapp.taskmenagementsystem.taskmenagment.repository.GenericRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Абстрактный сервис, который хранит в себе реализацию CRUD операций по-умолчанию.
 * Если реализация отличная от того, что представлено в этом классе,
 * то она переопределяется в реализации конкретного сервиса.
 *
 * @param <T> - Сущность, с которой мы работаем.
 * @param <N> - DTO, которую мы будем отдавать/принимать дальше.
 */
@Service
public abstract class GenericService<T extends GenericModel, N extends GenericDTO> {
    
    //Инжектим абстрактный репозиторий для работы с базой данных
    protected final GenericRepository<T> repository;
    //Инжектим абстрактный маппер для преобразований из DTO -> Entity, и обратно.
    protected final GenericMapper<T, N> mapper;
    
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    protected GenericService(GenericRepository<T> repository,
                             GenericMapper<T, N> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    
    /**
     * Метод, возвращающий полный список всех сущностей.
     *
     * @return Список сконвертированных сущностей в DTO
     */
//    public List<N> listAll() {
//        return mapper.toDTOs(repository.findAll());
//    }
    
    public Page<N> listAll(Pageable pageable) {
        Page<T> objects = repository.findAll(pageable);
        List<N> result = mapper.toDTOs(objects.getContent());
        return new PageImpl<>(result, pageable, objects.getTotalElements());
    }
    

    public N getOne(Long id) {
        return mapper.toDTO(repository.findById(id).orElseThrow(() -> new NotFoundException("Данных по заданному id: " + id + " не найдены")));
    }
    
    /***
     * Создание сущности в БД.
     *
     * @param object - информация о сущности/объекте.
     * @return - сохраненная в БД сущность в формате DTO.
     */
    public N create(N object) {
        object.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        object.setCreatedWhen(LocalDateTime.now());
        return mapper.toDTO(repository.save(mapper.toEntity(object)));
    }
    
    /***
     * Обновление сущности в БД.
     *
     * @param object - информация о сущности/объекте.
     * @return - обновленная в БД сущность в формате DTO.
     */
    public N update(N object) {
        return mapper.toDTO(repository.save(mapper.toEntity(object)));
    }


    public void delete(Long id) throws MyDeleteException {
        repository.deleteById(id);
    }

}
