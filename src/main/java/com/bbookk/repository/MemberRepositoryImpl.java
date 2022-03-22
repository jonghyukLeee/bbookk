package com.bbookk.repository;

import com.bbookk.entity.Book;
import com.bbookk.entity.Member;
import com.bbookk.entity.QMember;
import com.bbookk.repository.dto.ProfileDto;
import com.bbookk.repository.dto.QProfileDto;
import com.querydsl.jpa.impl.JPAQueryFactory;


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
    public Member findByLoginId(String loginId) {
        return queryFactory.selectFrom(QMember.member)
                .where(QMember.member.loginId.eq(loginId))
                .fetchOne();
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
    public ProfileDto getProfile(Long memberId) {
        return queryFactory.select(new QProfileDto(
                member.name,
                member.address.gu,
                member.cash
        )).from(member)
                .where(
                        member.id.eq(memberId)
                )
                .fetchOne();
    }


}
