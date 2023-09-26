package com.sku.elcoco.domain.post.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import com.sku.elcoco.domain.member.entity.QMember;
import com.sku.elcoco.domain.post.entity.Post;
import com.sku.elcoco.domain.post.entity.QPost;
import com.sku.elcoco.global.common.PostCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostSearchRepository {

    private final JPAQueryFactory queryFactory;

    private final QPost post = QPost.post;

    private final QMember member = QMember.member;

//    public List<Post> find(PostRequestDto.CONDITION condition) {
//        return queryFactory
//                .selectFrom(post)
//                .join(member).fetchJoin()
//                .where(
//                        DynamicQueryUtils.filter(condition.getPostIds(), post.id::in),
//                        DynamicQueryUtils.filter(condition.getTitle(), post.title::like),
//                        DynamicQueryUtils.filter(condition.getPostCategory(), post.postCategory::eq),
//                        DynamicQueryUtils.filter(condition.getWriterEmail(), post.member.email::eq),
//                        DynamicQueryUtils.filter(condition.getWriterName(), post.member.name::eq),
//                        DynamicQueryUtils.filter(condition.getWriterNickname(), post.member.nickname::eq),
//                        postDateBetween(condition.getFromPostDate(), condition.getToPostDate()),
//                        post.deleteAt.eq(Boolean.FALSE)
//                )
//                .fetch();
//    }

    public Page<Post> findWithPaging(Pageable pageable, String postCategory, String searchType, String searchText) {

        // 조건에 맞는 쿼리 구성
        JPAQuery<Post> query = queryFactory
                .selectFrom(post)
                .leftJoin(post.member, member)
                .where(
                        post.deleteAt.eq(Boolean.FALSE),
                        post.postCategory.eq(PostCategory.QNA)
                                .or(post.postCategory.eq(PostCategory.FREE))
                                .or(post.postCategory.eq(PostCategory.INFO)),
                        postCategoryEq(postCategory),
                        postSearchText(searchType, searchText)
                )
                .orderBy(post.regDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        QueryResults<Post> results = query.fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    public List<Post> findNoticePost() {
        return queryFactory
                .selectFrom(post)
                .join(member).fetchJoin()
                .where(
                        post.deleteAt.eq(Boolean.FALSE),
                        post.postCategory.eq(PostCategory.NOTICE)
                )
                .fetch();
    }



    private BooleanExpression postCategoryEq(String category) {
        if (!StringUtils.hasText(category)) {
            return null;
        }

        return post.postCategory.eq(PostCategory.valueOf(category));
    }

    private BooleanExpression postSearchText(String searchType, String searchText) {
        if (!StringUtils.hasText(searchText)) {
            return null;
        } else if (searchType.equals("title")) {
            return post.title.contains(searchText);
        } else if (searchType.equals("writer")) {
            return post.member.nickname.contains(searchText);
        } else {
            return post.title.contains(searchText).or(post.member.nickname.contains(searchText));
        }
    }

    private BooleanExpression postDateBetween(LocalDate fromPostDate, LocalDate toPostDate) {
        if (fromPostDate == null && toPostDate == null) {
            return null;
        }

        return post.regDate.between(fromPostDate.atStartOfDay(), toPostDate.atTime(LocalTime.MAX));

    }
}
