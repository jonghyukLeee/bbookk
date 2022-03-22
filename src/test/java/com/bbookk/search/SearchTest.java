package com.bbookk.search;

import com.bbookk.entity.Address;
import com.bbookk.entity.Book;
import com.bbookk.entity.Member;
import com.bbookk.repository.BookRepository;
import com.bbookk.repository.MemberRepository;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static com.bbookk.entity.QBook.book;
import static com.bbookk.entity.QMember.member;

@SpringBootTest
public class SearchTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Autowired
    BookRepository bookRepository;

    @Test
    @Transactional
    public void statusTest()
    {
        Book book = new Book("img","name","auth","pub","isbn");
        em.persist(book);

    }

    @Test
    void findBookTest()
    {
        //달러구트 꿈 백화점
        //memberId 1

        Book res = bookRepository.findMemberBook(1L, "달러구트 꿈 백화점");
        System.out.println(res.getImgSource());
    }

    @Test
    void queryTest()
    {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        String gu = "남동구";
        String query = "";
//        List<Book> fst = queryFactory.selectFrom(book)
//                .leftJoin(book.member, member)
//                .where(
//                        member.address.gu.eq(gu),
//                        book.bookName.contains(query)
//                ).fetch();
    }
}
