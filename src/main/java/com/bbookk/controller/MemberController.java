package com.bbookk.controller;

import com.bbookk.auth.CustomUserDetails;
import com.bbookk.controller.form.ModifyForm;
import com.bbookk.entity.Book;
import com.bbookk.entity.Member;
import com.bbookk.repository.MemberRepository;
import com.bbookk.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityManager;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @GetMapping("/member/main")
    public String main()
    {
        return "member/main";
    }

    @GetMapping("/admin")
    public String admin()
    {
        return "member/admin";
    }

    @GetMapping("/member/myPage")
    public String myPage(@AuthenticationPrincipal CustomUserDetails userDetails, Model model)
    {
        //현재 멤버정보
        Member member = userDetails.getMember();
        Map<Long,Integer> cashInfo = new HashMap<>(); // 아이디,캐시잔액
        cashInfo.put(member.getId(),member.getCash());
        model.addAttribute("cashInfo",cashInfo);
        return "member/myPage";
    }

    /**
     * 캐시 충전
     */
    @GetMapping("/member/chargeCash")
    public String chargePage()
    {
        return "member/myPage/info/charge";
    }
    @PostMapping("/member/chargeCash")
    public String chargeCash(@AuthenticationPrincipal CustomUserDetails userDetails,
                             @RequestParam(value = "amount",required = false) int amount)
    {
        memberService.addCash(userDetails.getMember(),amount);
        return "redirect:/member/myPage";
    }

    /**
     * 개인정보 수정
     */
    @GetMapping("/member/modify")
    public String modifyPage(Model model)
    {
        model.addAttribute("modifyForm",new ModifyForm());
        return "member/myPage/info/modifyMemberForm";
    }

    @PostMapping("/member/modify")
    public String modify(@AuthenticationPrincipal CustomUserDetails userDetails,ModifyForm form)
    {
        memberService.modify(userDetails.getMember(),form);
        return "redirect:/logout";
    }

    /**
     * 회원탈퇴
     */
    @GetMapping("/member/drop")
    public String drop(@AuthenticationPrincipal CustomUserDetails userDetails)
    {
        Member principal = userDetails.getMember();
        Optional<Member> findMember = memberRepository.findById(principal.getId());
        findMember.ifPresent(memberRepository::delete);
        return "redirect:/logout";
    }

    /**
     * 도서 관리
     */
    @GetMapping("/member/registerBookPage")
    public String registerBook()
    {
        return "member/myPage/book/registerBookForm";
    }

    @PostMapping("/member/registerBook")
    public String registerBookInfo(@AuthenticationPrincipal CustomUserDetails userDetails,
                                   @RequestParam("img") String img,
                                   @RequestParam("title") String title,
                                   @RequestParam("author") String author,
                                   @RequestParam("publisher") String publisher,
                                   @RequestParam("isbn") String isbn)
    {
        Book book = new Book(img,title,author,publisher,isbn);
        Member userDetailsMember = userDetails.getMember();
        memberService.addBook(userDetailsMember,book);
        return "/member/myPage/book/registerBookForm";
    }
}
