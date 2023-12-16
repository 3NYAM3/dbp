package databaseProject.dbp.repository;

import databaseProject.dbp.domain.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class TaskRepository {

    private final EntityManager em;

    public void save(Task task){
        em.persist(task);
    }


}
