package com.bbookk.entity;

import com.bbookk.repository.BookRepository;
import com.bbookk.repository.MemberRepository;
import com.bbookk.service.MemberService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

import static com.bbookk.entity.QMember.member;

@SpringBootTest
class UserTest {

    @Autowired
    EntityManager em;
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    MemberService memberService;

    @Test
    public void findByLoginId()
    {
        Boolean res = memberService.isDuplicatedLoginId("cjfwnsa");
        System.out.println(res);
    }

    @Test
    @Transactional
    public void findMember()
    {
        Optional<Member> findMember = memberRepository.findById(1L);
        System.out.println(em.contains(findMember.get()));
    }



    @Test
    @Transactional
    public void addBook()
    {
        Member admin = memberRepository.findById(1L).get();
        Book book = new Book("123","123","123","123","123");
        book.setMember(admin);
        em.persist(book);
        memberService.addBook(admin.getId(),book);
        em.flush();
        List<Book> res = em.createQuery("select b from Book b", Book.class).getResultList();
        for (Book re : res) {
            System.out.println(re.getBookName()+re.getMember().getId());
        }

    }

}