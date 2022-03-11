package com.bbookk.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orders {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    private Long borrower_id;

    private LocalDateTime orderTime;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public Orders(Long borrower_id) {
        this.borrower_id = borrower_id;
        this.orderTime = LocalDateTime.now();
        this.status = OrderStatus.REQUESTED;
    }
}
