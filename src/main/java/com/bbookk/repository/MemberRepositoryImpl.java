package com.bbookk.repository;

import com.bbookk.entity.Book;
import com.bbookk.repository.dto.FindBooksDto;
import com.bbookk.repository.dto.LibraryDto;
import com.bbookk.repository.dto.QFindBooksDto;
import com.bbookk.repository.dto.QLibraryDto;
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
                        book.isbn,
                        book.status
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
                        book.imgSource,
                        book.bookName,
                        book.author,
                        book.publisher,
                        member.name,
                        book.status
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

}
