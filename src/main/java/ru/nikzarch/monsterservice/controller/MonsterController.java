package ru.nikzarch.monsterservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nikzarch.monsterservice.domain.Monster;
import ru.nikzarch.monsterservice.domain.MonsterFeature;
import ru.nikzarch.monsterservice.service.MonsterFeatureService;
import ru.nikzarch.monsterservice.service.MonsterService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/monsters")
@RequiredArgsConstructor
public class MonsterController {

    private final MonsterService monsterService;
    private final MonsterFeatureService monsterFeatureService;

    @GetMapping
    public List<Monster> getAll() {
        return monsterService.getAll();
    }

    @GetMapping("/{id}")
    public Monster getById(@PathVariable Long id) {
        return monsterService.getById(id);
    }

    @PostMapping
    public Monster create(@RequestBody Monster monster) {
        return monsterService.createMonster(monster);
    }

    @PutMapping("/{id}")
    public Monster update(
            @PathVariable Long id,
            @RequestBody Monster monster
    ) {
        return monsterService.updateMonster(id, monster);
    }

    @GetMapping("/{id}/recommended-reward")
    public int getRecommendedReward(@PathVariable Long id) {
        Monster monster = monsterService.getById(id);
        return monsterService.calculateRecommendedReward(monster);
    }

    @GetMapping("/features")
    public ResponseEntity<List<MonsterFeature>> getAllMonsterFeatures(){
            return ResponseEntity.ok(monsterFeatureService.getAll());
    }
}
