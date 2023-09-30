package com.sku.elcoco.domain.member.service;

import com.sku.elcoco.domain.member.dto.MemberRequestDto;
import com.sku.elcoco.domain.member.dto.MemberResponseDto;
import com.sku.elcoco.domain.member.entity.Member;
import com.sku.elcoco.domain.member.repository.MemberRepository;
import com.sku.elcoco.domain.member.repository.MemberSearchRepository;
import com.sku.elcoco.domain.skill.entity.Skill;
import com.sku.elcoco.domain.skill.repository.SkillRepository;
import com.sku.elcoco.global.auth.JwtProvider;
import com.sku.elcoco.global.auth.Token;
import com.sku.elcoco.global.auth.TokenDto;
import com.sku.elcoco.global.auth.TokenRepository;
import com.sku.elcoco.global.common.Gender;
import com.sku.elcoco.global.common.Role;
import com.sku.elcoco.global.exception.DuplicatedException;
import com.sku.elcoco.global.exception.InvalidRequestException;
import com.sku.elcoco.global.exception.NotFoundException;
import com.sku.elcoco.global.exception.WrongPasswordException;
import com.sku.elcoco.global.model.ResponseStatus;
import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final MemberSearchRepository memberSearchRepository;

    private final SkillRepository skillRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;

    private final TokenRepository tokenRepository;

    @Transactional
    @Override
    public void createMember(MemberRequestDto.CREATE create) {

        isEmail(create.getEmail());
        isNickname(create.getNickname());

        Member member = toEntity(create);

        memberRepository.save(member);
    }

    @Override
    public MemberResponseDto.READ loginMember(MemberRequestDto.LOGIN login) {
        Optional<Member> member = memberRepository.findMemberByEmailAndDeleteAtFalse(login.getEmail());

        isMember(member);

        isPassword(login.getPassword(), member.get().getPassword());

        TokenDto tokenDto = TokenDto.builder()
                .accessToken(jwtProvider.createToken(member.get().getEmail(), String.valueOf(member.get().getRole())))
                .refreshToken(createRefreshToken(member.get()))
                .build();

        return toReadTokenDto(member.get(), tokenDto);
    }

    @Override
    public MemberResponseDto.READ getMemberDetail(Long memberId) {

        final Optional<Member> member = memberRepository.findMemberByIdAndDeleteAtFalse(memberId);

        isMember(member);

        return toReadDto(member.get());
    }

    @Override
    public List<MemberResponseDto.READ> getAllMembers() {
        List<Member> members = memberRepository.findAllByDeleteAtFalse();

        return members.stream()
                .map(this::toReadDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MemberResponseDto.READ> getSearchMembers(MemberRequestDto.CONDITION condition) {
        final List<Member> members = memberSearchRepository.find(condition);

        return members.stream()
                .map(this::toReadDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void updateMember(String email, MemberRequestDto.UPDATE update) {
        final Optional<Member> member = memberRepository.findMemberByEmailAndDeleteAtFalse(email);

        isMember(member);
        isNickname(update.getNickname());
        member.get().updateMember(toUpdateDto(update));
        memberRepository.save(member.get());
    }



    @Transactional
    @Override
    public void deleteMember(Long id) {
        final Optional<Member> member = memberRepository.findMemberByIdAndDeleteAtFalse(id);

        isMember(member);

        // 논리적 삭제
        member.get().changeDeleteAt();

        memberRepository.save(member.get());
    }


    public String createRefreshToken(Member member) {
        Token token = tokenRepository.save(
                Token.builder()
                        .id(member.getId())
                        .refreshToken(UUID.randomUUID().toString())
                        .expiration(120)
                        .build()
        );
        return token.getRefreshToken();
    }

    public Token validRefreshToken(Member member, String refreshToken) {
        Optional<Token> token = tokenRepository.findById(member.getId());

        isToken(token);

        // redis 에 해당 유저의 토큰이 존재하는지 체크
        if (token.get().getRefreshToken() == null) {
            return null;
        } else {
            if (token.get().getExpiration() < 10) {
                token.get().setExpiration(1000);
                tokenRepository.save(token.get());
            }
            if (!token.get().getRefreshToken().equals(refreshToken)) {
                return null;
            } else {
                return token.get();
            }
        }
    }

    public TokenDto refreshAccessToken(TokenDto tokenDto) {
        String identity = jwtProvider.getIdentity(tokenDto.getAccessToken());
        log.info("identity = {}", identity);
        Optional<Member> member = memberRepository.findMemberByEmailAndDeleteAtFalse(identity);

        isMember(member);

        Token refreshToken = validRefreshToken(member.get(), tokenDto.getRefreshToken());

        isRefreshToken(refreshToken);

        return TokenDto.builder()
                .accessToken(jwtProvider.createToken(identity, String.valueOf(member.get().getRole())))
                .refreshToken(refreshToken.getRefreshToken())
                .build();

    }

    private void isMember(Optional<Member> member) {
        if (member.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_MEMBER_NOT_FOUND);
        }
    }


    private void isNickname(String nickname) {
        if (memberRepository.existsMemberByNicknameAndDeleteAtFalse(nickname)) {
            throw new DuplicatedException(ResponseStatus.FAIL_MEMBER_NICKNAME_DUPLICATED);
        }
    }

    private void isEmail(String email) {
        if (memberRepository.existsMemberByEmailAndDeleteAtFalse(email)) {
            throw new DuplicatedException(ResponseStatus.FAIL_MEMBER_EMAIL_DUPLICATED);
        }
    }

    private void isPassword(String requestPassword, String getPassword) {
        if (!passwordEncoder.matches(requestPassword, getPassword)) {
            throw new WrongPasswordException(ResponseStatus.FAIL_MEMBER_PASSWORD_NOT_MATCHED);
        }
    }

    private void isToken(Optional<Token> token) {
        if (token.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_TOKEN_NOT_FOUND);
        }
    }

    private void isRefreshToken(Token refreshToken) {
        if (refreshToken == null) {
            throw new InvalidRequestException(ResponseStatus.FAIL_LOGIN_NOT_SUCCESS);
        }
    }

    private MemberResponseDto.READ toReadTokenDto(Member member, TokenDto tokenDto) {
        return MemberResponseDto.READ.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .nickname(member.getNickname())
                .role(String.valueOf(member.getRole()))
                .accessToken(tokenDto.getAccessToken())
                .build();
    }

    private MemberResponseDto.READ toReadDto(Member member) {
        return MemberResponseDto.READ.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .nickname(member.getNickname())
                .build();
    }

    private MemberRequestDto.UPDATE toUpdateDto(MemberRequestDto.UPDATE update) {
        MemberRequestDto.UPDATE encoding = MemberRequestDto.UPDATE.builder()
                .password(passwordEncoder.encode(update.getPassword()))
                .role(update.getRole())
                .name(update.getName())
                .nickname(update.getNickname())
                .build();
        return encoding;
    }

    private Member toEntity(MemberRequestDto.CREATE create) {
        return Member.builder()
                .email(create.getEmail())
                .password(passwordEncoder.encode(create.getPassword()))
                .name(create.getName())
                .nickname(create.getNickname())
                .role(Role.ROLE_USER)
                .build();
    }

}
