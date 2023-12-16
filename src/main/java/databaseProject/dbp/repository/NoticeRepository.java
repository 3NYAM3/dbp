package databaseProject.dbp.repository;

import databaseProject.dbp.domain.Notice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class NoticeRepository {

    private final EntityManager em;

    public void  save(Notice notice){
        em.persist(notice);
    }

    public List<Notice> findAll(){
        return em.createQuery("select n from Notice n", Notice.class)
                .getResultList();
    }
}
