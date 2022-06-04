package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.*;
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

            Team team = new Team();
            team.setName("TEAM1");

            member1.setTeam(team);
            em.persist(team);

            em.persist(member1);

            em.flush();
            em.clear();

            Member findMember = em.find(Member.class, member1.getId());

            List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();



            System.out.println("findMember.getTeam() = " + findMember.getTeam());

            System.out.println("==================");
            System.out.println(findMember.getTeam().getName());
            System.out.println("==================");

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
            emf.close();
        }
    }
}
