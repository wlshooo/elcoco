package com.sku.elcoco.domain.admin.service;


import com.sku.elcoco.domain.member.dto.MemberRequestDto;
import com.sku.elcoco.domain.member.dto.MemberResponseDto;
import com.sku.elcoco.domain.post.dto.PostResponseDto;
import com.sku.elcoco.domain.reply.dto.ReplyResponseDto;
import com.sku.elcoco.domain.report.entity.dto.ReportResponseDto;

import java.util.List;

public interface AdminService {
    //Member
    List<MemberResponseDto.adminREAD> getAllMember();

    Long updateMember(Long memberId, MemberRequestDto.UPDATE update);


    MemberResponseDto.adminREAD getMemberDetail(Long memberId);

    //Post
    List<PostResponseDto.READ> getAllMemberPost(Long memberId);

    PostResponseDto.READ getPostDetail(final Long postId);

    Long deletePost(final Long postId);

    //Reply

    List<ReplyResponseDto.adminREAD> getAllRepliesByMember(Long memberId);

    Long deleteReply(Long replyId);



    //Report
    List<ReportResponseDto.READ> getAllReports();

    ReportResponseDto.READ getReportDetail(Long reportId);

}
