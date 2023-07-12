package com.sku.elcoco.domain.board;

import com.sku.elcoco.domain.comment.Comment;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Entity
public class Board {
    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK

    private String title; // 제목

    private String content; // 내용

    private String writer; // 작성자

    @OneToMany(mappedBy = "board")
    private List<Comment> comments = new ArrayList<>();
    private int hits; // 조회 수

    private char deleteYn; // 삭제 여부

    private LocalDateTime createdDate = LocalDateTime.now(); // 생성일

    private LocalDateTime modifiedDate; // 수정일

    @Builder
    public Board(String title, String content, String writer, int hits, char deleteYn) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.hits = hits;
        this.deleteYn = deleteYn;
    }

    public void update(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.modifiedDate = LocalDateTime.now();
    }
    /**
     * 조회 수 증가
     */
    public void increaseHits() {
        this.hits++;
    }

    /**
     * 게시글 삭제
     */
    public void delete() {
        this.deleteYn = 'Y';
    }
}
