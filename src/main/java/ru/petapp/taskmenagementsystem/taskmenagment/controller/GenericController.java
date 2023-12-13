package ru.petapp.taskmenagementsystem.taskmenagment.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.petapp.taskmenagementsystem.taskmenagment.exception.MyDeleteException;
import ru.petapp.taskmenagementsystem.taskmenagment.model.GenericModel;
import ru.petapp.taskmenagementsystem.taskmenagment.service.GenericService;
import ru.petapp.taskmenagementsystem.taskmenagment.dto.GenericDTO;
import java.util.List;

/**
 * Абстрактный контроллер
 * который реализует все EndPoint`ы для crud операций используя абстрактный репозиторий
 *
 * @param <T> - Сущность с которой работает контроллер
 * @param <N> - DTO с которой работает контроллер
 */
@RestController
@Slf4j
public abstract class GenericController<T extends GenericModel, N extends GenericDTO> {
    
    // protected final GenericRepository<T> genericRepository;
    private GenericService<T, N> service;
    
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public GenericController(//GenericRepository<T> genericRepository,
                             GenericService<T, N> service) {
        //this.genericRepository = genericRepository;
        this.service = service;
    }
    
    //вернуть информацию о книге по переданному ID
    @Operation(description = "Получить запись по ID", method = "getOneById")
    @RequestMapping(value = "/getOneById", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<N> getOneById(@RequestParam(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
              // .body(genericRepository.findById(id).orElseThrow(() -> new NotFoundException("Данных с переданным ID не найдено")));
              .body(service.getOne(id));
    }
    
    @Operation(description = "Получить все записи", method = "getAll")
    @RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, params = "page")
    public ResponseEntity<Page<N>> getAll(@RequestParam(value = "page", required = false) Integer page,
                                          @RequestParam(value = "limit", required = false) Integer limit){
        PageRequest pageRequest = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.ASC, "taskTitle"));
    return ResponseEntity.status(HttpStatus.OK).body(service.listAll(pageRequest));
    }
    
    @Operation(description = "Создать новую запись", method = "create")
    @RequestMapping(value = "/add", method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON_VALUE,
                    consumes = MediaType.APPLICATION_JSON_VALUE)
    
    public ResponseEntity<N> create(@RequestBody N newEntity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(newEntity));
    }
    
    @Operation(description = "Обновить запись", method = "update")
    @RequestMapping(value = "/update", method = RequestMethod.PUT,
                    produces = MediaType.APPLICATION_JSON_VALUE,
                    consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<N> update(@RequestBody N updatedEntity,
                                    @RequestParam(value = "id") Long id) {
        updatedEntity.setId(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.update(updatedEntity));
    }
    
    //@RequestParam: localhost:9090/api/rest/books/deleteBook?id=1
    //@PathVariable: localhost:9090/api/rest/books/deleteBook/1
    @Operation(description = "Удалить запись по ID", method = "delete")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable(value = "id") Long id) throws MyDeleteException {
        service.delete(id);
    }
    


}
