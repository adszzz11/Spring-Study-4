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
                member.setAge(10);

                em.persist(member);



            em.flush();
            em.clear();

//            String q = "select m from Member m inner join m.team t";
//            String q = "select m from Member m left outer join m.team t";
//            String q = "select m from Member m, Team t where m.username = t.name";
//            String q = "select m from Member m left join m.team t on t.name = 'teamA'";
            String q = "select m from Member m left join Team t on m.username = t.name";
            List<Member> resultList = em.createQuery(q, Member.class).getResultList();


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
