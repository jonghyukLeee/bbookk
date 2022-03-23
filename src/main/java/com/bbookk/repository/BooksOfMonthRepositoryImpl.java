package com.bbookk.repository;

import com.bbookk.entity.BooksOfMonth;
import com.bbookk.entity.QBooksOfMonth;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

import java.util.List;

import static com.bbookk.entity.QBooksOfMonth.booksOfMonth;

public class BooksOfMonthRepositoryImpl implements BooksOfMonthRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public BooksOfMonthRepositoryImpl(EntityManager em)
    {
        this.queryFactory = new JPAQueryFactory(em);
    }
    @Override
    public BooksOfMonth findByBookName(String bookName) {
        return queryFactory.selectFrom(booksOfMonth)
                .where(booksOfMonth.bookName.eq(bookName))
                .fetchOne();
    }

    @Override
    public List<BooksOfMonth> getBooksOfMonth() {
        return queryFactory.selectFrom(booksOfMonth)
                .orderBy(booksOfMonth.registerCnt.desc(),booksOfMonth.registerTime.asc())
                .limit(3)
                .fetch();
    }
}
