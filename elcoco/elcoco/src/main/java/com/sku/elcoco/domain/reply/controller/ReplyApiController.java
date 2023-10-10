package com.sku.elcoco.domain.reply.controller;

import com.sku.elcoco.domain.reply.dto.ReplyRequestDto;
import com.sku.elcoco.domain.reply.dto.ReplyResponseDto;
import com.sku.elcoco.domain.reply.service.ReplyService;
import com.sku.elcoco.global.auth.CustomUserDetails;
import com.sku.elcoco.global.model.ResponseFormat;
import jakarta.validation.Valid;
import com.sku.elcoco.global.model.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ReplyApiController {

    private final ReplyService replyService;

    /**
     * 댓글 생성
     *
     * @param postId 게시글 PK
     * @param create ReplyRequestDto.Create
     * @return replyId
     */
    @PostMapping("/reply/{postId}")
    public ResponseFormat<Long> createReply(@AuthenticationPrincipal CustomUserDetails userDetails,
                                            @PathVariable Long postId,
                                            @RequestBody @Valid ReplyRequestDto.CREATE create) {
        Long replyId = replyService.createReply(userDetails.getUsername(), postId, create);
        return ResponseFormat.successWithData(com.sku.elcoco.global.model.ResponseStatus.SUCCESS_CREATE, replyId);
    }
//    @PostMapping("/reply/{postId}")
//    public Long createReply(@AuthenticationPrincipal CustomUserDetails userDetails,
//                                            @PathVariable Long postId,
//                                            @RequestBody @Valid ReplyRequestDto.CREATE create) {
//        return replyService.createReply(userDetails.getUsername(), postId, create);
//    }

//    /**
//     * 댓글 수정
//     *
//     * @param @PathVariable postId
//     * @param @PathVariable replyId
//     * @param update        ReplyRequestDto.UPDATE
//     * @return updateReplyId
//     */
//    @PatchMapping("/reply/{postId}/{replyId}")
//    public ResponseFormat<Long> updateReply(@AuthenticationPrincipal CustomUserDetails userDetails,
//                                            @PathVariable Long postId,
//                                            @PathVariable Long replyId,
//                                            @RequestBody @Valid ReplyRequestDto.UPDATE update) {
//        Long updateReplyId = replyService.updateReply(userDetails.getUsername(), replyId, update);
//        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, updateReplyId);
//    }

    /**
     * 댓글 삭제
     *
     * @param @PathVariable postId
     * @param @PathVariable replyId
     * @return deleteReplyId
     */
    @DeleteMapping("/reply/{postId}/{replyId}")
    public ResponseFormat<Long> deleteReply(@AuthenticationPrincipal CustomUserDetails userDetails,
                                            @PathVariable Long postId,
                                            @PathVariable Long replyId) {
        Long deleteReplyId = replyService.deleteReply(userDetails.getUsername(), postId, replyId);
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, deleteReplyId);
    }

    /**
     * 게시글 번호로 모든 댓글 조회
     *
     * @param postId 게시글 PK
     * @return List<ReplyResponseDto.READ>
     */
    @GetMapping("/reply/{postId}")
    public ResponseFormat<List<ReplyResponseDto.READ>> getReplies(@PathVariable Long postId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, replyService.getAllReplies(postId));
    }
//    @GetMapping("/reply/{postId}")
//    public List<ReplyResponseDto.READ> getReplies(@PathVariable Long postId) {
//        return  replyService.getAllReplies(postId);
//    }

    /**
     * Search Reply API - 검색 조건에 맞는 Reply 다중 조회
     *
     * @param condition
     * @return ResponseStatus.SUCCESS_OK + List<ReplyResponseDto.READ>
     */
    @GetMapping("/reply/search")
    public ResponseFormat<List<ReplyResponseDto.READ>> getSearchReplies(@RequestBody @Valid ReplyRequestDto.CONDITION condition) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, replyService.getSearchReplies(condition));
    }

    /**
     * reply 와 member 사이의 좋아요 연관테이블에 좋아요 정보를 넘겨주기 위한 API
     *
     * @param replyId
     * @param loginNickname
     * @return
     */
    @PostMapping("/reply/like/{replyId}/{loginNickname}")
    public ResponseFormat<ReplyResponseDto.READ> addReplyLike(@PathVariable Long replyId,
                                                              @PathVariable String loginNickname) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, replyService.addRepliesLikeByLoginNickname(replyId, loginNickname));
    }


    /** Member 가 좋아요 누른 댓글들을 조회를 위한 API **/
    @PostMapping("/replies/like/member/{memberNickname}")
    public ResponseFormat<List<ReplyResponseDto.READ>> getLikeRepliesByMemberNickname(@PathVariable String memberNickname) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, replyService.getLikeRepliesByMemberNickname(memberNickname));
    }

}
