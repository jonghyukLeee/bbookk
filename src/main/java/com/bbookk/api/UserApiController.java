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
        return ResponseEntity.ok().body(memberService.isDuplicatedLoginId(loginId));
    }

    @GetMapping("/user/v1/nickname")
    public ResponseEntity<Boolean> isDuplicateNickname(@RequestParam String nickname)
    {
        return ResponseEntity.ok().body(memberService.isDuplicatedNickname(nickname));
    }

}
