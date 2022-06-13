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

//            String query = "select m.username from Member m"; //상태 필드
//            String query = "select m.team from Member m"; //단일 값 연관 필드. 묵시적 내부 조인 발생, 추가탐색 못함.
//            String query = "select t.members from Team t"; //컬렉션 값 연관 경로. 묵시적 내부 조인 발생, 추가탐색 못함.
//            String query = "select m.username from Team t join t.members m"; //컬렉션 값 연관 경로. 명시적 조인 실행
            String query = "select p.name from Order o join o.product p"; //practice

//            되도록이면 묵시적인 내부 조인이 발생하지 않게 짜자.

//            Collection resultList = em.createQuery(query, Collection.class).getResultList();
            List<Collection> resultList = em.createQuery(query, Collection.class).getResultList();

            for (Object o : resultList) {
                System.out.println("o = " + o);
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
