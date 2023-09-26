package com.sku.elcoco.domain.report.service;

import com.sku.elcoco.domain.member.entity.Member;
import com.sku.elcoco.domain.member.repository.MemberRepository;
import com.sku.elcoco.domain.post.entity.Post;
import com.sku.elcoco.domain.post.repository.PostRepository;
import com.sku.elcoco.domain.report.entity.Report;
import com.sku.elcoco.domain.report.entity.dto.ReportRequestDto;
import com.sku.elcoco.domain.report.entity.dto.ReportResponseDto;
import com.sku.elcoco.domain.report.repository.ReportRepository;
import com.sku.elcoco.domain.report.repository.ReportSearchRepository;
import com.sku.elcoco.global.common.ReportCategory;
import com.sku.elcoco.global.exception.DuplicatedException;
import com.sku.elcoco.global.exception.NotFoundException;
import com.sku.elcoco.global.model.ResponseStatus;
import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    private final MemberRepository memberRepository;

    private final PostRepository postRepository;

    private final ReportSearchRepository reportSearchRepository;

    @Override
    @Transactional
    public void createReport(String email, Long postId, ReportRequestDto.CREATE create) {
        isReportCategory(create.getReportCategory());

        Optional<Member> findMember = memberRepository.findMemberByEmailAndDeleteAtFalse(email);
        isMember(findMember);

        Optional<Post> findPost = postRepository.findPostByIdAndDeleteAtFalse(postId);
        isPost(findPost);

        isReportDuplicated(findPost.get().getId(), findMember.get().getId());

        reportRepository.save(toEntity(create, findMember.get(), findPost.get()));
    }

    @Override
    public List<ReportResponseDto.READ> getAllReportsByPost(Long postId) {
        List<Report> reports = reportRepository.findReportsByPostIdAndDeleteAtFalse(postId);

        return reports.stream()
                .map(this::toReadDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReportResponseDto.READ getReportDetail(Long reportId) {
        Optional<Report> report = reportRepository.findReportByIdAndDeleteAtFalse(reportId);

        isReport(report);

        return toReadDto(report.get());
    }

    @Override
    public List<ReportResponseDto.READ> getAllReports() {
        List<Report> reports = reportRepository.findReportsByDeleteAtFalse();

        return reports.stream()
                .map(this::toReadDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReportResponseDto.READ> getSearchReports(ReportRequestDto.CONDITION condition) {
        List<Report> reports = reportSearchRepository.find(condition);

        return reports.stream()
                .map(this::toReadDto)
                .collect(Collectors.toList());
    }


    private void isMember(Optional<Member> member) {
        if (member.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_MEMBER_NOT_FOUND);
        }
    }

    private void isPost(Optional<Post> post) {
        if (post.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_POST_NOT_FOUND);
        }
    }

    private void isReport(Optional<Report> report) {
        if (report.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_REPORT_NOT_FOUND);
        }
    }

    private void isReportDuplicated(Long postId, Long memberId) {
        if (reportRepository.existsReportByPost_IdAndMember_IdAndDeleteAtFalse(postId, memberId)) {
            throw new DuplicatedException(ResponseStatus.FAIL_REPORT_DUPLICATED);
        }
    }

    private void isReportCategory(String reportCategory) {
        ReportCategory.of(reportCategory);
    }

    private Report toEntity(ReportRequestDto.CREATE create, Member reporter, Post reportPost) {
        return Report.builder()
                .member(reporter)
                .post(reportPost)
                .reportCategory(ReportCategory.valueOf(create.getReportCategory()))
                .title(create.getTitle())
                .description(create.getDescription())
                .build();
    }

    private ReportResponseDto.READ toReadDto(Report report) {
        return ReportResponseDto.READ.builder()
                .reportId(report.getId())
                .postId(report.getPost().getId())
                .memberEmail(report.getMember().getEmail())
                .memberName(report.getMember().getName())
                .reportCategory(String.valueOf(report.getReportCategory()))
                .postTitle(report.getPost().getTitle())
                .title(report.getTitle())
                .description(report.getDescription())
                .build();

    }
}
