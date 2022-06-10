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
            member.setUsername("관리자");
            member.setTeam(team);
            member.setMemberType(MemberType.ADMIN);
            member.setAge(10);

            em.persist(member);
            Member member2 = new Member();
            member2.setUsername("관리자2");
            member2.setTeam(team);
            member2.setMemberType(MemberType.ADMIN);
            member2.setAge(10);

            em.persist(member2);

            em.flush();
            em.clear();
//            String query = "select nullif(m.username, '관리자') from Member m";
//            String query = "select concat('a', 'b') from Member m";
//            String query = "select concat('a', 'b') from Member m";
//            String query = "select substring(m.username, 2, 3) from Member m";
//            String query = "select locate('de','abcdefg') from Member m";
//            String query = "select size(t.members) from Team t";
//            String query = "select index(t.members) from Team t";
//            String query = "select function('group_concat', m.username) from Member m";
            String query = "select group_concat(m.username) from Member m";


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
