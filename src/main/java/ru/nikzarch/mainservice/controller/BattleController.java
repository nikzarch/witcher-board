package ru.nikzarch.mainservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nikzarch.mainservice.domain.battles.dto.BattleResultDTO;
import ru.nikzarch.mainservice.service.BattleService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/battles")
@RequiredArgsConstructor
public class BattleController {

    private final BattleService battleService;

    @PostMapping
    public ResponseEntity<BattleResultDTO> fight(
            @RequestParam Long orderId,
            @RequestParam Long witcherId
    ) {
        return ResponseEntity.ok(battleService.fight(orderId, witcherId));
    }

    @GetMapping("/history/{witcherId}")
    public ResponseEntity<List<BattleResultDTO>> history(
            @PathVariable Long witcherId
    ) {
        return ResponseEntity.ok(battleService.getHistory(witcherId));
    }

}
