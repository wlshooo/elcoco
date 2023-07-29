package com.sku.elcoco.domain.board.dto;

import com.sku.elcoco.domain.board.Board;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardRequestDto {

    private String title;
    private String content;
    private String writer;
    private char deleteYn;

    public Board toEntity() {
        return Board.builder()
                .title(title)
                .content(content)
                .writer(writer)
                .hits(0)    //변경 해야함
                .deleteYn(deleteYn)
                .build();
    }
}
