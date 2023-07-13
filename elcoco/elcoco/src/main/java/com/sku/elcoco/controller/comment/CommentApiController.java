package com.sku.elcoco.controller.comment;

import com.sku.elcoco.domain.comment.dto.CommentRequestDto;
import com.sku.elcoco.domain.comment.dto.CommentResponseDto;
import com.sku.elcoco.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentApiController {

    private final CommentService commentService;

    //댓글 생성
    @PostMapping("/board/{boardId}/comments")
    public CommentResponseDto saveComment(@PathVariable final Long boardId, @RequestBody final CommentRequestDto params) {
        Long commentId = commentService.saveComment(params);
        return commentService.findCommentById(commentId);
    }

    //댓글 리스트 조회
    @GetMapping("/board/{boardId}/comments")
    public List<CommentResponseDto> findAllComment(@PathVariable final Long boardId) {
        return commentService.findAllCommentByPostId(boardId);
    }

    //댓글 상세정보 조회
    @GetMapping("/board/{boardId}/comments/{commentId}")
    public CommentResponseDto findCommentById(@PathVariable final Long boardId, @PathVariable final Long commentId) {
        return commentService.findCommentById(commentId);
    }
    //댓글 수정
    @PatchMapping("/board/{boardId}/comments/{commentId}")
    public CommentResponseDto updateComment(@PathVariable final Long boardId,
                                            @PathVariable final Long commentId,@RequestBody final CommentRequestDto params) {
        commentService.updateComment(params);
        return commentService.findCommentById(commentId);
    }
    //댓글 삭제
    @DeleteMapping("/board/{boardId}/comments/{commentId}")
    public Long deleteComment(@PathVariable final Long boardId, @PathVariable final Long commentId) {
        Long deleteId = commentService.deleteComment(commentId);
        return deleteId;
    }


}
