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
            Address address = new Address("oldCity", "a", "a");
            Member member = new Member();
            Address address1 = new Address(address.getCity(), address.getStreet(), address.getZipcode());
            member.setUsername("asdf");
            member.setHomeAaddress(address);
            em.persist(member);

            Member member2 = new Member();
            member2.setUsername("asdfasdf");
            member2.setHomeAaddress(address1);
            em.persist(member2);

            member2.getHomeAaddress().setCity("newCity");



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
