package hellojpa;

public class ValueMain {
    public static void main(String[] args) {

        Address ad1 = new Address("city", "street", "10000");
        Address ad2 = new Address("city", "street", "10000");

        System.out.println("(ad1 == ad2) = " + (ad1 == ad2));

        System.out.println("(ad1.equals(ad2)) = " + (ad1.equals(ad2)));
    }
}
