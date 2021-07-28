package com.crud.tasks.repository;

import com.crud.tasks.domain.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {

    @Override
    List<Task> findAll();                           //4me comment: domyślnie zwraca ITERABLE. Zmnieniamy typ zwracany//

    @Override
    Optional<Task> findById(Long id);

/*
    @Override
    void deleteById(Long id);                       //4me comment: nie da się zrobić OPTIONAL -a
*/

}