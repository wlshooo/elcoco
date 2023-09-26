package com.sku.elcoco.global.common;

import com.sku.elcoco.global.exception.NotFoundException;
import com.sku.elcoco.global.model.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum PostCategory {

    NOTICE("공지사항"),

    QNA("질문"),

    INFO("정보글"),

    FREE("자유게시판");

    String postCategory;

    public static PostCategory of(String postCategory) {
        return Arrays.stream(PostCategory.values())
                .filter(type -> type.toString().equalsIgnoreCase(postCategory))
                .findAny().orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_POST_CATEGORY_NOT_FOUND));
    }

}
