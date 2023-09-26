package com.sku.elcoco.domain.skill.controller;

import com.sku.elcoco.domain.skill.dto.SkillRequestDto;
import com.sku.elcoco.domain.skill.dto.SkillResponseDto;
import com.sku.elcoco.domain.skill.service.SkillService;
import com.sku.elcoco.global.model.ResponseFormat;
import jakarta.validation.Valid;
import com.sku.elcoco.global.model.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SkillApiController {

    private final SkillService skillService;

    /*
    TODO : 관리자만이 접근이 가능하도록 해줘야 한다.
     */

    /**
     * Create Skill API
     *
     * @param create
     * @return ResponseStatus.SUCCESS_CREATE + Void
     */
    @PostMapping("/skill")
    public ResponseFormat<Void> createSkill(@RequestBody @Valid SkillRequestDto.CREATE create) {
        skillService.createSkill(create);
        return ResponseFormat.success(ResponseStatus.SUCCESS_CREATE);
    }

    /**
     * Update Skill API
     *
     * @param update
     * @return ResponseStatus.SUCCESS_OK + Void
     */
    @PutMapping("/skill")
    public ResponseFormat<Void> updateSkill(@RequestBody @Valid SkillRequestDto.UPDATE update) {
        skillService.updateSkill(update);
        return ResponseFormat.success(ResponseStatus.SUCCESS_OK);
    }

    /**
     * Delete Skill API
     *
     * @param skillId
     * @return ResponseStatus.SUCCESS_OK + Void
     */
    @DeleteMapping("/skill/{skillId}")
    public ResponseFormat<Void> deleteSkill(@PathVariable(name = "skillId") Long skillId) {
        skillService.deleteSkill(skillId);
        return ResponseFormat.success(ResponseStatus.SUCCESS_OK);
    }

    /**
     * Read Skill API - 모든 skill 다중 조회
     *
     * @return ResponseStatus.SUCCESS_OK + List<SkillResponseDto.READ>
     */
    @GetMapping("/skill")
    public ResponseFormat<List<SkillResponseDto.READ>> getAllSkills() {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, skillService.getAllSkills());
    }

}
