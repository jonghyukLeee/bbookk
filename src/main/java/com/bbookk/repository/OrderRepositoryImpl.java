package com.bbookk.repository;

import com.bbookk.entity.OrderStatus;
import com.bbookk.repository.dto.BorrowListDto;
import com.bbookk.repository.dto.QBorrowListDto;
import com.bbookk.repository.dto.QRequestsDto;
import com.bbookk.repository.dto.RequestsDto;
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
    public List<RequestsDto> getRequestedOrders(Long id) {
        return queryFactory.select(new QRequestsDto(
                        orders.id,
                        book.imgSource,
                        book.bookName,
                        orders.borrowerName
                )).from(orders)
                .leftJoin(orders.book,book)
                .where(
                        book.member.id.eq(id),
                        orders.status.eq(OrderStatus.REQUESTED)
                ).fetch();
    }

    @Override
    public List<BorrowListDto> getBorrowList(Long id) {
        return queryFactory.select(new QBorrowListDto(
                book.imgSource,
                book.bookName,
                orders.orderTime,
                book.status
        )).from(orders)
                .leftJoin(orders.book, book)
                .where(
                        orders.borrowerId.eq(id)
                ).fetch();
    }
}
