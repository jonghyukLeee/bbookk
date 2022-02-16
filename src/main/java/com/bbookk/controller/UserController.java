package com.bbookk.controller;

import com.bbookk.controller.form.MemberForm;
import com.bbookk.entity.Address;
import com.bbookk.entity.Member;
import com.bbookk.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final MemberService memberService;
    private final BCryptPasswordEncoder encoder;

    @GetMapping("/")
    public String home()
    {
        return "member/main";
    }
    @GetMapping("/user/loginPage")
    public String loginPage()
    {
        return "user/loginPage";
    }
    @GetMapping("/user/joinMember")
    public String createForm(Model model)
    {
        model.addAttribute("memberForm",new MemberForm());
        return "user/createMemberForm";
    }

    @PostMapping("/user/joinMember")
    public String joinUser(@Valid MemberForm form)
    {
        Address address = new Address(form.getSi(),form.getGu(),form.getDong());
        String encodedPwd = encoder.encode(form.getPassword());

        Member member = new Member(form.getName(),form.getLoginId(),encodedPwd,form.getPhone(),address);
        memberService.join(member);

        return "redirect:/";
    }

}
