package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class DbServiceTest {

    @Autowired
    DbService dbService;

    @Test
    void getAllTasks() {
        //Given
        int counterBefore = dbService.getAllTasks().size();
        Task newTask = new Task(null, "newTitle", "newContent");
        dbService.saveTask(newTask);
        //When
        int counterAfter = dbService.getAllTasks().size();
        //THen
        assertEquals(1, counterAfter - counterBefore);

        //Cleanup
        dbService.deleteTask(newTask.getId());

    }

    @Test
    void getTask() {
        //Given
        Task newTask = new Task(null, "newTitle", "newContent");
        dbService.saveTask(newTask);

        //When
        Task testedTask = dbService.getTask(newTask.getId()).get();

        //THen
        assertEquals(newTask.getTitle(), testedTask.getTitle());

        //Cleanup
        dbService.deleteTask(newTask.getId());
    }

    @Test
    void saveTask() {
        //Given
        int counterBefore = dbService.getAllTasks().size();
        Task newTask = new Task(null, "newTitle", "newContent");
        //When
        Task testedTask = dbService.saveTask(newTask);
        //THen
        assertEquals(testedTask.getTitle(), newTask.getTitle());
        assertEquals(testedTask.getContent(), newTask.getContent());

        //Cleanup
        dbService.deleteTask(newTask.getId());
    }

    @Test
    void deleteTask() {
        //Given
        Task newTask = new Task(null, "newTitle", "newContent");
        Task testedTask = dbService.saveTask(newTask);
        int counterBefore = dbService.getAllTasks().size();

        //When
        dbService.deleteTask(testedTask.getId());

        //THen
        assertEquals(1,counterBefore-dbService.getAllTasks().size());
        assertSame(Optional.empty(),dbService.getTask(testedTask.getId()));
    }
}