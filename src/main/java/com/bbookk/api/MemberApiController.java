package com.bbookk.api;

import com.bbookk.auth.CustomUserDetails;
import com.bbookk.repository.MemberRepository;
import com.bbookk.repository.dto.BookDetailsDto;
import com.bbookk.repository.dto.FindBooksDto;
import com.bbookk.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final MemberRepository memberRepository;

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

    @GetMapping("/v1/find/books")
    public Page<FindBooksDto> findBooks(@AuthenticationPrincipal CustomUserDetails userDetails,
                                        @RequestParam("query") String query,
                                        Pageable pageable)
    {
        return memberRepository.findBooks(userDetails.getMember().getAddress().getGu(),query,pageable);
    }

    @GetMapping("/v1/details/book")
    public BookDetailsDto bookDetails(@RequestParam("memberId") Long id,
                                      @RequestParam("bookName") String bookName)
    {
        return memberRepository.getBookDetails(id,bookName);
    }


//    @GetMapping("/v1/borrow/book")
//    public Map<String,Boolean> rentRequest(@AuthenticationPrincipal CustomUserDetails userDetails,
//                                           @RequestParam("bookName")String bookName,
//                                           @RequestParam("lenderName")String lenderName)
//    {
//        Long borrowerId = userDetails.getMember().getId();
//
//    }

}
