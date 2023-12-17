package databaseProject.dbp.controller;

import databaseProject.dbp.controller.dto.ResponseDto;
import databaseProject.dbp.dto.projectDto.CreateProjectDto;
import databaseProject.dbp.dto.projectDto.ProjectDto;
import databaseProject.dbp.service.MemberService;
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
    private final MemberService memberService;


    /**
     * 프로젝트 리스트 불러오기
     * @param email
     * @return List{이름, 유형, 팀원이메일}
     */
    @GetMapping("/")
    public ResponseDto<?> getProjectList(@AuthenticationPrincipal String email){
        System.out.println(email);
        ResponseDto<List<ProjectDto>> result = projectService.getProjectList(email);
        return result;
    }




    @GetMapping("/pm/{projectId}")
    public ResponseDto<?> findLeaderId(@AuthenticationPrincipal String email, @PathVariable("projectId") Long projectId){
        System.out.println(email);
        System.out.println("asdfasdfasdfasdfa\nsdfasdfas\nasdfasdfasdfasdfa\nsdfasdfas\nasdfasdfasdfasdfa\nsdfasdfas\nasdfasdfasdfasdfa\nsdfasdfas\n");
        ResponseDto<?> result = projectService.checkLeaderId(email, projectId);
        return result;
    }


    @PostMapping("/create")
    public ResponseDto<?> createProject(@RequestBody CreateProjectDto requestBody, @AuthenticationPrincipal String leaderEmail){
        System.out.println(requestBody.toString());
        System.out.println(leaderEmail);
        ResponseDto<?> result = projectService.createProject(requestBody, leaderEmail);
        return result;
    }

    @GetMapping("/create/{email}")
    public ResponseDto<?> findAddMember(@PathVariable("email") String email){
        System.out.println(email);
        ResponseDto<?> result = memberService.findMember(email);
        return result;
    }

    @DeleteMapping("/withdrawal")
    public ResponseDto<?> projectWithdrawal(@AuthenticationPrincipal String email){
        ResponseDto<?> result = projectService.withdrawal(email);
        return result;
    }


}
