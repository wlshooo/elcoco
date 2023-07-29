package com.sku.elcoco.domain.message.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MessageRequestDto {
    @Getter
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class CREATE {
        private String title;

        private String content;

        private String senderName;

        private String receiverName;
    }

    @Getter
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class READ {

        private String nickName;
    }
}
