package databaseProject.dbp.controller;

import databaseProject.dbp.controller.dto.ResponseDto;
import databaseProject.dbp.dto.projectDto.ChangeLeaderDto;
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
     *
     * @param email
     * @return List{ProjectDto}
     */
    @GetMapping("/")
    public ResponseDto<?> getProjectList(@AuthenticationPrincipal String email) {
        ResponseDto<List<ProjectDto>> result = projectService.getProjectList(email);
        return result;
    }

    /**
     * 대시보드와 타임라인, 관리에 쓸 프로젝트 정보 가져오기
     *
     * @param projectId
     * @return Project
     */
    @GetMapping("/{projectId}")
    public ResponseDto<?> getProjectDetail(@PathVariable("projectId") Long projectId) {
        ResponseDto<ProjectDto> result = projectService.getProject(projectId);
        return result;
    }

    /**
     * 관리탭을 위한 리더 아이디 가져오기
     *
     * @param email
     * @param projectId
     * @return true or false, message
     */
    @GetMapping("/pm/{projectId}")
    public ResponseDto<?> findLeaderId(@AuthenticationPrincipal String email, @PathVariable("projectId") Long projectId) {
        ResponseDto<?> result = projectService.checkLeaderId(email, projectId);
        return result;
    }

    /**
     * 프로젝트 만들기
     *
     * @param requestBody
     * @param leaderEmail
     * @return true or false, message
     */
    @PostMapping("/create")
    public ResponseDto<?> createProject(@RequestBody CreateProjectDto requestBody, @AuthenticationPrincipal String leaderEmail) {
        ResponseDto<?> result = projectService.createProject(requestBody, leaderEmail);
        return result;
    }

    /**
     * 프로젝트 생성시 추가할 멤버 검색
     *
     * @param email
     * @return true or false, message
     */
    @GetMapping("/create/{email}")
    public ResponseDto<?> findAddMember(@PathVariable("email") String email) {
        ResponseDto<?> result = memberService.findMember(email);
        return result;
    }

    /**
     * 프로젝트 나가기
     *
     * @param email
     * @return true or false, message
     */
    @DeleteMapping("/withdrawal/{projectId}")
    public ResponseDto<?> projectWithdrawal(@AuthenticationPrincipal String email, @PathVariable("projectId") Long projectId) {
        ResponseDto<?> result = projectService.withdrawProject(email, projectId);
        return result;
    }

    /**
     * 프로젝트 수정
     *
     * @param projectId
     * @param updatedProject
     * @return true or false, message
     */
    @PutMapping("/{projectId}")
    public ResponseDto<?> updateProject(@PathVariable("projectId") Long projectId, @RequestBody CreateProjectDto updatedProject) {
        ResponseDto<?> result = projectService.updateProject(projectId, updatedProject);
        return result;
    }

    /**
     * 팀장 변경
     *
     * @param projectId
     * @param changeLeaderDto
     * @return true or false, message
     */
    @PutMapping("/leader/{projectId}")
    public ResponseDto<?> changeLeader(@PathVariable("projectId") Long projectId, @RequestBody ChangeLeaderDto changeLeaderDto) {
        ResponseDto<?> result = projectService.changeLeader(projectId, changeLeaderDto);
        return result;
    }

    /**
     * 프로젝트 삭제
     *
     * @param projectId
     * @return true or false, message
     */
    @DeleteMapping("/{projectId}")
    public ResponseDto<?> deleteProject(@PathVariable("projectId") Long projectId) {
        ResponseDto<?> result = projectService.deleteProject(projectId);
        return result;
    }


}
