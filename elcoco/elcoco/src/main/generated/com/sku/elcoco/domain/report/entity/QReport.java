package com.sku.elcoco.domain.report.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReport is a Querydsl query type for Report
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReport extends EntityPathBase<Report> {

    private static final long serialVersionUID = -547970434L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReport report = new QReport("report");

    public final com.sku.elcoco.global.common.QBaseEntity _super = new com.sku.elcoco.global.common.QBaseEntity(this);

    //inherited
    public final BooleanPath deleteAt = _super.deleteAt;

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.sku.elcoco.domain.member.entity.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDate = _super.modDate;

    public final com.sku.elcoco.domain.post.entity.QPost post;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final EnumPath<com.sku.elcoco.global.common.ReportCategory> reportCategory = createEnum("reportCategory", com.sku.elcoco.global.common.ReportCategory.class);

    public final StringPath title = createString("title");

    public QReport(String variable) {
        this(Report.class, forVariable(variable), INITS);
    }

    public QReport(Path<? extends Report> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReport(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReport(PathMetadata metadata, PathInits inits) {
        this(Report.class, metadata, inits);
    }

    public QReport(Class<? extends Report> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.sku.elcoco.domain.member.entity.QMember(forProperty("member")) : null;
        this.post = inits.isInitialized("post") ? new com.sku.elcoco.domain.post.entity.QPost(forProperty("post"), inits.get("post")) : null;
    }

}

