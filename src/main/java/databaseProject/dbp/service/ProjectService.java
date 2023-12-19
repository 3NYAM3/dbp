package databaseProject.dbp.service;

import databaseProject.dbp.controller.dto.ResponseDto;
import databaseProject.dbp.domain.*;
import databaseProject.dbp.dto.projectDto.ChangeLeaderDto;
import databaseProject.dbp.dto.projectDto.CreateProjectDto;
import databaseProject.dbp.dto.projectDto.ProjectDto;
import databaseProject.dbp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;

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

        try {
            List<String> memberEmailList = createProjectDto.getMemberList();
            if (memberEmailList != null && !memberEmailList.isEmpty()) {
                for (String memberEmail : memberEmailList) {
                    Member invitedMember = memberRepository.findByEmail(memberEmail);
                    if (invitedMember != null) {
                        project.getMembers().add(invitedMember);

                    }
                }
            }
            project.getMembers().add(leader);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("failed");
        }


        return ResponseDto.setSuccessNotIncludeData("Success");
    }

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
                    projectDto.setProjectId(project.getProjectId());
                    projectDto.setProjectName(project.getProjectName());
                    projectDto.setType(project.getType());
                    Member member = memberRepository.findOne(project.getLeaderId());
                    projectDto.setLeaderEmail(member.getEmail());

                    List<Member> members = memberRepository.findByProjectId(project.getProjectId());
                    List<String> memberEmailList = new ArrayList<>();
                    for (Member member1 : members) {
                        memberEmailList.add(member1.getEmail());
                    }
                    projectDto.setMemberEmail(memberEmailList);

                    return projectDto;
                })
                .collect(Collectors.toList());


        return ResponseDto.setSuccess("Success", projectDtoList);
    }

    public ResponseDto<ProjectDto> getProject(Long projectId) {
        Project project = null;
        ProjectDto projectDto = new ProjectDto();

        try {
            project = projectRepository.findOne(projectId);
            if (project == null) {
                return ResponseDto.setFailed("failed");
            }
            projectDto.setProjectId(projectId);
            projectDto.setType(project.getType());
            projectDto.setStartDate(project.getStartDate());
            projectDto.setLastDate(project.getLastDate());
            Member member = memberRepository.findOne(project.getLeaderId());
            projectDto.setLeaderEmail(member.getEmail());
            projectDto.setProjectName(project.getProjectName());

            List<Member> members = memberRepository.findByProjectId(project.getProjectId());
            List<String> memberEmailList = new ArrayList<>();
            for (Member member1 : members) {
                memberEmailList.add(member1.getEmail());
            }
            projectDto.setMemberEmail(memberEmailList);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("database error");
        }

        return ResponseDto.setSuccess("Success", projectDto);
    }


    public ResponseDto<?> checkLeaderId(String loggedInEmail, Long projectId) {
        Project project = null;
        String email;
        try {
            project = projectRepository.findOne(projectId);
            if (project == null) return ResponseDto.setFailed("failed");

            email = memberRepository.findOne(project.getLeaderId()).getEmail();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("database error");
        }

        if (!email.equals(loggedInEmail)) {
            return ResponseDto.setFailed("not leader");
        }


        return ResponseDto.setSuccessNotIncludeData("Success");
    }

    @Transactional
    public ResponseDto<?> withdrawProject(String email, Long projectId) {
        Member member = memberRepository.findByEmail(email);
        Project project = projectRepository.findOne(projectId);

        try {
            if (member == null || project == null) {
                return ResponseDto.setFailed("member, project get failed");
            }
            if (Objects.equals(member.getMemberId(), project.getLeaderId())) {
                Member newLeader = assignNewLeader(project);
                if (newLeader == null) {
                    memberRepository.withdrawFromProject(member, project);
                    deleteProject(projectId);
                }
            }
            memberRepository.withdrawFromProject(member, project);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("database error");
        }

        return ResponseDto.setSuccessNotIncludeData("Success");
    }

    @Transactional
    public ResponseDto<?> updateProject(Long projectId, CreateProjectDto updatedProjectDto) {
        Project project = null;
        List<Member> currentMembers = null;

        try {
            project = projectRepository.findOne(projectId);
            if (project == null) return ResponseDto.setFailed("cannot find project");

            currentMembers = memberRepository.findByProjectId(projectId);

            if (updatedProjectDto.getProjectName() != null) {
                project.setProjectName(updatedProjectDto.getProjectName());
            }
            if (updatedProjectDto.getType() != null) {
                project.setType(updatedProjectDto.getType());
            }

            if (updatedProjectDto.getStartDate() != null) {
                project.setStartDate(updatedProjectDto.getStartDate());
            }

            if (updatedProjectDto.getLastDate() != null) {
                project.setLastDate(updatedProjectDto.getLastDate());
            }
            if (updatedProjectDto.getMemberList() != null) {
                List<String> updatedMemberList = updatedProjectDto.getMemberList();

                for (String updatedMemberEmail : updatedMemberList) {
                    if (!currentMembers.stream().anyMatch(member -> member.getEmail().equals(updatedMemberEmail))) {
                        Member newMember = memberRepository.findByEmail(updatedMemberEmail);
                        if (newMember != null) {
                            project.getMembers().add(newMember);
                        }
                    }
                }

                for (Member currentMember : currentMembers) {
                    if (!updatedMemberList.contains(currentMember.getEmail())) {
                        project.getMembers().remove(currentMember);
                    }
                }
            }

            projectRepository.update(project);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("database error");
        }
        return ResponseDto.setSuccessNotIncludeData("Success");
    }

    @Transactional
    public ResponseDto<?> deleteProject(Long projectId) {
        try {
            Project project = projectRepository.findOne(projectId);
            List<Member> members = memberRepository.findByProjectId(projectId);

            for (Member member : members) {
                member.getProjects().remove(project);
            }

            projectRepository.removeProject(project);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("database error");
        }
        return ResponseDto.setSuccessNotIncludeData("Success");
    }

    @Transactional
    public ResponseDto<?> changeLeader(Long projectId, ChangeLeaderDto changeLeaderDto) {
        Project project = null;

        try {
            project = projectRepository.findOne(projectId);
            Long changeLeaderId = memberRepository.findByEmail(changeLeaderDto.getChangeLeaderEmail()).getMemberId();

            if (Objects.equals(project.getLeaderId(), changeLeaderId)) {
                return ResponseDto.setFailed("failed");
            }
            project.setLeaderId(changeLeaderId);

            projectRepository.update(project);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("database error");
        }

        return ResponseDto.setSuccessNotIncludeData("Success");
    }

    private Member assignNewLeader(Project project) {
        Set<Member> members = project.getMembers();
        if (members == null || members.size() == 1 || members.isEmpty()) {
            return null;
        }
        List<Member> memberList;
        do {
            memberList = new ArrayList<>(members);
            Collections.shuffle(memberList);

        } while (Objects.equals(memberList.get(0).getMemberId(), project.getLeaderId()));

        return memberList.get(0);
    }


}
