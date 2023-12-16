package databaseProject.dbp.controller;

import databaseProject.dbp.controller.dto.ResponseDto;
import databaseProject.dbp.dto.projectDto.CreateProjectDto;
import databaseProject.dbp.dto.projectDto.ProjectDto;
import databaseProject.dbp.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;


    @GetMapping("/")
    public ResponseDto<?> getProjectList(@AuthenticationPrincipal String email){
        System.out.println(email);
        ResponseDto<List<ProjectDto>> result = projectService.getProjectList(email);
        return result;
    }

    @PostMapping("/create")
    public ResponseDto<?> createProject(@RequestBody CreateProjectDto requestBody, @AuthenticationPrincipal String leaderEmail){
        System.out.println(requestBody.toString());
        System.out.println(leaderEmail);
        ResponseDto<?> result = projectService.createProject(requestBody, leaderEmail);
        return result;
    }

}
