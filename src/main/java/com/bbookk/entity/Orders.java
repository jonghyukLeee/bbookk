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
    private String borrowerName;

    @OneToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private LocalDateTime orderTime;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public void setLend()
    {
        this.status = OrderStatus.LEND;
        this.book.setLend();
    }

    public Orders(Long borrowerId, String borrowerName, Book book) {
        this.borrowerId = borrowerId;
        this.borrowerName = borrowerName;
        this.book = book;
        this.orderTime = LocalDateTime.now();
        this.status = OrderStatus.REQUESTED;
    }
}
