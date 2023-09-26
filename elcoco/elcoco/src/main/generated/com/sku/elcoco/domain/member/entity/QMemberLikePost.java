package com.sku.elcoco.domain.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberLikePost is a Querydsl query type for MemberLikePost
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberLikePost extends EntityPathBase<MemberLikePost> {

    private static final long serialVersionUID = 1174386113L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberLikePost memberLikePost = new QMemberLikePost("memberLikePost");

    public final com.sku.elcoco.global.common.QBaseEntity _super = new com.sku.elcoco.global.common.QBaseEntity(this);

    //inherited
    public final BooleanPath deleteAt = _super.deleteAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDate = _super.modDate;

    public final com.sku.elcoco.domain.post.entity.QPost post;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public QMemberLikePost(String variable) {
        this(MemberLikePost.class, forVariable(variable), INITS);
    }

    public QMemberLikePost(Path<? extends MemberLikePost> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberLikePost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberLikePost(PathMetadata metadata, PathInits inits) {
        this(MemberLikePost.class, metadata, inits);
    }

    public QMemberLikePost(Class<? extends MemberLikePost> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
        this.post = inits.isInitialized("post") ? new com.sku.elcoco.domain.post.entity.QPost(forProperty("post"), inits.get("post")) : null;
    }

}

