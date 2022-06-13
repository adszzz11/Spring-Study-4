package hellojpa.jpql;



import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;
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

            Team team2 = new Team();
            team2.setName("teamB");
            em.persist(team2);

            Member member = new Member();
            member.setUsername("회원1");
            member.setTeam(team);
            member.setMemberType(MemberType.ADMIN);
            member.setAge(10);

            em.persist(member);

            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setTeam(team2);
            member2.setMemberType(MemberType.ADMIN);
            member2.setAge(10);

            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.setTeam(team2);
            member3.setMemberType(MemberType.ADMIN);
            member3.setAge(10);

            em.persist(member3);

            em.flush();
            em.clear();

//            String query = "select m from Member m join fetch m.team";
//            String query = "select distinct t from Team t join fetch t.members";
//            String query = "select distinct t from Team t join fetch t.members m"; // 원칙적으로 사용하면 안됨. fetch를 여러번 시도할 때만 할 것
//            String query = "select t from Team t join t.members m"; // Collection Type 사용, select 추가로 나감. paging 사용 시 문제 생김
//            String query = "select m from Member m join fetch m.team"; // 해결1. 서로 바꾸기
            String query = "select t from Team t"; // 해결2. Lazy Loading, N+1 문제 발생
            //해결 3. BatchSize
            //해결 4. DTO


            List<Team> resultList = em.createQuery(query, Team.class).setFirstResult(0).setMaxResults(2).getResultList();

            for (Team t : resultList) {
                System.out.println("t = " + t.getName() + "," + t + ", members : " + t.getMembers().size() );
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
