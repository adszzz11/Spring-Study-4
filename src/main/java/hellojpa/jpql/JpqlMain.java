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


            //XML 우선, XML으로 다르게 배포하기 가능
            Member member1 = em.createNamedQuery("Member.findByUsername", Member.class)
                    .setParameter("username",member.getUsername())
                    .getSingleResult();
            System.out.println("member1 = " + member1);

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
