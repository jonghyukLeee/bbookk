package com.bbookk.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class MemberApiController {

    @GetMapping("/member/searchBook")
    public String searchBook(@RequestParam("query") String query)
    {
        Mono<String> mono = WebClient.builder()
                .baseUrl("https://dapi.kakao.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build().get()
                .uri(builder -> builder.path("/v3/search/book")
                        .queryParam("query", query)
                        .build())
                .header("Authorization", "KakaoAK "+"de56b657ef46f3b99834428dc7d579df")
                .exchangeToMono(response -> {
                    return response.bodyToMono(String.class);
                });
        return mono.block();
    }
}
