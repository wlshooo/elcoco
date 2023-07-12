package com.sku.elcoco.config;

import com.sku.elcoco.domain.board.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Profile("local")
@Component
@RequiredArgsConstructor
public class InitBoard {

    private final InitBoardService initBoardService;

    @PostConstruct
    public void init() {
        initBoardService.init();
    }
    @Component
    static class InitBoardService {
        @PersistenceContext
        EntityManager em;
        @Transactional
        public void init() {

            for (int i = 1; i <= 100; i++) {
                Board params = Board.builder()
                        .title(i+"번 게시글 제목")
                        .content(i+"번 게시글 내용")
                        .writer("write"+i)
                        .hits(0)
                        .deleteYn('N')
                        .build();
                em.persist(params);
            }
        }
    }
}