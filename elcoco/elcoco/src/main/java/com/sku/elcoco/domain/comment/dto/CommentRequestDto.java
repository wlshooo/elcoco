package com.sku.elcoco.domain.comment.dto;

import com.sku.elcoco.domain.board.Board;
import com.sku.elcoco.domain.comment.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDto {//생성과 수정에 사용
    private Long commentId;

    private Long boardId;

    private String writer;

    private String content;

    private char deleteYn;


    public Comment toEntity() {
        return Comment.builder()
                .content(content)
                .writer(writer)
                .deleteYn(deleteYn)
                .build();
    }
}
