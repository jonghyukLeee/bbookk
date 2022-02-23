package com.bbookk.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Orders {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    private Long borrower_id;

    private LocalDateTime orderTime;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public String getsStatusToString()
    {
        return status == OrderStatus.REQUESTED ? "대여요청" : "대여중";
    }
}
