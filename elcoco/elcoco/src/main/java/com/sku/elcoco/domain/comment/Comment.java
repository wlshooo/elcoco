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
    public Comment(Board board, String content, String writer, char deleteYn) {
        this.id = this.getId();
        this.board = board;
        this.content = content;
        this.writer = writer;
        this.deleteYn = deleteYn;
    }


    public void updateComment(String content, String writer) {
        this.content = content;
        this.writer = writer;
    }
    public void deleteComment() {
        this.deleteYn = 'Y';
    }

    public void addCommentInBoard(Board board) {    //연관관계 편의 메서드
        this.board = board;
        board.getComments().add(this);
    }


}
