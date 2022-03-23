package com.bbookk.api;

import com.bbookk.auth.CustomUserDetails;
import com.bbookk.entity.Book;
import com.bbookk.entity.Member;
import com.bbookk.repository.BookRepositoryImpl;
import com.bbookk.repository.OrderRepository;
import com.bbookk.service.MemberService;
import com.bbookk.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.NoHandlerFoundException;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    //Service
    private final OrderService orderService;

    //Repo
    private final MemberService memberService;
    private final BookRepositoryImpl bookRepository;

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

    @PostMapping("/v1/delete/book")
    public ResponseEntity<Boolean> deleteBook(@AuthenticationPrincipal CustomUserDetails userDetails,
                               @RequestParam("bookName") String bookName)
    {
        boolean body = memberService.deleteBook(userDetails.getMember().getId(), bookName);
        return ResponseEntity.ok().body(body);
    }

    @GetMapping("/v1/borrow/book")
    public ResponseEntity<Boolean> rentRequest(@AuthenticationPrincipal CustomUserDetails userDetails,
                                      @RequestParam("lenderId") Long id,
                                      @RequestParam("bookName")String bookName)
    {
        Member curMember = userDetails.getMember();
        //캐시가 부족할경우 false
        boolean response = memberService.subCashBorrowRequest(curMember.getId());
        if(response)
        {
            Long borrowerId = curMember.getId();
            Book findBook = bookRepository.findMemberBook(id, bookName);
            memberService.createOrder(borrowerId,findBook);
        }
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/v1/accept/order")
    public ResponseEntity<Boolean> acceptOrder(@AuthenticationPrincipal CustomUserDetails userDetails,
                                               @RequestParam("orderId") Long orderId)
    {
        orderService.acceptRequest(orderId);
        Long memberId = userDetails.getMember().getId();
        orderService.setMember(orderId,memberId);
        memberService.addCashLendBook(memberId);
        return ResponseEntity.ok().body(true);
    }

    @PostMapping("/v1/delete/order")
    public ResponseEntity<Boolean> deleteOrder(@RequestParam("orderId") Long orderId)
    {
        orderService.delete(orderId);
        return ResponseEntity.ok().body(true);
    }

}
