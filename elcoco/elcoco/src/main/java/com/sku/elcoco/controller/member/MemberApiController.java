package com.sku.elcoco.controller.member;

import com.sku.elcoco.domain.member.dto.MemberLoginRequestDto;
import com.sku.elcoco.domain.member.dto.MemberRequestDto;
import com.sku.elcoco.service.member.MemberService;
import com.sku.elcoco.config.jwt.TokenInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberApiController {
    private final MemberService memberService;


    @PostMapping("/signin")
    public Long signIn(@RequestBody MemberRequestDto memberRequestDto) {
        log.info("memberId = {}",memberRequestDto.getMemberId());
        Long memberId = memberService.signIn(memberRequestDto);
        return memberId;
    }

    @PostMapping("/login")
    public TokenInfo login(@RequestBody MemberLoginRequestDto memberLoginRequestDto) {
        String memberId = memberLoginRequestDto.getEmail();
        String password = memberLoginRequestDto.getPassword();
        TokenInfo tokenInfo = memberService.login(memberId, password);
        return tokenInfo;
    }

    @PostMapping("/test")
    public String test() {
        return "success";
    }

    //이메일 인증번호 발송
    @PostMapping("/emails/verification-requests")
    public ResponseEntity sendMessage(@RequestParam("email") String email) {
        memberService.sendCodeToEmail(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //이메일 인증번호 검증
    @GetMapping("/emails/verification")
    public ResponseEntity verificationEmail(@RequestParam("email") String email,
                                      @RequestParam("code") String authCode) {
        Boolean response = memberService.verifiedCode(email, authCode);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}