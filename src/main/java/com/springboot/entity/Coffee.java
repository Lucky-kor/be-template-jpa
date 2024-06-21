package com.springboot.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Coffee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long coffeeId;

    @Column(nullable = false,length = 50)
    private String korName;

    @Column(nullable = false,length = 50)
    private String engName;
//같은 바닐라 라떼인데 원두를 다른거 쓸 수 도있고 가격도 바뀔 수 있으니까

    @Column(nullable = false)
    private int price;

    @Column(nullable = false,unique = true,length = 3)
    private String coffeeCode;

    @Column(nullable = false,updatable = false)
    private LocalDate createdAt = LocalDate.now();

    @Column(nullable = false,name = "LAST_MODIFIED_AT")
    private LocalDate modifiedAt = LocalDate.now();

    public Coffee(String korName, String engName, int price, String coffeeCode) {
        this.korName = korName;
        this.engName = engName;
        this.price = price;
        this.coffeeCode = coffeeCode;
    }
}
