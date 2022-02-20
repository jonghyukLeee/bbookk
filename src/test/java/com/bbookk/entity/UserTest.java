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
    @Transactional
    public void saveTest()
    {
        Address address = new Address("as","as","as");
        Member u1 = new Member("u1","asd","123","010",address);
        Member u2 = new Member("u2","asdf","123","010",address);

        memberRepository.save(u1);
        memberRepository.save(u2);

        List<Member> res = em.createQuery("select u from Member u", Member.class).getResultList();

    }
    @Test
    @Transactional
    public void findByLoginId()
    {
        Address address = new Address("as","as","as");
        Member u1 = new Member("u1","asd","123","010",address);
        Member u2 = new Member("u2","asdf","123","010",address);

        memberRepository.save(u1);
        memberRepository.save(u2);

        System.out.println("fst"+ memberService.isDuplicateId("asd"));
        System.out.println("sec"+ memberService.isDuplicateId("asdd"));
        System.out.println(ResponseEntity.ok(true));
    }

    @Test
    public void findMember()
    {
        Address address = new Address("as","as","as");
        Member u1 = new Member("u1","asd","123","010",address);
        memberRepository.save(u1);

        System.out.println(memberService.isMember("as","12"));
    }

    @Test
    public void encodeTest()
    {
        Address address = new Address("as","as","as");
        Member member = new Member("u1","asd","123","010",address);

        member.setPassword(member.getPassword());
        memberRepository.save(member);

        List<Member> res = em.createQuery("select m from Member m", Member.class).getResultList();

        System.out.println(res);
    }
    
    @Test
    public void addCash()
    {
        Address address = new Address("as","as","as");
        Member member = new Member("u1","asd","123","010",address);

        memberRepository.save(member);
        System.out.println("check"+em.contains(member));
    }

    @Test
    public void addBook()
    {
        Address address = new Address("as","as","as");
        Member member = new Member("u1","asd","123","010",address);

        em.persist(member);
        Book book = new Book("123","123","123","123","123");
        book.setMember(member);
        em.persist(book);
        memberService.addBook(member,book);
        em.flush();
        em.clear();

        Optional<Member> getMember = memberRepository.findById(member.getId());

        System.out.println(getMember.get().getBooks().size());

    }

    @Test
    @Transactional
    public void deleteBook()
    {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        Address address = new Address("as","as","as");
        Member member = new Member("u1","asd","123","010",address);

        em.persist(member);

        Book book1 = new Book("123","123","123","123","123");
        Book book2 = new Book("123","456","123","123","123");

        em.persist(book1);
        em.persist(book2);

        book1.setMember(member);
        book2.setMember(member);

        em.flush();
        em.clear();

        List<Book> res = em.createQuery("select b from Book b", Book.class).getResultList();
        for (Book re : res) {
            System.out.println(re.getBookName());
        }

    }
}