package com.sku.elcoco.domain.member.controller;

import com.sku.elcoco.domain.member.dto.MemberRequestDto;
import com.sku.elcoco.domain.member.dto.MemberResponseDto;
import com.sku.elcoco.domain.member.service.MemberService;
import com.sku.elcoco.global.auth.CustomUserDetails;
import com.sku.elcoco.global.auth.TokenDto;
import com.sku.elcoco.global.model.ResponseFormat;
import jakarta.validation.Valid;
import com.sku.elcoco.global.model.ResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberApiController {

    private final MemberService memberService;

    /**
     * Create Member API
     *
     * @param create
     * @return ResponseStatus.SUCCESS_CREATE + Void
     */
    @PostMapping("/member")
    public ResponseFormat<Void> createMember(@RequestBody @Valid MemberRequestDto.CREATE create) {
        memberService.createMember(create);
        return ResponseFormat.success(ResponseStatus.SUCCESS_CREATE);
    }

    /**
     * @param login
     * @return ResponseStatus.SUCCESS_OK + MemberResponseDto.READ
     */
    @PostMapping("/login")
    public ResponseFormat<MemberResponseDto.READ> loginMember(@RequestBody @Valid MemberRequestDto.LOGIN login) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, memberService.loginMember(login));
    }

    /**
     * Update Member API
     *
     * @param update
     * @return ResponseStatus.SUCCESS_NO_CONTENT + Void
     */
    @PutMapping("/member")
    public ResponseFormat<Void> updateMember(@AuthenticationPrincipal CustomUserDetails userDetails,
                                             @RequestBody @Valid MemberRequestDto.UPDATE update) {
        memberService.updateMember(userDetails.getUsername(), update);
        return ResponseFormat.success(ResponseStatus.SUCCESS_NO_CONTENT);
    }

    /**
     * Delete Member API
     *
     * @param memberId
     * @return ResponseStatus.SUCCESS_NO_CONTENT + Void
     */
    @DeleteMapping("/member/{memberId}")
    public ResponseFormat<Void> deleteMember(@PathVariable(name = "memberId") Long memberId) {
        memberService.deleteMember(memberId);
        return ResponseFormat.success(ResponseStatus.SUCCESS_NO_CONTENT);
    }

    /**
     * Read Member API - memberId 값으로 단일 조회
     *
     * @param memberId
     * @return ResponseStatus.SUCCESS_OK + MemberResponseDto.READ
     */
    @GetMapping("/member/{memberId}")
    public ResponseFormat<MemberResponseDto.READ> getMemberDetail(@PathVariable(name = "memberId") Long memberId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, memberService.getMemberDetail(memberId));
    }

    /**
     * Read Member API - 모든 member 다중 조회
     *
     * @return ResponseStatus.SUCCESS_OK + List<MemberResponseDto.READ>
     */
    @GetMapping("/member")
    public ResponseFormat<List<MemberResponseDto.READ>> getAllMembers() {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, memberService.getAllMembers());
    }

    /**
     * Search Member API - 검색 조건에 맞는 member 다중 조회
     *
     * @param condition
     * @return ResponseStatus.SUCCESS_OK + List<MemberResponseDto.READ>
     */
    @GetMapping("/member/search")
    public ResponseFormat<List<MemberResponseDto.READ>> getSearchMembers(@RequestBody @Valid MemberRequestDto.CONDITION condition) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, memberService.getSearchMembers(condition));
    }

    /**
     * Refresh
     *
     * @Param tokenDto
     * @Return
     */
    @GetMapping("/refresh")
    public ResponseFormat<TokenDto> refresh(@RequestBody @Valid TokenDto tokenDto) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, memberService.refreshAccessToken(tokenDto));
    }

    //이메일 인증번호 발송
    @PostMapping("/emails/verification-requests")
    public ResponseFormat<Void> sendMessage(@RequestParam("email") String email) {
        memberService.sendCodeToEmail(email);
        return ResponseFormat.success(ResponseStatus.SUCCESS_OK);
    }

    //이메일 인증번호 검증
    @GetMapping("/emails/verification")
    public ResponseFormat<Void> verificationEmail(@RequestParam("email") String email,
                                                  @RequestParam("code") String authCode) {
        Boolean response = memberService.verifiedCode(email, authCode);
        log.info("response = {}", response);
        return ResponseFormat.success(ResponseStatus.SUCCESS_OK);
    }

}
