package com.sku.elcoco.config.jwt;

import lombok.Data;

@Data
public class MemberLoginRequestDto {
    private String memberId;
    private String password;
}