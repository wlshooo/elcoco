package com.sku.elcoco.domain.skill.repository;

import com.sku.elcoco.domain.skill.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

    // skillId 값으로 skill 단일 조회 + 삭제되지 않은 skill
    Optional<Skill> findSkillByIdAndDeleteAtFalse(Long id);

    // skill 다중 조회 + 삭제되지 않은 skill
    List<Skill> findAllByDeleteAtFalse();

    // skillName 과 동일한 skill 다중 조회 + 삭제되지 않은 skill
    List<Skill> findAllByNameInAndDeleteAtFalse(List<String> skillNames);

    // skillName 검증 + 삭제되지 않은 skill
    boolean existsSkillByNameAndDeleteAtFalse(String name);

    boolean existsSkillByName(String name);

    Optional<Skill> findSkillById(Long skillId);
}
