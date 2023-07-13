package com.sku.elcoco;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sku.elcoco.domain.board.Board;
import com.sku.elcoco.domain.board.QBoard;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class ElcocoApplicationTests {

	@Autowired
	EntityManager em;
	@Test
	void contextLoads() {
		Board params = Board.builder()
				.title("1번 게시글 제목")
				.content("1번 게시글 내용")
				.writer("이진호")
				.hits(0)
				.deleteYn('N')
				.build();
		em.persist(params);
		JPAQueryFactory query = new JPAQueryFactory(em);
		QBoard qBoard = QBoard.board;

		Board result = query
				.selectFrom(qBoard)
				.fetchOne();

		assertThat(result).isEqualTo(params);
//lombok 동작 확인 (hello.getId())
		assertThat(result.getId()).isEqualTo(params.getId());
	}

}
