package ru.nikzarch.monsterservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.nikzarch.monsterservice.domain.Monster;
import ru.nikzarch.monsterservice.service.MonsterService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/monsters")
@RequiredArgsConstructor
public class MonsterController {

    private final MonsterService monsterService;

    /** Просмотр бестиария */
    @GetMapping
    public List<Monster> getAll() {
        return monsterService.getAll();
    }

    /** Получить конкретного монстра */
    @GetMapping("/{id}")
    public Monster getById(@PathVariable Long id) {
        return monsterService.getById(id);
    }

    /** Добавление монстра (ведьмак) */
    @PostMapping
    public Monster create(@RequestBody Monster monster) {
        return monsterService.createMonster(monster);
    }

    /** Редактирование монстра (ведьмак) */
    @PutMapping("/{id}")
    public Monster update(
            @PathVariable Long id,
            @RequestBody Monster monster
    ) {
        return monsterService.updateMonster(id, monster);
    }

    /** Автоматический расчёт награды */
    @GetMapping("/{id}/recommended-reward")
    public int getRecommendedReward(@PathVariable Long id) {
        Monster monster = monsterService.getById(id);
        return monsterService.calculateRecommendedReward(monster);
    }
}
