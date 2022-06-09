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

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);


                Member member = new Member();
                member.setUsername("member 1");
                member.setTeam(team);
                member.setMemberType(MemberType.ADMIN);
                member.setAge(10);

                em.persist(member);



            em.flush();
            em.clear();

//            String q = "select m.username, 'HELLO', TRUE from Member m where m.memberType=hellojpa.jpql.MemberType.USER";
            String q = "select m.username, 'HELLO', TRUE from Member m where m.memberType=hellojpa.jpql.MemberType.USER";

            List<Object[]> resultList = em.createQuery(q).getResultList();

            for (Object[] objects : resultList) {
                System.out.println("objects = " + objects[0]);
                System.out.println("objects = " + objects[1]);
                System.out.println("objects = " + objects[2]);

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
