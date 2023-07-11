package com.sku.elcoco.domain.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {//조회 시 사용

    private Long commentId;

    private Long postId;

    private String content;

    private String writer;

    private String deleteYn;

    private LocalDateTime createDate;

    private LocalDateTime modifiedDate;
}
