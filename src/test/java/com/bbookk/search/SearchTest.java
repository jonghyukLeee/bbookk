package com.bbookk.search;

import com.bbookk.entity.Address;
import com.bbookk.entity.Book;
import com.bbookk.entity.Member;
import com.bbookk.repository.BookRepository;
import com.bbookk.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

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
}
