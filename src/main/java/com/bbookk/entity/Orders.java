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
    private Long borrowerId;
    @OneToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private LocalDateTime orderTime;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public Orders(Long borrowerId, Book book) {
        this.borrowerId = borrowerId;
        this.book = book;
        this.orderTime = LocalDateTime.now();
        this.status = OrderStatus.REQUESTED;
    }
}
