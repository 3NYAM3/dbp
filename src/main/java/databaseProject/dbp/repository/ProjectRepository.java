package databaseProject.dbp.repository;

import databaseProject.dbp.domain.Member;
import databaseProject.dbp.domain.Project;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProjectRepository {

    private final EntityManager em;

    public void save(Project project){
        em.persist(project);
    }

    public Project findOne(Long projectId){
        return em.find(Project.class, projectId);
    }

    public List<Project> findAll(){
        return em.createQuery("select p from Project p", Project.class)
                .getResultList();
    }

    public List<Project> findByMemberEmail(String email){
        return em.createQuery(
                "select p from Project p " +
                        "join p.members m " +
                        "where m.email = :email",
                Project.class)
                .setParameter("email",email)
                .getResultList();
    }

    public void updateLeader(Long leaderId){
        Project project = em.createQuery("select p from Project p where p.leaderId=:leaderId", Project.class)
                .setParameter("leaderId", leaderId)
                .getSingleResult();
        if (project != null) {
            project.setLeaderId(leaderId);
            em.merge(project);
        }
    }






}
