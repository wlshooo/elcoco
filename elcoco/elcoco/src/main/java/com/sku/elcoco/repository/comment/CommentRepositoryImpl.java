package com.sku.elcoco.repository.comment;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sku.elcoco.domain.comment.QComment;
import com.sku.elcoco.domain.comment.dto.CommentRequestDto;
import com.sku.elcoco.domain.comment.dto.CommentResponseDto;

import javax.persistence.EntityManager;
import java.util.List;

import static com.sku.elcoco.domain.comment.QComment.*;

public class CommentRepositoryImpl implements CommentRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    public CommentRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public void save(CommentRequestDto params) {

    }

    @Override
    public CommentResponseDto findCommentById(Long commentId) {
        return null;
    }

    @Override
    public void updateComment(CommentRequestDto params) {

    }

    @Override
    public void deleteComment(Long commentId) {

    }

    @Override
    public List<CommentResponseDto> findAllCommentByPostId(Long postId) {
        return null;
    }

    @Override
    public int count(Long postId) {
        return 0;
    }
}
