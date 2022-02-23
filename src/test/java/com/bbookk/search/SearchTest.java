package com.bbookk.search;

import com.bbookk.entity.Address;
import com.bbookk.entity.Book;
import com.bbookk.entity.Member;
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

    @Test
    @Transactional
    public void statusTest()
    {
        Book book = new Book("img","name","auth","pub","isbn");
        em.persist(book);

    }
}
