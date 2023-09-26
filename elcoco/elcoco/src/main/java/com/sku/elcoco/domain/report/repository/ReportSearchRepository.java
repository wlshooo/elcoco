package com.sku.elcoco.domain.report.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import com.sku.elcoco.domain.member.entity.QMember;
import com.sku.elcoco.domain.post.entity.QPost;
import com.sku.elcoco.domain.report.entity.QReport;
import com.sku.elcoco.domain.report.entity.Report;
import com.sku.elcoco.domain.report.entity.dto.ReportRequestDto;
import com.sku.elcoco.global.common.ReportCategory;
import com.sku.elcoco.global.util.DynamicQueryUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReportSearchRepository {

    private final JPAQueryFactory queryFactory;

    private final QReport report = QReport.report;

    private final QMember member = QMember.member;

    private final QPost post = QPost.post;

    public List<Report> find(ReportRequestDto.CONDITION condition) {
        return queryFactory
                .selectFrom(report)
                .join(post).fetchJoin()
                .join(member).fetchJoin()
                .where(
                        DynamicQueryUtils.filter(condition.getReportIds(), report.id::in),
                        DynamicQueryUtils.filter(condition.getReportPostId(), report.post.id::eq),
                        DynamicQueryUtils.filter(condition.getReportPostTitle(), report.post.title::like),
                        DynamicQueryUtils.filter(condition.getReportMemberEmail(), report.member.email::eq),
                        DynamicQueryUtils.filter(condition.getReportMemberName(), report.member.name::eq),
                        DynamicQueryUtils.filter(condition.getReportMemberNickname(), report.member.nickname::eq),
                        DynamicQueryUtils.filter(condition.getTitle(), report.title::like),
                        reportDateBetween(condition.getFromReportDate(), condition.getToReportDate()),
                        reportCategoryEq(condition.getReportCategory()),
                        report.deleteAt.eq(Boolean.FALSE)
                )
                .fetch();
    }

    private BooleanExpression reportDateBetween(LocalDate fromReportDate, LocalDate toReportDate) {
        if (fromReportDate == null && toReportDate == null) {
            return null;
        }

        return report.regDate.between(fromReportDate.atStartOfDay(), toReportDate.atStartOfDay());
    }

    private BooleanExpression reportCategoryEq(String reportCategory) {
        if (!StringUtils.hasText(reportCategory)) {
            return null;
        }

        return report.reportCategory.eq(ReportCategory.valueOf(reportCategory));
    }
}
