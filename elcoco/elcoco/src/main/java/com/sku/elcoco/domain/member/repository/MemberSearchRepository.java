package com.sku.elcoco.domain.member.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sku.elcoco.domain.member.dto.MemberRequestDto;
import com.sku.elcoco.domain.member.entity.Member;
import com.sku.elcoco.domain.member.entity.QMember;
import com.sku.elcoco.global.common.Gender;
import com.sku.elcoco.global.util.DynamicQueryUtils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberSearchRepository {

    private final JPAQueryFactory queryFactory;

    private final QMember member = QMember.member;

    public List<Member> find(MemberRequestDto.CONDITION condition) {
        return queryFactory
                .selectFrom(member)
                .where(
                        DynamicQueryUtils.filter(condition.getMemberIds(), member.id::in),
                        DynamicQueryUtils.filter(condition.getEmail(), member.email::eq),
                        DynamicQueryUtils.filter(condition.getName(), member.name::eq),
                        DynamicQueryUtils.filter(condition.getNickname(), member.nickname::eq),
                        DynamicQueryUtils.filter(condition.getTelephone(), member.telephone::eq),
                        memberBirthdayBetween(condition.getFromBirthDate(), condition.getToBirthDate()),
                        memberGenderEq(condition.getGender()),
                        member.deleteAt.eq(Boolean.FALSE)
                )
                .fetch();
    }

    private BooleanExpression memberBirthdayBetween(LocalDate fromBirthDate, LocalDate toBirthDate) {
        if (fromBirthDate == null && toBirthDate == null) {
            return null;
        }

        return member.birthDate.between(fromBirthDate, toBirthDate);
    }

    private BooleanExpression memberGenderEq(String gender) {
        if (!StringUtils.hasText(gender)) {
            return null;
        }

        return member.gender.eq(Gender.valueOf(gender));
    }

}
