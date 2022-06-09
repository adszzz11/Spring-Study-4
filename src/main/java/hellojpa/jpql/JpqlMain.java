package hellojpa.jpql;



import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class JpqlMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();

            //entity
            List<Team> resultList = em.createQuery("select m.team from Member m join m.team t", Team.class).getResultList();
            //embedded
            List<Address> resultList1 = em.createQuery("select o.address from Order o", Address.class).getResultList();
            //scala
            List<Object[]> resultList2 = em.createQuery("select distinct m.username, m.age from Member m").getResultList();

            for (Object o : resultList2) {
                Object[] temp= (Object[])o;
                System.out.println(temp[0]+", "+temp[1]);
            }


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
