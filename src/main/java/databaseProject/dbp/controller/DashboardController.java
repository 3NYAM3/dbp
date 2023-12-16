package databaseProject.dbp.controller;

import databaseProject.dbp.controller.dto.ResponseDto;
import databaseProject.dbp.service.NoticeService;
import databaseProject.dbp.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/project/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final ProjectService projectService;
    private final NoticeService noticeService;

    @GetMapping("/notice/{projectId}")
    public ResponseDto getNotice(@PathVariable("projectId") Long projectId){
        return null;
    }
    @GetMapping("/{projectId}")
    public ResponseDto<?> getProjectDetail(@PathVariable("projectId") Long projectId){
        System.out.println(projectId);
        ResponseDto<?> result = projectService.getProject(projectId);
        return result;
    }


}
