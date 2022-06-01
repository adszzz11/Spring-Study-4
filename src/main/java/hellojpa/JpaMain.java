package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

            Member member1 = new Member();
            member1.setUsername("hello");
            Member member2 = new Member();
            member2.setUsername("hello");

            em.persist(member1);
            em.persist(member2);

            em.flush();
            em.clear();

            Member reference = em.getReference(Member.class, member1.getId());

            Hibernate.initialize(reference);
            System.out.println(emf.getPersistenceUnitUtil().isLoaded(reference));
            System.out.println("reference = " + reference.getUsername());
            System.out.println(emf.getPersistenceUnitUtil().isLoaded(reference));
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
            emf.close();
        }
    }
}
