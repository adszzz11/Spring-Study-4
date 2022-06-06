package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Member member = new Member();
            member.setUsername("member");
            member.setHomeAaddress(new Address("a", "b", "c"));
            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("피자");
            member.getFavoriteFoods().add("햄버거");

            member.getAddressHistory().add(new Address("a1", "b", "c"));
            member.getAddressHistory().add(new Address("a2", "b", "c"));

            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("------------------------------------------------------------");
            Member findMember = em.find(Member.class, member.getId());

            findMember.getHomeAaddress().setCity("newCity");
            findMember.setHomeAaddress(new Address(
                    "newCity",
                    findMember.getHomeAaddress().getStreet(),
                    findMember.getHomeAaddress().getZipcode()
                )
            );

            // 치킨 to 한식
            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("한식");

            findMember.getAddressHistory().remove(new Address("a2", "b", "c"));
            findMember.getAddressHistory().add(new Address("newCity", "b", "c"));

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
