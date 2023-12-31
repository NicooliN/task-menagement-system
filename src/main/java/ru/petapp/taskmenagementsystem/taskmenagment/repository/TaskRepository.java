package ru.petapp.taskmenagementsystem.taskmenagment.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.petapp.taskmenagementsystem.taskmenagment.model.Task;

@Repository
public interface TaskRepository
      extends GenericRepository<Task> {

    Page<Task> getTaskByUserId(Long id, Pageable pageable);

}
