//package databaseProject.dbp.controller;
//
//import databaseProject.dbp.controller.dto.ResponseDto;
//import databaseProject.dbp.dto.taskDto.TaskDto;
//import databaseProject.dbp.service.TaskService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("api/project/timeline/{projectId}")
//@RequiredArgsConstructor
//public class TimeLineController {
//
//    private final TaskService taskService;
//
//    @GetMapping("/")
//    public ResponseDto<?> getTaskListForTimeline(@PathVariable("projectId") Long projectId){
//        ResponseDto<List<TaskDto>> result = taskService.getTaskList(projectId);
//        return result;
//    }
//}
