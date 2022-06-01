package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
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



            System.out.println("reference = " + reference.getClass());
            Member m1 = em.find(Member.class, member1.getId());
            Member m2 = em.getReference(Member.class, member2.getId());
            System.out.println("m1.getClass() = " + m1.getClass());
            System.out.println("reference = " + reference.getClass());

            System.out.println(m1 == reference);
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
            emf.close();
        }
    }
}
