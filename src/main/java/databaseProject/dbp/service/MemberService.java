package databaseProject.dbp.service;

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
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getMemberId();
    }

    private void validateDuplicateMember(Member member) {
        Member findMembers = memberRepository.findByEmail(member.getEmail());
        if(!(findMembers == null)){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
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
