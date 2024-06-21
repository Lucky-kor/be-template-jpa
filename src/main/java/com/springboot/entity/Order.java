package com.springboot.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "ORDERS")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;
    //    이넘타입을 담을때 문자열만쓴다 ! 외워야한다.
//    별표 2개 짜리!
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.ORDER_COMPLETE;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false, name = "LAST_MODIFIED_AT")
    private LocalDateTime modifiedAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
//    JoinColume - > MemberTable에 name= "MEMBER_ID" 컬럼을 참조해주세요.
//    ManyToOne 에서 1로 갑니다 다대일 참조 에너테이션 실제로 Member 객체끼리 연결합니다.
//    외래키에서 단방향 매핑을 할 때 1인쪽 객체를 필드값에 적어줘야하고
//    디비에 저장할 때 매핑할 객체이름의 기본키를 DB에 컬럼명으로 해줘야 한다. - 테이블기준으로 생각해보면 n인쪽인 Order 테이블에서 1인 Member의 기본키를 가져야하니까
//    그 기본키를 컬럼에서 찾아 준 것임.
    private Member member;

    //   1:N일경우엔 항상 만든 메서드로 이어줘야한다
//            Order order = new Order();
//            order.addMember(currentMember);
//            em.persist(order);
    public void addMember(Member member) {
        this.member = member;
//        member의 입장에서 연결이 필요한데
//        member가 가지고 있는 orders(List)에
//        나 자신 Order(this)를 추가합니다.
        if (!member.getOrders().contains(this)) {
            member.addOrder(this);
        }

    }

    public enum OrderStatus {
        ORDER_REQUEST(1, "주문 요청"),
        ORDER_CONFIRM(2, "주문 확정"),
        ORDER_COMPLETE(3, "주문 완료"),
        ORDER_CANCEL(4, "주문 취소");


        @Getter
        private int stepNumber;
        @Getter
        private String stepDescription;

        OrderStatus(int stepNumber, String stepDescription) {
            this.stepNumber = stepNumber;
            this.stepDescription = stepDescription;
        }
    }

}
