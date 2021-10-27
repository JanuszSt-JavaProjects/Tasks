package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaskMapperTest {

    @Autowired
    TaskMapper taskMapper;


    @Test
    void mapToTask() {
        //Given
        TaskDto taskDto = new TaskDto(1L, "TaskDto Title", "TaskDto Content");

        //When
        Task mappedTaskDto = taskMapper.mapToTask(taskDto);

        //Then
        assertEquals(1L, mappedTaskDto.getId());
        assertEquals("TaskDto Title", mappedTaskDto.getTitle());
        assertEquals("TaskDto Content", mappedTaskDto.getContent());
    }


    @ExtendWith(MockitoExtension.class)
    @Test
    void mapToTaskDto() {

        //Given
        Task task = new Task(1L, "Task Title", "Task Content");

        //When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);

        //Then
        assertNotNull(taskDto);
        assertEquals(1L, taskDto.getId());
        assertEquals("Task Title", taskDto.getTitle());
        assertEquals("Task Content", taskDto.getContent());
    }

    @Test
    void mapToTaskDtoList() {

        //Given
        List<Task> taskList = List.of(
                new Task(1L, "1Title", "1Content"),
                new Task(2L, "2Title", "2Content")
        );

        //When
        List <TaskDto> taskDtoList =taskMapper.mapToTaskDtoList(taskList);

        //Then
        assertEquals(2,taskDtoList.size());
        assertTrue(2L==taskDtoList.get(0).getId()|| 2L ==taskDtoList.get(1).getId());
        assertTrue(1L==taskDtoList.get(0).getId()|| 1L ==taskDtoList.get(1).getId());
        assertFalse(3L==taskDtoList.get(0).getId()|| 3L ==taskDtoList.get(1).getId());
    }
}