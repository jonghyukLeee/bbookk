package com.bbookk.repository;

import com.bbookk.entity.Book;
import com.bbookk.entity.OrderStatus;
import com.bbookk.entity.Orders;
import com.bbookk.repository.dto.*;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.bbookk.entity.QBook.book;
import static com.bbookk.entity.QMember.member;
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
    public List<RequestsDto> getReturnOrders(Long id) {
        return queryFactory.select(new QRequestsDto(
                        orders.id,
                        book.imgSource,
                        book.bookName,
                        orders.borrowerName
                )).from(orders)
                .leftJoin(orders.book,book)
                .where(
                        book.member.id.eq(id),
                        orders.status.eq(OrderStatus.RETURN)
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

    @Override
    public BorrowListDetailsDto getBorrowListDetails(Long id, String bookName) {
        BorrowListDetailsDto res = queryFactory.select(new QBorrowListDetailsDto(
                        book.imgSource,
                        book.bookName,
                        orders.orderTime
                )).from(orders)
                .join(orders.book,book)
                .where(
                        orders.borrowerId.eq(id),
                        book.bookName.eq(bookName)
                ).fetchOne();

        //lenderName
        String lenderName = queryFactory.select(orders.member.name)
                .from(orders)
                .join(orders.member,member)
                .where(orders.borrowerId.eq(id)).fetchOne();

        res.setLenderName(lenderName);

        return res;
    }

    @Override
    public Orders findByBorrowerIdAndBook(Long borrowerId, String bookName) {
        return queryFactory.selectFrom(orders)
                .where(
                        orders.borrowerId.eq(borrowerId),
                        orders.book.bookName.eq(bookName)
                ).fetchOne();
    }

    @Override
    public Book findBookById(Long id) {
        return queryFactory.select(orders.book)
                .from(orders)
                .join(orders.book,book)
                .where(orders.id.eq(id)).fetchOne();
    }
}
