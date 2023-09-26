package com.sku.elcoco.global.auth;

import com.sku.elcoco.domain.member.entity.Member;
import com.sku.elcoco.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findMemberByEmailAndDeleteAtFalse(username).orElseThrow(
                () -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다.")
        );
        return new CustomUserDetails(member);
    }
}
