package com.sku.elcoco.domain.comment;

import com.sku.elcoco.domain.board.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    private String content;

    private String writer;

    private char deleteYn;

    private LocalDateTime createDate = LocalDateTime.now();

    private LocalDateTime modifiedDate;


    @Builder

    public Comment(Board board, String content, String writer,
                   char deleteYn, LocalDateTime createDate, LocalDateTime modifiedDate) {
        this.board = board;
        this.content = content;
        this.writer = writer;
        this.deleteYn = deleteYn;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
    }
}
