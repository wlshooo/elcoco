package com.sku.elcoco.domain.comment.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponseDto {//조회 시 사용

    private Long commentId;

    private Long boardId;

    private String content;

    private String writer;

    private char deleteYn;

    private LocalDateTime createDate;

    private LocalDateTime modifiedDate;

    @QueryProjection
    public CommentResponseDto(Long commentId, Long boardId, String content, String writer,
                              char deleteYn, LocalDateTime createDate, LocalDateTime modifiedDate) {
        this.commentId = commentId;
        this.boardId = boardId;
        this.content = content;
        this.writer = writer;
        this.deleteYn = deleteYn;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
    }
}
