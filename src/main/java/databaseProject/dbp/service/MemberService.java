package databaseProject.dbp.service;

import databaseProject.dbp.controller.dto.ResponseDto;
import databaseProject.dbp.domain.Member;
import databaseProject.dbp.domain.Project;
import databaseProject.dbp.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


    @Transactional
    public ResponseDto<?> join(Member member){
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
