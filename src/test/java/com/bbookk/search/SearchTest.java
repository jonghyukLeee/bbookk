package com.bbookk.search;

import com.bbookk.entity.Address;
import com.bbookk.entity.Book;
import com.bbookk.entity.BookStatus;
import com.bbookk.entity.Member;
import com.bbookk.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import java.util.Optional;

@SpringBootTest
public class SearchTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Test
    public void defaultSearch()
    {

    }

    public void init()
    {
        Address address = new Address("인천","남동구","만수동");
        Member m1 = new Member("name","loginId","password","phone",address);
        m1.setPassword(m1.getPassword());
        em.persist(m1);

        for(int i = 1; i <= 100; ++i)
        {
            Book b = new Book("img","book"+i,"author",
                    "publisher","isbn");
            b.setMember(m1);
            em.persist(b);
        }

        em.flush();
        em.clear();


    }
}
