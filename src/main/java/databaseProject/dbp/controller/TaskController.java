package databaseProject.dbp.controller;

import databaseProject.dbp.controller.dto.ResponseDto;
import databaseProject.dbp.dto.taskDto.CreateTaskDto;
import databaseProject.dbp.dto.taskDto.TaskDto;
import databaseProject.dbp.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/project/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/{projectId}/create")
    public ResponseDto<?> createTask(@RequestBody CreateTaskDto createTaskDto, @PathVariable("projectId") Long projectId){
        System.out.println(createTaskDto);
        System.out.println(projectId);
        ResponseDto<?> result = taskService.createTask(createTaskDto, projectId);
        return result;
    }

    @GetMapping("/list/{projectId}")
    public ResponseDto<?> getTaskList(@PathVariable("projectId") Long projectId){
        System.out.println("Sdfsdfasdfasdfasdfasdfasdfasdfasdfa@@@@@@");
        ResponseDto<List<TaskDto>> result = taskService.getTaskList(projectId);
        return result;
    }
    @GetMapping("/{taskId}")
    public ResponseDto<?> getTask(@PathVariable("taskId")Long taskId){
        System.out.println(taskId);
        ResponseDto<?> result = taskService.getTask(taskId);
        return result;
    }

    @PutMapping("/edit/{taskId}")
    public ResponseDto<?> editTask(@RequestBody CreateTaskDto createTaskDto, @PathVariable("taskId") Long taskId){
        System.out.println("editTask"+createTaskDto);
        ResponseDto<?> result = taskService.editTask(createTaskDto,taskId);
        return result;
    }

}