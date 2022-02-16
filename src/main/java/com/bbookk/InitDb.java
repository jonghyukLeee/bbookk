package com.bbookk;

import com.bbookk.entity.Address;
import com.bbookk.entity.Member;
import com.bbookk.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

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
        private final BCryptPasswordEncoder encoder;

        public void dbInit1() {
            String password = encoder.encode("123");
            Member admin = new Member("admin","admin",password,"010",null);
            admin.setAdmin();
            memberRepository.save(admin);
        }
    }
}
