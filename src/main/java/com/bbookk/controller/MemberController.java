package com.bbookk.controller;

import com.bbookk.auth.CustomUserDetails;
import com.bbookk.controller.dto.MypageDto;
import com.bbookk.controller.form.ModifyForm;
import com.bbookk.entity.Book;
import com.bbookk.entity.Member;
import com.bbookk.repository.BookRepository;
import com.bbookk.repository.MemberRepository;
import com.bbookk.repository.dto.BorrowBooksDto;
import com.bbookk.repository.dto.BorrowDetailsDto;
import com.bbookk.repository.dto.LibraryDto;
import com.bbookk.repository.dto.MyBookDetailsDto;
import com.bbookk.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    /**
     * admin 페이지
     */
//    @GetMapping("/admin")
//    public String admin()
//    {
//        return "admin";
//    }

    @GetMapping("/profile")
    public String myPage(@AuthenticationPrincipal CustomUserDetails userDetails, Model model)
    {
        //현재 멤버정보
        Member member = userDetails.getMember();
        MypageDto res = new MypageDto(member.getName(),member.getCash());
        model.addAttribute("res",res);
        return "profile";
    }

    // 충전기능 삭제
//    /**
//     * 캐시 충전
//     */
//    @GetMapping("/member/chargeCash")
//    public String chargePage()
//    {
//        return "member/myPage/info/charge";
//    }
//    @PostMapping("/member/chargeCash")
//    public String chargeCash(@AuthenticationPrincipal CustomUserDetails userDetails,
//                             @RequestParam(value = "amount",required = false) int amount)
//    {
//        memberService.addCash(userDetails.getMember(),amount);
//        return "redirect:/member/myPage";
//    }

    /**
     * 개인정보 수정
     */
    @GetMapping("/modify")
    public String modifyPage(Model model)
    {
        model.addAttribute("modifyForm",new ModifyForm());
        return "modify";
    }

    @PostMapping("/modify")
    public String modify(@AuthenticationPrincipal CustomUserDetails userDetails,ModifyForm form)
    {
        memberService.modify(userDetails.getMember(),form);
        return "redirect:/logout";
    }

    /**
     * 회원탈퇴
     */
    @GetMapping("/drop")
    public String drop(@AuthenticationPrincipal CustomUserDetails userDetails)
    {
        memberService.drop(userDetails.getMember());
        return "redirect:/logout";
    }

    /**
     * 도서 관리
     */
    //도서등록
    @GetMapping("/register/book")
    public String registerBook()
    {
        return "registerBook";
    }

    @PostMapping("/register/book")
    public String registerBookInfo(@AuthenticationPrincipal CustomUserDetails userDetails,
                                   @RequestParam("img") String img,
                                   @RequestParam("title") String title,
                                   @RequestParam("author") String author,
                                   @RequestParam("publisher") String publisher,
                                   @RequestParam("isbn") String isbn)
    {
        Book book = new Book(img,title,author,publisher,isbn);
        memberService.addBook(userDetails.getMember().getId(),book);
        return "registerBook";
    }
    //나의 서재
    @GetMapping("/library")
    public String myLibrary(@AuthenticationPrincipal CustomUserDetails userDetails,
                            @PageableDefault(size = 5)Pageable pageable,
                            Model model)
    {
        Page<LibraryDto> library = memberService.getLibrary(userDetails.getMember(),pageable);
        int startPage = Math.max(1,library.getPageable().getPageNumber()+1);
        int endPage = Math.min(library.getTotalPages(),startPage+4);
        model.addAttribute("start",startPage);
        model.addAttribute("end",endPage);
        model.addAttribute("library",library);
        return "/library";
    }

    @PostMapping("/delete/book")
    public String deleteBook(@AuthenticationPrincipal CustomUserDetails userDetails,
                             @RequestParam("bookName") String bookName)
    {
        memberService.deleteBook(userDetails.getMember().getId(), bookName);
        return "/library";
    }

    @GetMapping("/library/details/{bookName}")
    public String libraryDetails(@AuthenticationPrincipal CustomUserDetails userDetails,
                              @PathVariable String bookName,
                              Model model)
    {

        Long memberId = userDetails.getMember().getId();
        Book findBook = bookRepository.findMemberBook(memberId, bookName);
        MyBookDetailsDto dto = new MyBookDetailsDto(findBook.getImgSource(),findBook.getBookName());
        String status = findBook.getStatus() ? "대여가능" : "대여중";
        dto.setStatus(status);
        model.addAttribute("details",dto);
        return "myBookDetails";
    }

    //대여하기
    @GetMapping("/borrow")
    public String borrowPage(@AuthenticationPrincipal CustomUserDetails userDetails,
                             @RequestParam(value = "query",defaultValue = "")String query,
                             @PageableDefault(size=5) Pageable pageable,
                             Model model)
    {
        Page<BorrowBooksDto> list = bookRepository.findBooks(userDetails.getMember().getAddress().getGu(),
                query, pageable);
        int startPage = Math.max(1,list.getPageable().getPageNumber()+1);
        int endPage = Math.min(list.getTotalPages(),startPage+4);
        model.addAttribute("start",startPage);
        model.addAttribute("end",endPage);
        model.addAttribute("list",list);
        return "/borrow";
    }

    @GetMapping("/borrow/details")
    public String borrowDetails(@RequestParam("lenderId") Long id,
                                @RequestParam("bookName") String bookName,
                                Model model)
    {
        Book findBook = bookRepository.findMemberBook(id, bookName);
        Optional<Member> findMember = memberRepository.findById(id);
        BorrowDetailsDto dto = new BorrowDetailsDto(findBook.getImgSource(),
                findBook.getBookName(),findMember.get().getName());
        String status = findBook.getStatus() ? "대여가능" : "대여중";
        dto.setStatus(status);
        model.addAttribute("details",dto);
        return "/borrow/details";
    }
}
