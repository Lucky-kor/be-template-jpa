package com.springboot;

import com.springboot.entity.Member;
import com.springboot.entity.Order;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

@Configuration
//빈설정정보를 알려줌
public class JpaBasicConfig {
    private EntityManager em;
    private EntityTransaction tx;


    @Bean
//    람다식을 어플리케이션 실행시점에 실행시킴
    public CommandLineRunner testJpaBasicRunner(EntityManagerFactory emFactory) {
        this.em = emFactory.createEntityManager();
        this.tx = em.getTransaction();


        return args -> {
            // TODO 이 곳에 학습할 코드를 타이핑하세요!

            tx.begin();
            Member currentMember = new Member("arcanacoach@gmail","제리","010-5454-5452");

            Order order = new Order();
            order.addMember(currentMember);
            currentMember.addOrder(order);
            em.persist(order);
            em.persist(currentMember);

            tx.commit();
            Order findOrder = em.find(Order.class,1L);
            System.out.printf("findOrderMemberName : %s, findOrderEmail : %s",findOrder.getMember().getName(),findOrder.getMember().getEmail());


            Member findMember = em.find(Member.class,1L);
            List<Order> orders = findMember.getOrders();

            System.out.println(orders.size());
            orders.stream()
                    .forEach(currentOrder -> {
                        System.out.printf("findOrderId: %d, findOrderStatus : %s", findOrder.getOrderId(),findOrder.getOrderStatus());
                    });
//            tx.begin();
//            Member member = em.find(Member.class, 1L);
//            member.setEmail("agaeg@gmail");
//            tx.commit();
//            System.out.println("MemberId: " + member.getMemberId());
//            종료되지는 않는데 수정하는 쿼리도 안나감

        };
    }

//    private void example01() {
//
//        Member member = new Member("arcanacoach@gmail.com");
//        em.persist(member);
//        Member resultMember = em.find(Member.class, 1L);
//        System.out.printf("Id : %d, email: %s\n", resultMember.getMemberId(), resultMember.getEmail());
//
//    }
//
//    private void example02() {
//        tx.begin();
////        트랙잭션 시작장소
//        Member member = new Member("arcanacoach@gmail.com");
//        em.persist(member);
////        영속성컨텍스트에 저장하는 메서드
////
//        tx.commit();
////        1차 캐시에 멤버 객체는 두고 쓰기지연 저장소에있는 SQL문만 지움
////        실제로 멤버를 테이블에 저장
//        Member resultMember = em.find(Member.class, 1L);
//
//        System.out.printf("Id : %d, email: %s\n", resultMember.getMemberId(), resultMember.getEmail());
//
//        Member resultMember1 = em.find(Member.class, 2L);
//
//        System.out.println(resultMember1 == null);
//
//    }
//
//    private void example03() {
//        tx.begin();
//
//        Member member1 = new Member("lucky@gmail.com");
//        Member member2 = new Member("latte@gmail.com");
////      이때는 자바에서만 객체가 있는거라 영속성컨텍스트에는 없음
//        em.persist(member1);
//        em.persist(member2);
////       -> persist !! 이 코드가 실행될 때 영속성컨텍스트에 들어감
//        tx.commit();
//
//    }
//
//    private void example04(){
//        tx.begin();
//        em.persist(new Member("lucky@gmail.com"));
////        이 시점의 상태를 보관한다 - 스냅샷
//        tx.commit();
//
//        tx.begin();
//        Member member1 = em.find(Member.class,1L);
////        영속성컨텍스트에 1차캐시안에는 남아있기때문에 select 쿼리문실행안하고 1차캐시에 있는 member 정보를 보내준다
//        member1.setEmail("latte@gmail.com");
////        DB를 바꾸는게 아니고 자바객체만 변경한 것이니까 DB에는 바뀌지 않는게 정상이지만
////        1차 캐시에 있는 정보가 변경 되었다면 그 사항을 JPA가 찾아서 바꿔주게 된다
////        setter -> update 실행된다 객체가 변경되면 DB도 변경해야한다 파악
//        tx.commit();
//    }
//
//    private void example05(){
//        tx.begin();
//        em.persist(new Member("kyj930610@gmail.com"));
//        tx.commit();
//
//        tx.begin();
//        Member member = em.find(Member.class,1L);
//        em.remove(member);
////        1차캐시에 member를 지우고 쿼리를 남겨둔다.
////        커밋 시점에 delete쿼리를 보내서 db에서도 지움
//        tx.commit();
//
////        1차캐시 확인 후에 변경사항이 있는 상태를 스냅샷으로 보관하고 다음단계에
//    }
}

