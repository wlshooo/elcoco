package com.sku.elcoco.domain.admin.service;

import com.sku.elcoco.domain.member.dto.MemberRequestDto;
import com.sku.elcoco.domain.member.dto.MemberResponseDto;
import com.sku.elcoco.domain.member.entity.Member;
import com.sku.elcoco.domain.member.repository.MemberRepository;
import com.sku.elcoco.domain.post.dto.PostResponseDto;
import com.sku.elcoco.domain.post.entity.Post;
import com.sku.elcoco.domain.post.repository.PostRepository;
import com.sku.elcoco.domain.reply.dto.ReplyResponseDto;
import com.sku.elcoco.domain.reply.entity.Reply;
import com.sku.elcoco.domain.reply.repository.ReplyRepository;
import com.sku.elcoco.domain.report.entity.Report;
import com.sku.elcoco.domain.report.entity.dto.ReportResponseDto;
import com.sku.elcoco.domain.report.repository.ReportRepository;
import com.sku.elcoco.global.common.PostCategory;
import com.sku.elcoco.global.common.Role;
import com.sku.elcoco.global.exception.DuplicatedException;
import com.sku.elcoco.global.exception.NotFoundException;
import com.sku.elcoco.global.exception.WrongPasswordException;
import com.sku.elcoco.global.model.ResponseStatus;
import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final MemberRepository memberRepository;

    private final PostRepository postRepository;

    private final ReplyRepository replyRepository;

    private final ReportRepository reportRepository;

    private final PasswordEncoder passwordEncoder;



    @Override
    public List<MemberResponseDto.adminREAD> getAllMember() {
        List<Member> members = memberRepository.findAll();

        return members.stream()
                .map(this::toReadDto)
                .collect(Collectors.toList());
    }

    @Override
    public Long updateMember(Long memberId, MemberRequestDto.UPDATE update) {

        final Optional<Member> member = memberRepository.findMemberById(memberId);

        isMember(member);
        //isTelephone(update.getTelephone());
        //isNickname(update.getNickname());

        member.get().updateMember(toUpdateDto(update));
        memberRepository.save(member.get());
        return member.get().getId();
    }

    @Override
    public MemberResponseDto.adminREAD getMemberDetail(Long memberId) {
        final Optional<Member> member = memberRepository.findMemberById(memberId);

        isMember(member);

        return toReadDto(member.get());
    }

    @Override
    public List<PostResponseDto.READ> getAllMemberPost(Long memberId) {
        List<Post> posts = postRepository.findPostsByMemberId(memberId);

        return posts.stream().map(this::toReadDto)
                .collect(Collectors.toList());

    }

    @Override
    public PostResponseDto.READ getPostDetail(Long postId) {
        Optional<Post> post = postRepository.findPostById(postId);

        isPost(post);

        PostResponseDto.READ dto = toReadDto(post.get());

        return dto;
    }

    @Override
    public Long deletePost(Long postId) {
        Optional<Post> post = postRepository.findPostById(postId);

        isPost(post);

        post.get().changeDeleteAt();

        postRepository.save(post.get());

        return post.get().getId();
    }

    @Override
    public List<ReplyResponseDto.adminREAD> getAllRepliesByMember(Long memberId) {
        List<Reply> list = replyRepository.findRepliesByMemberId(memberId);

        return list.stream()
                .map(this::toReadDto)
                .collect(Collectors.toList());
    }

    @Override
    public Long deleteReply(Long replyId) {
        Optional<Reply> reply = replyRepository.findReplyById(replyId);

        isReply(reply);

        reply.get().changeDeleteAt();

        replyRepository.save(reply.get());

        return reply.get().getId();
    }


    @Override
    public List<ReportResponseDto.READ> getAllReports() {
        List<Report> reports = reportRepository.findAll();

        return reports.stream()
                .map(this::toReadDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReportResponseDto.READ getReportDetail(Long reportId) {
        Optional<Report> report = reportRepository.findReportById(reportId);

        isReport(report);

        return toReadDto(report.get());
    }



    private MemberResponseDto.adminREAD toReadDto(Member member) {
        return MemberResponseDto.adminREAD.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .password(member.getPassword())
                .nickname(member.getNickname())
                .role(String.valueOf(member.getRole()))
                .build();
    }

    private MemberRequestDto.UPDATE toUpdateDto(MemberRequestDto.UPDATE update) {
        MemberRequestDto.UPDATE encoding = MemberRequestDto.UPDATE.builder()
                .password(update.getPassword())
                .role(Role.of(update.getRole()).toString()) //이렇게 해도 되나..?
                .name(update.getName())
                .nickname(update.getNickname())
                .build();
        return encoding;
    }

    private PostResponseDto.READ toReadDto(Post post) {
            return PostResponseDto.READ.builder()
                    .postId(post.getId())
                    .memberEmail(post.getMember().getEmail())
                    .memberNickname(post.getMember().getNickname())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .deleteAt(post.isDeleteAt())
                    .postCategory(post.getPostCategory())
                    .viewCount(post.getViewCount())
                    .likeCount(post.getLikeCount())
                    .regDate(post.getRegDate())
                    .build();

    }

    private ReplyResponseDto.adminREAD toReadDto(Reply reply) {
        ReplyResponseDto.adminREAD dto = ReplyResponseDto.adminREAD.builder()
                .replyId(reply.getId())
                .memberNickname(reply.getMember().getNickname())
                .postId(reply.getPost().getId())
                .deleteAt(reply.isDeleteAt())
                .content(reply.getContent())
                .regDate(reply.getRegDate())
                .likeCount(reply.getLikeCount())
                .build();
        return dto;
    }

    private ReportResponseDto.READ toReadDto(Report report) {
        return ReportResponseDto.READ.builder()
                .reportId(report.getId())
                .postId(report.getPost().getId())
                .memberEmail(report.getMember().getEmail())
                .memberName(report.getMember().getName())
                .regDate(report.getRegDate())
                .reportCategory(String.valueOf(report.getReportCategory()))
                .postTitle(report.getPost().getTitle())
                .title(report.getTitle())
                .description(report.getDescription())
                .build();

    }


    private void isMember(Optional<Member> member) {
        if (member.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_MEMBER_NOT_FOUND);
        }
    }


    private void isNickname(String nickname) {
        if (memberRepository.existsMemberByNicknameAndDeleteAtFalse(nickname)) {
            throw new DuplicatedException(ResponseStatus.FAIL_MEMBER_NICKNAME_DUPLICATED);
        }
    }

    private void isEmail(String email) {
        if (memberRepository.existsMemberByEmailAndDeleteAtFalse(email)) {
            throw new DuplicatedException(ResponseStatus.FAIL_MEMBER_EMAIL_DUPLICATED);
        }
    }

    private void isPassword(String requestPassword, String getPassword) {
        if (!passwordEncoder.matches(requestPassword, getPassword)) {
            throw new WrongPasswordException(ResponseStatus.FAIL_MEMBER_PASSWORD_NOT_MATCHED);
        }
    }
    private void isReply(Optional<Reply> reply) {
        if (reply.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_REPLY_NOT_FOUND);
        }
    }

    private void isPost(Optional<Post> post) {
        if (post.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_POST_NOT_FOUND);
        }
    }

    private void isPostCategory(String category) {
        PostCategory.of(category);
    }


    private void isReport(Optional<Report> report) {
        if (report.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_REPORT_NOT_FOUND);
        }
    }
}
