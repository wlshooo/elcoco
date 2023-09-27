package com.sku.elcoco.dummy;


import com.sku.elcoco.domain.skill.dto.SkillRequestDto;
import com.sku.elcoco.domain.skill.service.SkillService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DummyDataTest {

    @Autowired
    SkillService skillService;

    @Test
    void createSkill() {
        SkillRequestDto.CREATE create1 = SkillRequestDto.CREATE.builder()
                .name("Java")
                .build();

        SkillRequestDto.CREATE create2 = SkillRequestDto.CREATE.builder()
                .name("Python")
                .build();

        SkillRequestDto.CREATE create3 = SkillRequestDto.CREATE.builder()
                .name("JavaScript")
                .build();

        SkillRequestDto.CREATE create4 = SkillRequestDto.CREATE.builder()
                .name("SpringBoot")
                .build();

        SkillRequestDto.CREATE create5 = SkillRequestDto.CREATE.builder()
                .name("React")
                .build();

        SkillRequestDto.CREATE create6 = SkillRequestDto.CREATE.builder()
                .name("C++")
                .build();

        SkillRequestDto.CREATE create7 = SkillRequestDto.CREATE.builder()
                .name("C#")
                .build();

        SkillRequestDto.CREATE create8 = SkillRequestDto.CREATE.builder()
                .name("Flutter")
                .build();

        SkillRequestDto.CREATE create9 = SkillRequestDto.CREATE.builder()
                .name("Spring")
                .build();

        SkillRequestDto.CREATE create10 = SkillRequestDto.CREATE.builder()
                .name("Node.js")
                .build();

        skillService.createSkill(create1);
        skillService.createSkill(create2);
        skillService.createSkill(create3);
        skillService.createSkill(create4);
        skillService.createSkill(create5);
        skillService.createSkill(create6);
        skillService.createSkill(create7);
        skillService.createSkill(create8);
        skillService.createSkill(create9);
        skillService.createSkill(create10);
    }
}
