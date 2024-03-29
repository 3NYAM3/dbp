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

    public void save(Notice notice) {
        em.persist(notice);
    }

    public Notice findOne(Long noticeId) {
        return em.find(Notice.class, noticeId);
    }

    public void update(Notice notice) {
        em.merge(notice);
    }

    public List<Notice> findNoticesByProjectId(Long projectId) {
        String jpql = "SELECT n FROM Notice n WHERE n.project.projectId = :projectId";
        return em.createQuery(jpql, Notice.class)
                .setParameter("projectId", projectId)
                .getResultList();
    }

    public void removeNotice(Notice notice) {
        em.remove(notice);
    }
}
