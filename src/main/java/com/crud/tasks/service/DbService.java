package com.crud.tasks.service;


import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor                          //4me comment: adnotacja zastępuje zakomentowany konstruktor.

public class DbService {

    private final TaskRepository repository;

    /*    public DbService(TaskRepository repository) {
            this.repository = repository;
        }
    */


    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public Optional<Task> getTask(Long id) {
        return repository.findById(id);

    }

    public Task saveTask(Task task) {
        repository.save(task);
        return task;
    }

    public void deleteTask(Long taskId) {

        repository.deleteById(taskId);
    }

}
