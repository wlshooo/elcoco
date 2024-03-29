package com.sku.elcoco.domain.mypage.service;

import com.sku.elcoco.domain.member.dto.MemberRequestDto;
import com.sku.elcoco.domain.member.dto.MemberResponseDto;
import com.sku.elcoco.domain.post.dto.PostResponseDto;
import com.sku.elcoco.domain.reply.dto.ReplyResponseDto;
import jakarta.transaction.Transactional;

import java.util.List;

public interface MyPageService {

    MemberResponseDto.READ getMemberDetail(String memberEmail);

    List<PostResponseDto.READ> getMemberPosts(String memberEmail);

    List<ReplyResponseDto.READ> getMemberReplies(String memberEmail);

    boolean checkDuplicateNickname(String nickname);

    void updateNickname(String memberEmail,String nickname);

    void updatePassword(String memberEmail,String password);

}
