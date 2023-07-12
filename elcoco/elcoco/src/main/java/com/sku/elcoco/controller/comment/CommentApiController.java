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

    @PostMapping("/board/{boardId}/comments")
    public CommentResponseDto saveComment(@PathVariable final Long boardId, @RequestBody final CommentRequestDto params) {
        Long commentId = commentService.saveComment(params);
        return commentService.findCommentById(commentId);
    }

    @GetMapping("/board/{boardId}/comments")
    public List<CommentResponseDto> findAllComment(@PathVariable final Long boardId) {
        return commentService.findAllCommentByPostId(boardId);
    }


}
