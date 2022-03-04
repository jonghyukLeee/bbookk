package com.bbookk.api;

import com.bbookk.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final MemberService memberService;

    /**
     * 중복체크
     */

    @GetMapping("/user/v1/loginId")
    public ResponseEntity<Boolean> isDuplicateLoginId(@RequestParam String loginId)
    {
        System.out.println("loginId = "+loginId);
        return ResponseEntity.ok().body(memberService.isDuplicatedLoginId(loginId));
    }

    @GetMapping("/user/v1/nickname")
    public Map<String,Boolean> isDuplicateNickname(@RequestParam String nickname)
    {
        System.out.println("nickName = " + nickname);
        Map<String,Boolean> res = new HashMap<>();
        res.put("isDuplicate", memberService.isDuplicatedNickname(nickname));
        return res;
    }

}
