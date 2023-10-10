package com.sku.elcoco.domain.reply.service;



import com.sku.elcoco.domain.reply.dto.ReplyRequestDto;
import com.sku.elcoco.domain.reply.dto.ReplyResponseDto;

import java.util.List;

public interface ReplyService {

    /**
     * 댓글을 생성.
     *
     * @param email   댓글을 생성할 회원의 이메일
     * @param postId  댓글이 속한 포스트의 ID
     * @param create  생성할 댓글 정보를 담고 있는 DTO
     * @return 생성된 댓글의 ID
     */
    Long createReply(String email, Long postId, ReplyRequestDto.CREATE create);

    /**
     * 특정 포스트에 대한 모든 댓글 목록을 조회.
     *
     * @param postId 조회할 포스트의 ID
     * @return 모든 댓글 목록을 담은 DTO 리스트 (List<ReplyResponseDto.READ>)
     */
    List<ReplyResponseDto.READ> getAllReplies(Long postId);

    /**
     * 조건에 맞는 댓글 목록을 검색.
     *
     * @param condition 검색 조건을 담고 있는 DTO
     * @return 조건에 맞는 댓글 목록을 담은 DTO 리스트 (List<ReplyResponseDto.READ>)
     */
    List<ReplyResponseDto.READ> getSearchReplies(ReplyRequestDto.CONDITION condition);

    /**
     * 댓글을 삭제.
     *
     * @param email   댓글을 삭제할 회원의 이메일
     * @param postId  댓글이 속한 포스트의 ID
     * @param replyId 삭제할 댓글의 ID
     * @return 삭제된 댓글의 ID
     */
    Long deleteReply(String email, Long postId, Long replyId);

    /**
     * 댓글 좋아요 버튼 클릭 시, 연관테이블에 추가해주기 위한 서비스
     *
     * @param replyId
     * @param loginNickname
     * @return
     */
    ReplyResponseDto.READ addRepliesLikeByLoginNickname(Long replyId, String loginNickname);

    /**
     * memberNickname 으로 member가 좋아요 누른 게시물 조회하는 서비스
     *
     * @param memberNickname
     * @return
     */
    List<ReplyResponseDto.READ> getLikeRepliesByMemberNickname (String memberNickname);

}
