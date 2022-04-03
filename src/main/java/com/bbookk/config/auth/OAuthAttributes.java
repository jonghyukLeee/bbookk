package com.bbookk.config.auth;

import com.bbookk.entity.Member;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {

    private String name;
    private String loginId;

    @Builder
    public OAuthAttributes(String name, String loginId)
    {
        this.name = name;
        this.loginId = loginId;
    }

    public static OAuthAttributes of(String provider, Map<String, Object> attributes)
    {
        if(provider.equals("google")) return ofGoogle(attributes);
        return ofKakao(attributes);
    }

    private static OAuthAttributes ofKakao(Map<String, Object> attributes) {
        Map<String,Object> account = (Map<String, Object>)attributes.get("kakao_account");
        Map<String,Object> profile = (Map<String, Object>)account.get("profile");

        return OAuthAttributes.builder()
                .name((String)profile.get("nickname"))
                .loginId((String)account.get("email"))
                .build();
    }

    private static OAuthAttributes ofGoogle(Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String)attributes.get("name"))
                .loginId((String)attributes.get("email"))
                .build();
    }

    public Member toEntity()
    {
        return Member.builder()
                .name(this.name)
                .loginId(this.loginId)
                .build();
    }
}
