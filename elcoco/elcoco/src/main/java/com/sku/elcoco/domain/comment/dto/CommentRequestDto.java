package com.sku.elcoco.domain.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDto {//생성과 수정에 사용
    private Long commentId;

    private Long postId;

    private String writer;

    private String content;
}
