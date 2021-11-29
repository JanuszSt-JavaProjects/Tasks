package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService service;

    @MockBean
    private TaskMapper taskMapper;

    @Captor
    ArgumentCaptor<Long> longArgumentCaptor;

    List<Task> tasks = prepareDatabaseMock();

    public List<Task> prepareDatabaseMock() {
        tasks = Arrays.asList(
                new Task(1L, "Task1", "Content1"),
                new Task(2L, "Task2", "Content2"),
                new Task(3L, "Task3", "Content3")
        );
        return tasks;
    }

    @Test
    void getTasks() throws Exception {

        //Given
        given(service.getAllTasks()).willReturn(tasks);
        given(taskMapper.mapToTaskDtoList(any())).willReturn(new TaskMapper().mapToTaskDtoList(tasks));

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/tasks/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.is("Task1")));
    }

    @Test
    void getTask() throws Exception {

        //Given
        Task task = tasks.get(0);
        given(service.getTask(any())).willReturn(java.util.Optional.ofNullable(task));
        given(taskMapper.mapToTaskDto(any())).willCallRealMethod();

        //When & Then
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/v1/tasks/0")
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Task1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.aMapWithSize(3)));

    }

    @Test
    void deleteTask() throws Exception {

        //Given & When & Then
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .delete("/v1/tasks/0")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));

        verify(service, times(1)).deleteTask(any());
        verify(service).deleteTask(longArgumentCaptor.capture());
        assertEquals(0, longArgumentCaptor.getValue());
    }

    @Test
    void updateTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(100L, "TaskDtoTitle", "TaskDtoContent");
        Task expectedTask = new Task(100L, "TaskDtoTitle", "TaskDtoContent");

        given(service.saveTask(any())).willReturn(expectedTask);
        given(taskMapper.mapToTaskDto(any())).willCallRealMethod();

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/v1/tasks/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .content(jsonContent))
                .andDo(x -> assertTrue(jsonContent.contains("TaskDtoTitle")))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(100)));
        verify(service).saveTask(any());
    }

    @Test
    void createTask() throws Exception {

        //Given
        TaskDto taskDto = new TaskDto(null, "TaskDtoTitle", "TaskDtoContent");
        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/v1/tasks")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(x -> assertTrue(jsonContent.contains("TaskDtoTitle")));

        verify(service).saveTask(any());
        verify(taskMapper).mapToTask(any());
        verify(taskMapper).mapToTask(any());
    }
}