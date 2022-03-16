package com.bbookk.repository;

import com.bbookk.entity.OrderStatus;
import com.bbookk.repository.dto.QRequestedBooksDto;
import com.bbookk.repository.dto.RequestedBooksDto;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.bbookk.entity.QBook.book;
import static com.bbookk.entity.QOrders.orders;

public class OrderRepositoryImpl implements OrderRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public OrderRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<RequestedBooksDto> getRequestedOrders(Long id) {
        return queryFactory.select(new QRequestedBooksDto(
                orders.book.imgSource,
                orders.book.bookName
                ))
                .from(orders)
                .where(
                        orders.book.id.eq(id),
                        orders.status.eq(OrderStatus.REQUESTED))
                .fetch();
    }
}
