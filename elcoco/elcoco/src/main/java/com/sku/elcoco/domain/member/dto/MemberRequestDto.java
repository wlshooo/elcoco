package com.sku.elcoco.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class MemberRequestDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CREATE {

        private String email;

        private String password;

        private String name;

        private String nickname;

        private String role;

    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UPDATE {


        private String password;

        private String name;

        private String nickname;

        private String role;

    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CONDITION {

        private List<Long> memberIds;

        private String email;

        private String name;

        private String nickname;

    }


    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LOGIN {

        private String email;

        private String password;
    }
}
