package databaseProject.dbp.service;

import databaseProject.dbp.controller.dto.ResponseDto;
import databaseProject.dbp.domain.Project;
import databaseProject.dbp.domain.Task;
import databaseProject.dbp.dto.taskDto.CreateTaskDto;
import databaseProject.dbp.dto.taskDto.TaskDto;
import databaseProject.dbp.repository.ProjectRepository;
import databaseProject.dbp.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    @Transactional
    public ResponseDto<?> createTask(CreateTaskDto createTaskDto, Long projectId) {
        String taskContent = createTaskDto.getContent();
        String memo = createTaskDto.getMemo();
        LocalDate startDate = createTaskDto.getStartDate();
        LocalDate lastDate = createTaskDto.getLastDate();
        Project project = projectRepository.findOne(projectId);


        Task task = null;

        try {
            task = Task.createTask(project,taskContent, memo, startDate, lastDate);
            if (task == null) return ResponseDto.setFailed("create Failed");
        } catch (Exception e) {
            return ResponseDto.setFailed("database error");
        }

        taskRepository.save(task);

        return ResponseDto.setSuccessNotIncludeData("Success");
    }


    public ResponseDto<List<TaskDto>> getTaskList(Long projectId) {
        List<Task> tasks = null;

        try {
            tasks = taskRepository.findTaskByProjectId(projectId);
            if (tasks == null) return ResponseDto.setFailed("task get failed");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.setFailed("database error");
        }

        List<TaskDto> taskDtoList = tasks.stream()
                .map(task -> {
                    TaskDto taskDto = new TaskDto();
                    taskDto.setTaskId(task.getTaskId());
                    taskDto.setContent(task.getContent());
                    taskDto.setMemo(task.getMemo());
                    taskDto.setLastDate(task.getLastDate());
                    taskDto.setStartDate(task.getStartDate());

                    return taskDto;
                }).collect(Collectors.toList());

        return ResponseDto.setSuccess("Success", taskDtoList);
    }




    @Transactional
    public ResponseDto<?> editTask(CreateTaskDto updateDto, Long taskId) {
        Task task = null;

        try{
            task = taskRepository.findOne(taskId);
            if (task==null) return ResponseDto.setFailed("cannnot find task");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.setFailed("database error");
        }

        if(updateDto.getContent() != null){
            task.setContent(updateDto.getContent());
        }

        if (updateDto.getMemo() != null){
            task.setMemo(updateDto.getMemo());
        }

        if(updateDto.getStartDate() != null){
            task.setStartDate(updateDto.getStartDate());
        }

        if(updateDto.getLastDate()!=null){
            task.setLastDate(updateDto.getLastDate());
        }

        taskRepository.updateTask(task);

        return ResponseDto.setSuccessNotIncludeData("Success");
    }

    public ResponseDto<?> getTask(Long taskId) {
        Task task = null;
        try {
            task = taskRepository.findOne(taskId);
            if(task==null) return ResponseDto.setFailed("failed");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.setFailed("database error");
        }
        TaskDto taskDto= new TaskDto();
        taskDto.setTaskId(taskId);
        taskDto.setContent(task.getContent());
        taskDto.setMemo(task.getMemo());
        taskDto.setStartDate(task.getStartDate());
        taskDto.setLastDate(task.getLastDate());

        return ResponseDto.setSuccess("Success", taskDto);
    }

    @Transactional
    public ResponseDto<?> deleteTask(Long taskId) {
        Task task = taskRepository.findOne(taskId);

        try {
            if (task==null) return ResponseDto.setFailed("task get failed");
            taskRepository.deleteTask(task);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.setFailed("database error");
        }

        return ResponseDto.setSuccessNotIncludeData("Success");
    }
}
