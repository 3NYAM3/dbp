package databaseProject.dbp.repository;

import databaseProject.dbp.domain.Notice;
import databaseProject.dbp.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepository {

    private final EntityManager em;

    public void save(Review review){
        em.persist(review);
    }

    public List<Review> findByMemberId(Long noticeId){
        return em.createQuery("select r from Review r where  r.notice.noticeId= : noticeId", Review.class)
                .setParameter("noticeId", noticeId)
                .getResultList();

    }
}
