package com.springboot.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    기본키를 설정하는건 테이블에서 판단해서 만들어 준다 - > 앱어딘가에 직접 id를 입력한경우에는 작동x
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    jpa에게 판단해 "줘"
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
//   시퀀스 : 기본키를 만들어주는 객체로  기본키를 만들어주는 것
//    테이블과 매핑되는 엔티티클래스를 만들때는 @entity어노테이션, id어노테이션,생성전략(기본키,GenerationType)은 실과 바늘이다.
    private Long memberId;
    @Column(nullable = false, updatable = false, unique = true)
    @Setter
//    컬럼명은 항상 매핑해주자 속성 - NotNull 이다 , updatable 수정가능여부, unique 고유한지
    private String email;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false, unique = true, length = 13)
    private String phone;

    //   양방향일때는 무조건 매핑해줘야한다 테이블입장에서 내가 참조해야하는 테이블의 기본키를 저장해뒀으니 @OneToMany(mappedBy = "member")
//    @OneToMany(mappedBy = "") 객체의 입장에서 ManyToOne이 나를 참조 하고 있는 변수의 명이 되어야한다 현재 Order에서 ManyToOne이 참고하고있는 외래키는 Member member 이기 떄문에
//     @ManyToOne
//    @JoinColumn(name = "MEMBER_ID")
////    JoinColume - > MemberTable에 name= "MEMBER_ID" 컬럼을 참조해주세요.
////    ManyToOne 에서 1로 갑니다 다대일 참조 에너테이션 실제로 Member 객체끼리 연결합니다.
////    외래키에서 단방향 매핑을 할 때 1인쪽 객체를 필드값에 적어줘야하고
////    디비에 저장할 때 매핑할 객체이름의 기본키를 DB에 컬럼명으로 해줘야 한다. - 테이블기준으로 생각해보면 n인쪽인 Order 테이블에서 1인 Member의 기본키를 가져야하니까
////    그 기본키를 컬럼에서 찾아 준 것임.
//    private Member member;
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    //    호출되는 시점에 나 자신으로 바인딩한다.
//    구현체가 나오는 시점에 본인으로 바꾸겠다.
    public void addOrder(Order order) {
        orders.add(order);
//        order의 입장에서도 연결이 필요한데
//        order가 가지고있는 member에
//        나 자신 Member(this)를 추가해야한다.
        if (order.getMember() != this) {
            order.addMember(this);
        }

    }

    //    createdAt , modifiedAt은 필수로 만들어준다


    @Column(nullable = false, updatable = false)
    //  JAVA LocalDateTime -> DB TIMESTAMP로 바뀐다
    private LocalDateTime createdAt = LocalDateTime.now();


    @Column(nullable = false, name = "LAST_MODIFIED_AT")
    //NAME = DB 컬럼에 저장할 때 이름을 바꿔준다.
    private LocalDateTime modifiedAt = LocalDateTime.now();

    // 엔티티클래스는 갖고있는데 DB에서는 사용안하겠다 매핑안하겠다. 임시로 사용해야할 데이터를 서버 메모리에 적재하는 용도로 써줌

//    @Transient 컬럼은 만들지 않겠다.
//    private String age;


    public Member(String email, String name, String phone) {
        this.email = email;
        this.name = name;
        this.phone = phone;
    }

    public Member(String email) {
        this.email = email;
    }
}
