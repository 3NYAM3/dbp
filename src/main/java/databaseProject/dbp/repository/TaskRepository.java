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

    public Task findOne(Long taskId){
        return em.find(Task.class, taskId);
    }

    public List<Task> findTaskByProjectId(Long projectId) {
        return em.createQuery("select t from Task t where t.project.projectId = :projectId", Task.class)
                .setParameter("projectId", projectId)
                .getResultList();
    }

    public void updateTask(Task task){
        em.merge(task);
    }
}
