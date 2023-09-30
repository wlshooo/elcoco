package com.sku.elcoco.domain.member.repository;

import com.sku.elcoco.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    // memberId 값으로 member 단일 조회 + 삭제되지 않은 member
    Optional<Member> findMemberByIdAndDeleteAtFalse(Long id);

    // member 다중 조회 + 삭제되지 않은 member
    List<Member> findAllByDeleteAtFalse();

    // email 값으로 member 단일 조회 + 삭제되지 않은 member
    Optional<Member> findMemberByEmailAndDeleteAtFalse(String email);


    // nickname 값으로 member 단일 조회 + 삭제되지 않은 member
    Optional<Member> findMemberByNicknameAndDeleteAtFalse(String nickname);

    // email 검증 + 삭제되지 않은 member
    boolean existsMemberByEmailAndDeleteAtFalse(String email);

    // nickname 검증 + 삭제되지 않은 member
    boolean existsMemberByNicknameAndDeleteAtFalse(String nickname);

    Optional<Member> findMemberById(Long memberId);

    Optional<Member> findMemberByEmail(String email);
}
