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

    /**
     * 작업 생성
     *
     * @param createTaskDto
     * @param projectId
     * @return true or false, message
     */
    @PostMapping("/{projectId}/create")
    public ResponseDto<?> createTask(@RequestBody CreateTaskDto createTaskDto, @PathVariable("projectId") Long projectId) {
        ResponseDto<?> result = taskService.createTask(createTaskDto, projectId);
        return result;
    }

    /**
     * 작업리스트 가져오기
     *
     * @param projectId
     * @return taskDtoList
     */
    @GetMapping("/list/{projectId}")
    public ResponseDto<?> getTaskList(@PathVariable("projectId") Long projectId) {
        ResponseDto<List<TaskDto>> result = taskService.getTaskList(projectId);
        return result;
    }

    /**
     * 작업 내용 가져오기
     *
     * @param taskId
     * @return taskDto
     */
    @GetMapping("/{taskId}")
    public ResponseDto<?> getTask(@PathVariable("taskId") Long taskId) {
        ResponseDto<?> result = taskService.getTask(taskId);
        return result;
    }

    /**
     * 작업 수정
     *
     * @param createTaskDto
     * @param taskId
     * @return true or false, message
     */
    @PutMapping("/edit/{taskId}")
    public ResponseDto<?> editTask(@RequestBody CreateTaskDto createTaskDto, @PathVariable("taskId") Long taskId) {
        ResponseDto<?> result = taskService.editTask(createTaskDto, taskId);
        return result;
    }

    /**
     * 작업삭제
     *
     * @param taskId
     * @return true or false, message
     */
    @DeleteMapping("delete/{taskId}")
    public ResponseDto<?> deleteTask(@PathVariable("taskId") Long taskId) {
        ResponseDto<?> result = taskService.deleteTask(taskId);
        return result;
    }

}