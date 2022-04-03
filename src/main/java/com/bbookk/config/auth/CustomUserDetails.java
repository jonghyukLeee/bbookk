package com.bbookk.config.auth;

import com.bbookk.entity.Member;
import com.bbookk.repository.MemberRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Getter
public class CustomUserDetails implements UserDetails, OAuth2User {
    private final Member member;
    private Map<String, Object> attribute;

    public CustomUserDetails(Member member) {
        this.member = member;
    }

    public CustomUserDetails(Member member, Map<String, Object> attribute) {
        this.member = member;
        this.attribute = attribute;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attribute;
    }

    @Override
    public String getName() {
        return null;
    }

    //해당 유저의 권한을 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(()->member.getRole().toString());
        return collect;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }


    @Override
    public String getUsername() {
        return member.getLoginId();
    }


    // 아래는 아직 사용예정 x
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
