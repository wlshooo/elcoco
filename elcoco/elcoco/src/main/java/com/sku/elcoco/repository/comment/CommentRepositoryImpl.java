package com.sku.elcoco.repository.comment;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sku.elcoco.domain.board.dto.QBoardResponseDto;
import com.sku.elcoco.domain.comment.QComment;
import com.sku.elcoco.domain.comment.dto.CommentRequestDto;
import com.sku.elcoco.domain.comment.dto.CommentResponseDto;
import com.sku.elcoco.domain.comment.dto.QCommentResponseDto;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static com.sku.elcoco.domain.comment.QComment.*;

public class CommentRepositoryImpl implements CommentRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    public CommentRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }


    @Override
    public CommentResponseDto findCommentById(Long commentId) {
        CommentResponseDto result = jpaQueryFactory
                .select(new QCommentResponseDto(
                        comment.id,
                        comment.board.id,
                        comment.content,
                        comment.writer,
                        comment.deleteYn,
                        comment.createDate,
                        comment.modifiedDate))
                .from(comment)
                .where(comment.id.eq(commentId))
                .fetchOne();

        return result;
    }


    @Override
    public List<CommentResponseDto> findAllCommentByPostId(Long postId) {
        List<CommentResponseDto> result = jpaQueryFactory
                .select(Projections.fields(CommentResponseDto.class,
                        comment.id, comment.board.id, comment.content, comment.writer, comment.deleteYn,
                        comment.createDate, comment.modifiedDate))
                .from(comment)
                .where(comment.board.id.eq(postId))
                .fetch();

        return result;
    }

    @Override
    public int count(Long postId) {
        return 0;
    }
}
