package databaseProject.dbp.service;

import databaseProject.dbp.controller.dto.ResponseDto;
import databaseProject.dbp.domain.Member;
import databaseProject.dbp.domain.Project;
import databaseProject.dbp.dto.projectDto.CreateProjectDto;
import databaseProject.dbp.dto.projectDto.ProjectDto;
import databaseProject.dbp.repository.MemberRepository;
import databaseProject.dbp.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;


    public ResponseDto<List<ProjectDto>> getProjectList(String email) {
        List<Project> projects = null;

        try {
            projects = projectRepository.findByMemberEmail(email);
            if (projects == null) return ResponseDto.setFailed("project get failed");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("database error");
        }

        List<ProjectDto> projectDtoList = projects.stream()
                .map(project -> {
                    ProjectDto projectDto = new ProjectDto();
                    projectDto.setTitle(project.getProjectName());
                    projectDto.setType(project.getType());
                    Member member = memberRepository.findOne(project.getLeaderId());
                    projectDto.setLeaderEmail(member.getEmail());
                    return projectDto;
                })
                .collect(Collectors.toList());


        return ResponseDto.setSuccess("Success", projectDtoList);
    }


    @Transactional
    public ResponseDto<?> createProject(CreateProjectDto createProjectDto, String leaderEmail) {
        String projectName = createProjectDto.getProjectName();
        String type = createProjectDto.getType();
        LocalDate start = createProjectDto.getStartDate();
        LocalDate last = createProjectDto.getLastDate();
        Member leader = memberRepository.findByEmail(leaderEmail);
        if (leader == null) {
            return ResponseDto.setFailed("cannot find your id");
        }

        Long leaderId = leader.getMemberId();

        Project project = null;
        try {
            project = Project.createProject(projectName, type, start, last, leaderId);
            if (project == null) return ResponseDto.setFailed("create project failed");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("database error");
        }

        projectRepository.save(project);

        try{
            List<String> memberEmailList = createProjectDto.getMemberList();
            if (memberEmailList != null && !memberEmailList.isEmpty()) {
                for (String memberEmail : memberEmailList) {
                    Member invitedMember = memberRepository.findByEmail(memberEmail);
                    if (invitedMember != null) {
                        project.getMembers().add(invitedMember);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.setFailed("failed");
        }


        return ResponseDto.setSuccess("Success", null);
    }
}
