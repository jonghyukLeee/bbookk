package com.bbookk;

import com.bbookk.entity.Address;
import com.bbookk.entity.Book;
import com.bbookk.entity.Member;
import com.bbookk.repository.BookRepository;
import com.bbookk.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final MemberRepository memberRepository;
        private final BookRepository bookRepository;
        private final EntityManager em;
        private final BCryptPasswordEncoder encoder;

        public void dbInit1() {
            Address address = new Address("인천","남동구","만수동");
            Member admin = new Member("admin","admin","admin","123","010",address);
            Member m1 = new Member("박철준","cjfwns","박철준","123","01000000000",address);
            Member m2 = new Member("강훈","gns","강훈","123","01000000000",address);
            admin.setAdmin();
            admin.setPassword(admin.getPassword());
            m1.setPassword(m1.getPassword());
            m2.setPassword(m2.getPassword());
            em.persist(admin);
            em.persist(m1);
            em.persist(m2);

//            Book book = new Book("https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F5416922%3Ftimestamp%3D20220218170906"
//            ,"달러구트 꿈 백화점","이미예","팩토리나인","1165341905 9791165341909");
//            book.setMember(admin);
//            bookRepository.save(book);
            for(int i = 1; i <= 100; ++i)
            {
                Book b = new Book("img","book"+i,"author",
                        "publisher","isbn");
                b.setMember(admin);
                em.persist(b);
            }
        }
    }
}
