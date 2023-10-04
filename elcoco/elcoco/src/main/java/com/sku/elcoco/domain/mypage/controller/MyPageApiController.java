package com.sku.elcoco.domain.mypage.controller;

import com.sku.elcoco.domain.member.dto.MemberResponseDto;
import com.sku.elcoco.domain.mypage.service.MyPageService;
import com.sku.elcoco.domain.post.dto.PostResponseDto;
import com.sku.elcoco.domain.reply.dto.ReplyResponseDto;
import com.sku.elcoco.global.auth.CustomUserDetails;
import com.sku.elcoco.global.exception.UnauthorizedException;
import com.sku.elcoco.global.model.ResponseFormat;
import com.sku.elcoco.global.model.ResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mypage")
public class MyPageApiController {

    private final MyPageService myPageService;

    @GetMapping("/member")
    public ResponseFormat<MemberResponseDto.READ> getMemberDetail(@AuthenticationPrincipal CustomUserDetails memberEmail) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK,myPageService.getMemberDetail(memberEmail.getUsername()));
    }

    @GetMapping("/member/post")
    public ResponseFormat<List<PostResponseDto.READ>> getMemberPost(@AuthenticationPrincipal CustomUserDetails memberEmail) {
        if (memberEmail.getUsername() == null) {
            throw new UnauthorizedException(ResponseStatus.FAIL_TOKEN_NOT_FOUND);
        }
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK,myPageService.getMemberPosts(memberEmail.getUsername()));
    }

    @GetMapping("/member/reply")
    public ResponseFormat<List<ReplyResponseDto.READ>> getMemberReply(@AuthenticationPrincipal CustomUserDetails memberEmail) {
        if (memberEmail.getUsername() == null) {
            throw new UnauthorizedException(ResponseStatus.FAIL_TOKEN_NOT_FOUND);
        }
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK,myPageService.getMemberReplies(memberEmail.getUsername()));
    }

    @PostMapping("member/check-nickname")
    public ResponseFormat<Boolean> checkNickname(@RequestBody String nickname) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK,myPageService.checkDuplicateNickname(nickname));
    }

    @PutMapping("member/nickname")
    public ResponseFormat<Void> updateNickname(@AuthenticationPrincipal CustomUserDetails memberEmail, @RequestBody String nickname) {
        myPageService.updateNickname(memberEmail.getUsername(),nickname);
        return ResponseFormat.success(ResponseStatus.SUCCESS_OK);
    }
}
