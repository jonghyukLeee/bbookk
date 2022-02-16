package com.bbookk.auth;

import com.bbookk.entity.Member;
import com.bbookk.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * config에서 .loginProcessingUrl("/user/login")
 * 로 세팅을 해두었기 때문에, 해당 url 요청이 오게되면
 * 현재 서비스에서 UserDetailsService 타입으로, loadUserByUsername 함수가 실행된다.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    //String 타입의 파라미터를 폼에서의 name과 일치시켜줘야함.
    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Member findMember = memberRepository.findByLoginId(loginId);
        if(findMember == null) throw new UsernameNotFoundException(loginId);
        return new CustomUserDetails(findMember);
        }
    }

