package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        tx.begin();
        try {
            List<Member> resultList1 = em.createQuery(
                    "select m from Member m where m.username like 'kim'",
                    Member.class
            ).getResultList();

            CriteriaQuery<Member> query= cb.createQuery(Member.class);
            Root<Member> m = query.from(Member.class);
            CriteriaQuery<Member> cq = query.select(m);

            List<Member> resultList2 = em.createNativeQuery("select MEMBER_ID, city, street, zipcode, USERNAME from MEMBER", Member.class).getResultList();


            String username="lee";
            if(username != null) {
                cq.where(cb.equal(m.get("username"), "kim"));
            }
            em.createQuery(cq).getResultList();


            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
            emf.close();
        }
    }
}
