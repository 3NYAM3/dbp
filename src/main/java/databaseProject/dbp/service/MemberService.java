package databaseProject.dbp.service;

import databaseProject.dbp.controller.dto.ResponseDto;
import databaseProject.dbp.domain.Member;
import databaseProject.dbp.domain.Project;
import databaseProject.dbp.dto.memberDto.LoggedInMemberDto;
import databaseProject.dbp.dto.memberDto.LoginDto;
import databaseProject.dbp.dto.memberDto.LoginResponseDto;
import databaseProject.dbp.dto.memberDto.SignUpDto;
import databaseProject.dbp.repository.MemberRepository;
import databaseProject.dbp.security.TokenProvider;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class MemberService {

    @Autowired private MemberRepository memberRepository;

    @Autowired private TokenProvider tokenProvider;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public ResponseDto<?> join(SignUpDto dto){

        Member member = new Member();
        member.setName(dto.getName());
        member.setEmail(dto.getEmail());
        member.setPassword(passwordEncoder.encode(dto.getPassword()));

        try{
           if(validateDuplicateMember(member)){
               return ResponseDto.setFailed("Existed Email");
           }
        }catch (Exception e){
            return ResponseDto.setFailed("database Error");
        }

        memberRepository.save(member);
        return ResponseDto.setSuccess("Sign Up Success",null);
    }



    private boolean validateDuplicateMember(Member member) {
        Member findMembers = memberRepository.findByEmail(member.getEmail());
        if(!(findMembers==null)){
            return true;
        }
        return false;
    }

    @Transactional
    public ResponseDto<LoginResponseDto> login(LoginDto dto){
        String dtoEmail = dto.getEmail();
        String dtoPassword = dto.getPassword();

        Member member = null;
        try{
            member = memberRepository.findByEmail(dtoEmail);
            if (member == null) return ResponseDto.setFailed("login failed");
            if (!passwordEncoder.matches(dtoPassword, member.getPassword()))
                return ResponseDto.setFailed("login failed");
        }catch (Exception e){
            return ResponseDto.setFailed("Database error");
        }
        //member.setPassword("");

        String token = tokenProvider.create(dtoEmail);
        int exprTime = 3600000;

        LoginResponseDto loginResponseDto = new LoginResponseDto(token,exprTime,member);
        return ResponseDto.setSuccess("Success",loginResponseDto);

    }

    public ResponseDto<LoggedInMemberDto> getLoginMember(String email){
        Member member = null;

        try{
            member = memberRepository.findByEmail(email);
            if(member==null) return ResponseDto.setFailed("info get failed");

        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.setFailed("Database Error");
        }

        String name = member.getName();

        LoggedInMemberDto getLoginMemberResponseDto = new LoggedInMemberDto(email, name);
        return ResponseDto.setSuccess("Success", getLoginMemberResponseDto);
    }

    public ResponseDto<?> findMember(String email){
        Member member = null;
        try {
            member = memberRepository.findByEmail(email);
            if(member==null) return ResponseDto.setFailed("info get failed");
        }catch(Exception e){
            e.printStackTrace();
            return ResponseDto.setFailed("database error");
        }
        return  ResponseDto.setSuccessNotIncludeData("Success");
    }

    @Transactional
    public ResponseDto<?> updatePassword(String email, Map<String,String> password) {
        Member member = null;
        try{
            member = memberRepository.findByEmail(email);
            if (member==null) return ResponseDto.setFailed("member null");
            if (!passwordEncoder.matches(password.get("nowPassword"), member.getPassword()))
                return ResponseDto.setFailed("password wrong");
        }catch (Exception e){
            return ResponseDto.setFailed("database error");
        }

        memberRepository.updatePassword(passwordEncoder.encode(password.get("changePassword")), email);

        return ResponseDto.setSuccessNotIncludeData("Success");
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findById(Long memberId){
        return memberRepository.findOne(memberId);
    }

    public Set<Project> findProjectByMemberId(Long memberId){
        Member member = memberRepository.findOne(memberId);
        return member.getProjects();
    }



}
