package databaseProject.dbp.repository;

import databaseProject.dbp.domain.Member;
import databaseProject.dbp.domain.Project;
import jakarta.persistence.EntityManager;
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




}
