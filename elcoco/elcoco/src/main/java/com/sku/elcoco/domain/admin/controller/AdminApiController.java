package com.sku.elcoco.domain.admin.controller;

import com.sku.elcoco.domain.admin.service.AdminService;
import com.sku.elcoco.domain.member.dto.MemberRequestDto;
import com.sku.elcoco.domain.member.dto.MemberResponseDto;
import com.sku.elcoco.domain.post.dto.PostResponseDto;
import com.sku.elcoco.domain.reply.dto.ReplyResponseDto;
import com.sku.elcoco.domain.report.entity.dto.ReportResponseDto;
import com.sku.elcoco.domain.skill.dto.SkillRequestDto;
import com.sku.elcoco.domain.skill.dto.SkillResponseDto;
import com.sku.elcoco.global.model.ResponseFormat;
import com.sku.elcoco.global.model.ResponseStatus;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminApiController {

    private final AdminService adminService;

    @GetMapping("/member")
    public ResponseFormat<List<MemberResponseDto.adminREAD>> getAllMember() {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.getAllMember());
    }

    @PutMapping("/member/{memberId}")
    public ResponseFormat<Long> updateMember(@PathVariable(name = "memberId") Long memberId, @RequestBody @Valid MemberRequestDto.UPDATE update) {

        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.updateMember(memberId, update));
    }

    @GetMapping("/member-detail/{memberId}")
    public ResponseFormat<MemberResponseDto.adminREAD> getMemberDetail(@PathVariable(name = "memberId") Long memberId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.getMemberDetail(memberId));
    }

    @GetMapping("/member-post/{memberId}")
    public ResponseFormat<List<PostResponseDto.READ>> getAllMemberPost(@PathVariable(name = "memberId") Long memberId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.getAllMemberPost(memberId));
    }

    @GetMapping("/post-detail/{postId}")
    public ResponseFormat<PostResponseDto.READ> getPostDetail(@PathVariable(name = "postId") Long postId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.getPostDetail(postId));
    }

    @DeleteMapping("/post/{postId}")
    public ResponseFormat<Long> deletePost(@PathVariable(name = "postId") Long postId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.deletePost(postId));
    }

    @GetMapping("/reply/{memberId}")
    public ResponseFormat<List<ReplyResponseDto.adminREAD>> getAllRepliesByMember(@PathVariable(name = "memberId") Long memberId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.getAllRepliesByMember(memberId));
    }

    @DeleteMapping("/reply/{replyId}")
    public ResponseFormat<Long> deleteReply(@PathVariable(name = "replyId") Long replyId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.deleteReply(replyId));
    }


    @GetMapping("/report")
    public ResponseFormat<List<ReportResponseDto.READ>> getAllReports() {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.getAllReports());
    }

    @GetMapping("/report-detail/{reportId}")
    public ResponseFormat<ReportResponseDto.READ> getReportDetail(@PathVariable(name = "reportId") Long reportId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.getReportDetail(reportId));
    }
}
