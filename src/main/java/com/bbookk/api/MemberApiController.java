package com.bbookk.api;

import com.bbookk.auth.CustomUserDetails;
import com.bbookk.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @Value("${apis.kakao.restApiKey}") String key;
    @GetMapping("/v1/search/book")
    public String searchBook(@RequestParam("query") String query)
    {
        Mono<String> mono = WebClient.builder()
                .baseUrl("https://dapi.kakao.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build().get()
                .uri(builder -> builder.path("/v3/search/book")
                        .queryParam("query", query)
                        .build())
                .header("Authorization", "KakaoAK "+key)
                .exchangeToMono(response -> {
                    return response.bodyToMono(String.class);
                });
        return mono.block();
    }

    @GetMapping("/v1/check/book")
    public Map<String,Boolean> checkBook(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam("bookName") String bookName)
    {
        Map<String,Boolean> res = new HashMap<>();
        res.put("response",memberService.isDuplicatedBook(userDetails.getMember().getId(), bookName));
        return res;
    }

}
