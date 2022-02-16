package com.bbookk.api;

import com.bbookk.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final MemberService memberService;

    @GetMapping("/user/checkId/{loginId}")
    public Map<String,Object> isDuplicateId(@PathVariable("loginId") String loginId)
    {
        Map<String,Object> res = new HashMap<>();
        res.put("isDuplicate", memberService.isDuplicateId(loginId));
        return res;
    }

}
