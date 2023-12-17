package databaseProject.dbp.controller;

import databaseProject.dbp.controller.dto.ResponseDto;
import databaseProject.dbp.dto.taskDto.CreateTaskDto;
import databaseProject.dbp.dto.taskDto.TaskDto;
import databaseProject.dbp.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/project/task/{projectId}")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/create")
    public ResponseDto<?> createTask(@RequestBody CreateTaskDto createTaskDto, @PathVariable("projectId") Long projectId){
        System.out.println(createTaskDto);
        System.out.println(projectId);
        ResponseDto<?> result = taskService.createTask(createTaskDto, projectId);
        return result;
    }

    @GetMapping("/")
    public ResponseDto<?> getTaskList(@PathVariable("projectId") Long projectId){
        System.out.println("Sdfsdfasdfasdfasdfasdfasdfasdfasdfa@@@@@@");
        ResponseDto<List<TaskDto>> result = taskService.getTaskList(projectId);
        return result;
    }

    @PutMapping("/edit/{taskId}")
    public ResponseDto<?> editTask(@RequestBody CreateTaskDto createTaskDto, @PathVariable("projectId") Long projectId, @PathVariable("taskId") Long taskId){
        System.out.println("editTask"+createTaskDto);
        System.out.println(projectId);
        ResponseDto<?> result = taskService.editTask(createTaskDto,taskId);
        return result;
    }

}
