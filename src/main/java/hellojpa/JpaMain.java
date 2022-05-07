package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.transaction.Transactional;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            //create Member
            Member member=new Member();
            member.setId(3L);
            member.setName("hello");

            em.persist(member);

            //find Member
//            Member member1 = em.find(Member.class, 1L);
//            System.out.println("member1.getName() = " + member1.getName());
//            System.out.println("member1.getId() = " + member1.getId());
            List<Member> result = em.createQuery("select m from Member m", Member.class)
//                    .setFirstResult(0)
//                    .setMaxResults(10)
                    .getResultList();

            for (Member member1 : result) {
                System.out.println("member1.getName() = " + member1.getName());
            }


            //modify Member
//            member1.setName("thisisTest");

            //remove Member
//            em.remove(member1);

            tx.commit();

        }catch (Exception e) {
            tx.rollback();
        }finally {
            em.close();
            emf.close();
        }
    }
}
