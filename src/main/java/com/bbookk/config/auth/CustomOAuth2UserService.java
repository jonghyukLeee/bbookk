package com.bbookk.config.auth;

import com.bbookk.entity.Member;
import com.bbookk.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException
    {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId();

        OAuthAttributes attribute = OAuthAttributes.of(provider, oAuth2User.getAttributes());
        Member member = memberRepository.findByLoginId(attribute.getLoginId());
        //첫로그인
        if(member == null)
        {
            member = attribute.toEntity();
            memberRepository.save(member);
        }
        return new CustomUserDetails(member,oAuth2User.getAttributes());
    }
}
