package com.sku.elcoco.domain.member.entity;

import com.sku.elcoco.domain.member.dto.MemberRequestDto;
import com.sku.elcoco.global.common.BaseEntity;
import com.sku.elcoco.global.common.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "email", unique = true)
    @NotNull
    private String email;

    @Column(name = "password")
    @NotNull
    private String password;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "nickname", unique = true)
    @NotNull
    private String nickname;

    @Column(name = "member_role")
    @Enumerated(EnumType.STRING)
    private Role role;


    @Column(name = "refresh_token")
    private String refreshToken;


    @Builder
    public Member(String email,
                  String password,
                  String name,
                  String nickname,
                  Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.role = role;
    }

    // modify 위한 메소드
    public void updateMember(MemberRequestDto.UPDATE update) {
        this.password = update.getPassword();
        this.name = update.getName();
        this.nickname = update.getNickname();
        this.role = Role.valueOf(update.getRole());
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
