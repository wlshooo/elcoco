package com.sku.elcoco.domain.member.repository;

import com.sku.elcoco.domain.member.entity.MemberLikeReply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberLikeReplyRepository extends JpaRepository<MemberLikeReply,Long> {
    boolean existsMemberLikeReplyByReply_IdAndMember_IdAndDeleteAtFalse(Long replyId, Long memberId);
}
