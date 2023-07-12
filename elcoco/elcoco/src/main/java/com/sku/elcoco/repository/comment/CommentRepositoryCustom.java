package com.sku.elcoco.repository.comment;

import com.sku.elcoco.domain.comment.Comment;
import com.sku.elcoco.domain.comment.dto.CommentRequestDto;
import com.sku.elcoco.domain.comment.dto.CommentResponseDto;

import java.util.List;

public interface CommentRepositoryCustom {



    /**
     * 댓글 상세정보 조회
     * @param commentId - PK
     * @return 댓글 상세정보
     */
    CommentResponseDto findCommentById(Long commentId);

    /**
     * 댓글 수정
     * @param params - 댓글 정보
     */
    //void updateComment(CommentRequestDto params); //영속성컨테스트의 변동감지로 처리

    /**
     * 댓글 삭제
     * @param commentId - PK
     */
    //void deleteComment(Long commentId);       //deleteYn = Y 로 바꾸는 Comment 클래스의 commentDelete 메서드를 이용해 논리적으로 삭제
    /**
     * 댓글 리스트 조회
     * @param postId - 게시글 번호 (FK)
     * @return 댓글 리스트
     */
    List<CommentResponseDto> findAllCommentByPostId(Long postId);

    /**
     * 댓글 수 카운팅
     * @param postId - 게시글 번호 (FK)
     * @return 댓글 수
     */
    int count(Long postId);
}
