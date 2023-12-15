package databaseProject.dbp.repository;

import databaseProject.dbp.domain.Member;
import javax.persistence.EntityManager;
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

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public Member findByEmail(String email){
         List<Member> result =  em.createQuery("select m from Member m where m.email= :email", Member.class)
                                .setParameter("email", email)
                                .getResultList();

         return result.isEmpty() ? null:result.get(0);
    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name= :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

    public boolean existsByEmailAndPassword(String email, String password) {
        Long count = (Long) em.createQuery("select count(m) from Member m where m.email = :email and m.password = :password")
                .setParameter("email", email)
                .setParameter("password", password)
                .getSingleResult();


        return count > 0;
    }


}
