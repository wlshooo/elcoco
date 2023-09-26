package com.sku.elcoco.domain.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -1037131062L;

    public static final QMember member = new QMember("member1");

    public final com.sku.elcoco.global.common.QBaseEntity _super = new com.sku.elcoco.global.common.QBaseEntity(this);

    public final StringPath address = createString("address");

    public final DatePath<java.time.LocalDate> birthDate = createDate("birthDate", java.time.LocalDate.class);

    //inherited
    public final BooleanPath deleteAt = _super.deleteAt;

    public final StringPath email = createString("email");

    public final EnumPath<com.sku.elcoco.global.common.Gender> gender = createEnum("gender", com.sku.elcoco.global.common.Gender.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath major = createString("major");

    public final ListPath<MemberSkill, QMemberSkill> memberSkills = this.<MemberSkill, QMemberSkill>createList("memberSkills", MemberSkill.class, QMemberSkill.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDate = _super.modDate;

    public final StringPath name = createString("name");

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final StringPath refreshToken = createString("refreshToken");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final EnumPath<com.sku.elcoco.global.common.Role> role = createEnum("role", com.sku.elcoco.global.common.Role.class);

    public final StringPath telephone = createString("telephone");

    public final StringPath univName = createString("univName");

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

