package com.sku.elcoco.repository.board;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sku.elcoco.domain.board.Board;
import com.sku.elcoco.domain.board.QBoard;
import com.sku.elcoco.domain.board.dto.BoardResponseDto;
import com.sku.elcoco.domain.board.dto.QBoardResponseDto;
import com.sku.elcoco.paging.CommonParams;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import java.util.List;

import static com.sku.elcoco.domain.board.QBoard.*;

public class BoardRepositoryImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public BoardRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public int count(CommonParams params) {

        long result = queryFactory
                .select(board.count())
                .from(board)
                .where(board.deleteYn.eq('N'),CommonSearchCondition(params))
                .fetchOne();
        return (int) result;

    }

    @Override
    public List<BoardResponseDto> findAllByBoard(CommonParams params) {
        return queryFactory
                .select(new QBoardResponseDto(
                        board.id,
                        board.title,
                        board.content,
                        board.writer,
                        board.hits,
                        board.deleteYn,
                        board.createdDate,
                        board.modifiedDate))
                .from(board)
                .where(board.deleteYn.eq('N'),CommonSearchCondition(params))
                .limit(params.getRecordPerPage())  //limit는 한 화면에 보여줄 데이터의 개수입니다.
                .offset(params.getPagination().getLimitStart()) //offset은 0부터 시작하며, 몇 번째 row에서 데이터 조회를 시작할지 정한다고 하였습니다.
                .orderBy(board.id.desc(),board.createdDate.desc())
                .fetch();
    }

    private BooleanExpression CommonSearchCondition(CommonParams params) {
        String keyword = params.getKeyword();
        String searchType = params.getSearchType();
        if (keyword != null && keyword.equals("") == false) {
            if (searchType == null || searchType.equals("")) {
                return board.title.contains(keyword).or(board.content.contains(keyword)).or(board.writer.contains(keyword));
            }
            else if (searchType.equals("title")) {
                return board.title.contains(keyword);
            } else if (searchType.equals("content")) {
                return board.content.contains(keyword);
            } else if (searchType.equals("writer")) {
                return board.writer.contains(keyword);
            }
        }
        return null;
    }
}

