package com.sku.elcoco.domain.post.dto;


import com.sku.elcoco.domain.file.entity.FileFormat;
import com.sku.elcoco.domain.post.entity.Post;
import com.sku.elcoco.global.common.PostCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class PostResponseDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class READ {
        private Long postId;

        private String memberEmail;

        private String memberNickname;

        private String title;

        private String content;

        private PostCategory postCategory;

        private int likeCount;

        private int viewCount;

        private int replyCount;

        private List<FileFormat> fileFormat;

        private boolean deleteAt;

        private LocalDateTime regDate;

        public READ(Post post) {
        }
    }

}
