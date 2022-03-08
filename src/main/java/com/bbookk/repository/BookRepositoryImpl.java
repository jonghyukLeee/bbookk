package com.bbookk.repository;

import com.bbookk.entity.Book;
import com.bbookk.repository.dto.*;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import java.util.List;
import static com.bbookk.entity.QBook.book;
import static com.bbookk.entity.QMember.member;

public class BookRepositoryImpl implements BookRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public BookRepositoryImpl(EntityManager em)
    {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<LibraryDto> getLibrary(Long id,Pageable pageable) {
        List<LibraryDto> res = queryFactory
                .select(new QLibraryDto(
                        book.imgSource,
                        book.bookName,
                        book.author,
                        book.publisher,
                        book.isbn
                ))
                .from(book)
                .where(
                        book.member.id.eq(id)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        JPAQuery<Book> countQuery = queryFactory.selectFrom(book)
                .where(book.member.id.eq(id));

        return PageableExecutionUtils.getPage(res,pageable, countQuery::fetchCount);
    }

    @Override
    public Book findMemberBook(Long id, String bookName) {
        return queryFactory
                .selectFrom(book)
                .where(
                        book.bookName.eq(bookName),
                        book.member.id.eq(id)
                ).fetchOne();
    }

    @Override
    public Page<FindBooksDto> findBooks(String gu, String query, Pageable pageable) {

        System.out.println("offset = "+pageable.getOffset());
        //쿼리결과
        List<FindBooksDto> results = queryFactory
                .select(new QFindBooksDto(
                        member.id,
                        book.imgSource,
                        book.bookName,
                        book.author,
                        book.publisher,
                        member.name
                )).from(book)
                .leftJoin(book.member, member)
                .where(
                        member.address.gu.eq(gu),
                        book.bookName.contains(query)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        //카운트
        JPAQuery<Book> countQuery = queryFactory.selectFrom(book)
                .leftJoin(book.member, member)
                .where(
                        member.address.gu.eq(gu),
                        book.bookName.contains(query)
                );
        return PageableExecutionUtils.getPage(results,pageable, countQuery::fetchCount);
    }

//    @Override
//    public BookDetailsDto getBookDetails(Long id, String bookName) {
//        Book findBook = queryFactory.selectFrom(book)
//                .leftJoin(book.member, member)
//                .where(
//                        member.id.eq(id),
//                        book.bookName.eq(bookName)
//                ).fetchOne();
//
//        Member findMember = queryFactory.selectFrom(QMember.member)
//                .where(QMember.member.id.eq(id))
//                .fetchOne();
//
//        return new BookDetailsDto(findBook.getImgSource(),findBook.getBookName(),
//                findMember.getName(),findBook.getStatus());
//    }
}
