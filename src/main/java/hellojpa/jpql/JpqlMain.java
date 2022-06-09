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
            String query = "select " +
                    "case when m.age <=10 then '학생요금' " +
                    "when m.age >=60 then '경로요금' " +
                    "else '일반요금' end " +
                    "from Member m";

            List<String> resultList = em.createQuery(query, String.class).getResultList();
            for (String s : resultList) {
                System.out.println(s);
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
