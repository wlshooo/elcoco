package com.sku.elcoco.domain.reply.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class ReplyResponseDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class READ {
        private Long replyId;

        private String memberNickname;

        private Long postId;

        private String content;

        private int likeCount;

        private LocalDateTime regDate;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class adminREAD {
        private Long replyId;

        private String memberNickname;

        private Long postId;

        private String content;

        private int likeCount;

        private boolean deleteAt;

        private LocalDateTime regDate;
    }

}
