package com.sku.elcoco.repository.member;

import com.sku.elcoco.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    Optional<Member> findMemberByNickname(String nickname);
}