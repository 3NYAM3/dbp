package databaseProject.dbp.repository;

import databaseProject.dbp.domain.Notice;
import databaseProject.dbp.domain.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TaskRepository {

    private final EntityManager em;

    public void save(Task task){
        em.persist(task);
    }


    public List<Task> findTaskByProjectId(Long projectId) {
        String jpql = "SELECT t FROM Task t WHERE t.project.projectId = :projectId";
        return em.createQuery(jpql, Task.class)
                .setParameter("projectId", projectId)
                .getResultList();
    }
}
