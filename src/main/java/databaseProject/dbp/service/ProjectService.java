package databaseProject.dbp.service;

import databaseProject.dbp.domain.Member;
import databaseProject.dbp.repository.MemberRepository;
import databaseProject.dbp.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;

//    @Transactional
//    public Long project(String projectName, String type, String startDate, String lastDate){
//        Member member = memberRepository.findOne();
//
//    }


}
