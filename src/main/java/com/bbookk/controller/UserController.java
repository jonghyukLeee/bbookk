package com.bbookk.controller;

import com.bbookk.auth.CustomUserDetails;
import com.bbookk.controller.form.MemberForm;
import com.bbookk.entity.Address;
import com.bbookk.entity.BooksOfMonth;
import com.bbookk.entity.Member;
import com.bbookk.repository.BooksOfMonthRepository;
import com.bbookk.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final MemberService memberService;
    private final BooksOfMonthRepository bomRepository;
    private final BCryptPasswordEncoder encoder;

    @GetMapping("/")
    public String home(Model model)
    {
        List<BooksOfMonth> list = bomRepository.getBooksOfMonth();
        model.addAttribute("list",list);
        return "index";
    }

    @GetMapping("/loginForm")
    public String login()
    {
        return "loginForm";
    }

    @GetMapping("/user/join")
    public String createForm(Model model)
    {
        model.addAttribute("memberForm",new MemberForm());
        return "/register";
    }

    @PostMapping("/user/join")
    public String joinUser(@Valid MemberForm form)
    {
        Address address = new Address(form.getSi(),form.getGu(),form.getDong());
        String encodedPwd = encoder.encode(form.getPassword());

        Member member = new Member(form.getName(),form.getLoginId(),encodedPwd,form.getPhone(),address);
        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/test")
    public String test()
    {
        return "test";
    }

}
