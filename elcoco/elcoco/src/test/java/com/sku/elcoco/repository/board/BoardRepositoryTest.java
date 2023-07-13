package com.sku.elcoco.repository.board;

import com.sku.elcoco.domain.board.Board;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    EntityManager em;

    @Test
    void save() {
        Board params = Board.builder()
                .title("1번 게시글 제목")
                .content("1번 게시글 내용")
                .writer("이진호")
                .hits(0)
                .deleteYn('N')
                .build();

        boardRepository.save(params);

        Board findBoard = boardRepository.findById(params.getId()).get();
        assertThat(findBoard).isEqualTo(params);

        List<Board> result1 = boardRepository.findAll();
        assertThat(result1).containsExactly(params);



    }

    @Test
    void findAll() {

        // 1. 전체 게시글 수 조회
        long boardsCount = boardRepository.count();
        assertThat(boardsCount).isEqualTo(1);

        // 2. 전체 게시글 리스트 조회
        List<Board> boards = boardRepository.findAll();
        assertThat(boards.size()).isEqualTo(1);
    }

    @Test
    void delete() {

        // 1. 게시글 조회
        Board entity = boardRepository.findById((long) 8).get();

        // 2. 게시글 삭제
        boardRepository.delete(entity);
    }
}