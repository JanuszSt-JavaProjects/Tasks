package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/v1/task")
@RequiredArgsConstructor                                  // 4me comment: - zastępuje konstruktor WRAZ Z adnotacją.
public class TaskController {

    private final DbService service;
    private final TaskMapper taskMapper;

/*    @Autowired
    public TaskController(DbService service, TaskMapper taskMapper) {
        this.service = service;
        this.taskMapper = taskMapper;
    }*/

    @RequestMapping(method = RequestMethod.GET, value = "getTasks")
    public List<TaskDto> getTasks() {
        List<Task> tasks = service.getAllTasks();
        return taskMapper.mapToTaskDtoList(tasks);

    }

    @RequestMapping(method = RequestMethod.GET, value = "getTask")
    public TaskDto getTask(@RequestParam Long taskId) throws TaskNotFoundException {
        return taskMapper.mapToTaskDto(
//              service.getTask(taskId).orElseThrow(TaskNotFoundException::new)          //4me comment: to samo
                service.getTask(taskId).orElseThrow(
                        () -> new TaskNotFoundException()
                )
        );
    }


    @DeleteMapping(value = "deleteTask")
    public TaskDto deleteTask(@RequestParam Long taskId) {
        TaskDto taskDto;
        taskDto = (service.getTask(taskId).isPresent() ?
                taskMapper.mapToTaskDto(service.getTask(taskId).get()) : null);

        if (taskDto != null) {
            service.deleteTask(taskId);
            return taskDto;
        }

        return null;
    }



    @RequestMapping(
            value = "updateTask",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.PUT
    )
    public TaskDto updateTask(@RequestBody TaskDto taskDto) {

        Task task = taskMapper.mapToTask(taskDto);
        Task savedTask = service.saveTask(task);

        return taskMapper.mapToTaskDto(savedTask);

    }


    @RequestMapping(
            method = RequestMethod.POST,
            value = "createTask",
            consumes = MediaType.APPLICATION_JSON_VALUE)

    public void createTask(@RequestBody TaskDto taskDto) {
        Task task = taskMapper.mapToTask(taskDto);
        service.saveTask(task);
    }
}