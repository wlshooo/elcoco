package com.sku.elcoco.service.comment;

import com.sku.elcoco.domain.board.Board;
import com.sku.elcoco.domain.comment.Comment;
import com.sku.elcoco.domain.comment.dto.CommentRequestDto;
import com.sku.elcoco.domain.comment.dto.CommentResponseDto;
import com.sku.elcoco.repository.board.BoardRepository;
import com.sku.elcoco.repository.comment.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    private final BoardRepository boardRepository;
    /**
     * 댓글 저장
     * @param params
     * @return commentId -PK
     */
    @Transactional
    public Long saveComment(final CommentRequestDto params) {
        Optional<Board> findBoard = boardRepository.findById(params.getBoardId());
        Board board = findBoard.get();
        Comment comment = params.toEntity();
        comment.addCommentInBoard(board);
        commentRepository.save(comment);
        return comment.getId();
    }

    /**
     * 댓글 상세 정보
     * @param commentId
     * @return CommentResponseDto result
     */
    public CommentResponseDto findCommentById(final Long commentId) {
        CommentResponseDto result = commentRepository.findCommentById(commentId);
        return result;
    }

    /**
     * 댓글 수정
     * @param params
     * @return comment PK
     */
    @Transactional
    public Long updateComment(final CommentRequestDto params) {
        Optional<Comment> findComment = commentRepository.findById(params.getCommentId());
        Comment comment = findComment.get();
        comment.updateComment(params.getContent(), params.getContent());
        return comment.getId();
    }

    /**
     * 댓글 삭제
     * @param commentId
     * @return  comment PK
     */
    @Transactional
    public Long deleteComment(final Long commentId) {
        Optional<Comment> oComment = commentRepository.findById(commentId);
        Comment comment = oComment.get();
        comment.deleteComment();
        return comment.getId();
    }

    /**
     * 댓글 리스트 조회
     * @param postId
     * @return postId에 해당하는 게시글에 등록된 댓글 리스트
     */
    public List<CommentResponseDto> findAllCommentByPostId(final Long postId) {
        List<CommentResponseDto> result = commentRepository.findAllCommentByPostId(postId);
        return result;
    }

}
