package com.sku.elcoco.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequestDto {
    private Long memberId;

    private String email;

    private String password;

    private String nickname;
}
