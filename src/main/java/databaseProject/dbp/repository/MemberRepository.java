package databaseProject.dbp.repository;

import databaseProject.dbp.domain.Member;

import javax.persistence.EntityManager;

import databaseProject.dbp.domain.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public void delete(Member member) {
        em.remove(member);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByProjectId(Long projectId) {
        return em.createQuery("select m from Project p join p.members m where p.projectId=:projectId", Member.class)
                .setParameter("projectId", projectId)
                .getResultList();
    }

    public Member findByEmail(String email) {
        List<Member> result = em.createQuery("select m from Member m where m.email= :email", Member.class)
                .setParameter("email", email)
                .getResultList();

        return result.isEmpty() ? null : result.get(0);
    }

    public void updatePassword(String password, String email) {
        Member member = em.createQuery("select m from Member m where m.email=:email", Member.class)
                .setParameter("email", email)
                .getSingleResult();
        if (member != null) {
            member.setPassword(password);
            em.merge(member);
        }
    }

    public void withdrawFromProject(Member member, Project project) {
        member.getProjects().remove(project);
        project.getMembers().remove(member);
    }

}
