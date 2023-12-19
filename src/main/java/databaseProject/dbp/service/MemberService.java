package databaseProject.dbp.service;

import databaseProject.dbp.controller.dto.ResponseDto;
import databaseProject.dbp.domain.Member;
import databaseProject.dbp.domain.Project;
import databaseProject.dbp.dto.memberDto.*;
import databaseProject.dbp.repository.MemberRepository;
import databaseProject.dbp.repository.ProjectRepository;
import databaseProject.dbp.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;

    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public ResponseDto<?> join(SignUpDto dto) {
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        Member member = Member.createMember(dto.getName(), dto.getEmail(), encodedPassword);

        try {
            if (validateDuplicateMember(member)) {
                return ResponseDto.setFailed("이메일 중복");
            }
        } catch (Exception e) {
            return ResponseDto.setFailed("데이터 베이스 오류");
        }

        memberRepository.save(member);
        return ResponseDto.setSuccessNotIncludeData("Sign Up Success");
    }


    private boolean validateDuplicateMember(Member member) {
        Member findMembers = memberRepository.findByEmail(member.getEmail());
        return !(findMembers == null);
    }

    @Transactional
    public ResponseDto<LoginResponseDto> login(LoginDto dto) {
        String dtoEmail = dto.getEmail();
        String dtoPassword = dto.getPassword();

        Member member = null;
        try {
            member = memberRepository.findByEmail(dtoEmail);
            if (member == null) return ResponseDto.setFailed("login failed");
            if (!passwordEncoder.matches(dtoPassword, member.getPassword()))
                return ResponseDto.setFailed("login failed");
        } catch (Exception e) {
            return ResponseDto.setFailed("Database error");
        }

        String token = tokenProvider.create(dtoEmail);
        int exprTime = 3600000;

        LoginResponseDto loginResponseDto = new LoginResponseDto(token, exprTime, member);
        return ResponseDto.setSuccess("Success", loginResponseDto);
    }

    public ResponseDto<LoggedInMemberDto> getLoginMember(String email) {
        Member member = null;

        try {
            member = memberRepository.findByEmail(email);
            if (member == null) return ResponseDto.setFailed("info get failed");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("Database Error");
        }

        String name = member.getName();

        LoggedInMemberDto getLoginMemberResponseDto = new LoggedInMemberDto(email, name);
        return ResponseDto.setSuccess("Success", getLoginMemberResponseDto);
    }

    public ResponseDto<?> findMember(String email) {
        Member member = null;
        try {
            member = memberRepository.findByEmail(email);
            if (member == null) return ResponseDto.setFailed("info get failed");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("database error");
        }
        return ResponseDto.setSuccessNotIncludeData("Success");
    }

    @Transactional
    public ResponseDto<?> updatePassword(String email, PasswordDto passwordDto) {
        Member member = null;
        try {
            member = memberRepository.findByEmail(email);
            if (member == null) return ResponseDto.setFailed("member null");
            if (!passwordEncoder.matches(passwordDto.getCurrentPassword(), member.getPassword()))
                return ResponseDto.setFailed("password wrong");
        } catch (Exception e) {
            return ResponseDto.setFailed("database error");
        }

        memberRepository.updatePassword(passwordEncoder.encode(passwordDto.getChangePassword()), email);

        return ResponseDto.setSuccessNotIncludeData("Success");
    }

    @Transactional
    public ResponseDto<?> withdrawMember(String email) {
        Member member = memberRepository.findByEmail(email);
        Set<Project> projects = member.getProjects();

        try {
            Member leader = null;
            for (Project project : projects) {
                if (Objects.equals(project.getLeaderId(), member.getMemberId())) {//팀장ㅍ일때
                    leader = assignNewLeader(project);
                    if (leader == null) {
                        projectRepository.removeProject(project);
                    } else {
                        project.setLeaderId(leader.getMemberId());
                        project.getMembers().remove(member);
                    }
                }else {//팀장이 아닐떄
                    memberRepository.withdrawFromProject(member, project);
                }
            }

            memberRepository.delete(member);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("database error");
        }

        return ResponseDto.setSuccessNotIncludeData("withdrawal successful");

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
