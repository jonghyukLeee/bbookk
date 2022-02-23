package com.bbookk.repository;

import com.bbookk.entity.Book;
import com.bbookk.entity.Member;
import com.bbookk.entity.QBook;
import com.bbookk.entity.QMember;
import com.bbookk.repository.dto.*;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.bbookk.entity.QMember.member;
import static com.bbookk.entity.QBook.book;

public class MemberRepositoryImpl implements MemberRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public boolean isDuplicateBook(Long id, String bookName) {

        List<Book> res = queryFactory
                .selectFrom(book)
                .leftJoin(book.member, member)
                .where(
                        member.id.eq(id),
                        book.bookName.eq(bookName)
                )
                .fetch();
        return !res.isEmpty();
    }

    @Override
    public List<LibraryDto> getLibrary(Long id) {
        return queryFactory
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
                .fetch();
    }

    @Override
    public Book findMemberBook(Long id, String bookName) {
        return queryFactory
                .selectFrom(book)
                .leftJoin(book.member, member)
                .where(
                        book.bookName.eq(bookName),
                        book.member.id.eq(id)
                )
                .fetchOne();
    }

    @Override
    public Page<FindBooksDto> findBooks(String gu, String query, Pageable pageable) {

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

    @Override
    public BookDetailsDto getBookDetails(Long id, String bookName) {
        Book findBook = queryFactory.selectFrom(book)
                .leftJoin(book.member, member)
                .where(
                        member.id.eq(id),
                        book.bookName.eq(bookName)
                ).fetchOne();

        Member findMember = queryFactory.selectFrom(QMember.member)
                .where(QMember.member.id.eq(id))
                .fetchOne();

        return new BookDetailsDto(findBook.getImgSource(),findBook.getBookName(),
                findMember.getName(),findBook.getStatus());

    }

}
