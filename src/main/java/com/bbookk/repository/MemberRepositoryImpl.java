package com.bbookk.repository;

import com.bbookk.entity.Book;
import com.bbookk.entity.BookStatus;
import com.bbookk.repository.dto.LibraryDto;
import com.bbookk.repository.dto.NoneBooksDto;
import com.bbookk.repository.dto.QLibraryDto;
import com.bbookk.repository.dto.QNoneBooksDto;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

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
    public List<NoneBooksDto> findBooksByGu(String gu) {
        return queryFactory
                .select(new QNoneBooksDto(
                        book.imgSource,
                        book.bookName,
                        book.author,
                        book.publisher,
                        member.name
                )).from(book)
                .leftJoin(book.member, member)
                .where(
                        member.address.gu.eq(gu),
                        book.status.eq(BookStatus.NONE)
                )
                .fetch();
    }
}
