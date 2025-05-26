package com.goclub.xian.pairing.controller;

import com.goclub.xian.pairing.models.Bye;
import com.goclub.xian.pairing.models.Pairing;
import com.goclub.xian.pairing.models.PairingResult;
import com.goclub.xian.pairing.service.PairingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tournaments/{tournamentId}")
public class PairingController {

    @Autowired
    private PairingService pairingService;

    // 生成某轮对阵（管理员权限）
    @PostMapping("/pairings/generate")
    public ResponseEntity<?> generatePairing(@PathVariable Long tournamentId, @RequestParam Integer round) {
        pairingService.generatePairing(tournamentId, round);
        return ResponseEntity.ok("Pairing generated");
    }

    // 查询某轮对阵表
    @GetMapping("/pairings")
    public List<Pairing> getPairings(@PathVariable Long tournamentId, @RequestParam Integer round) {
        return pairingService.getPairings(tournamentId, round);
    }

    // 查询某轮轮空
    @GetMapping("/byes")
    public List<Bye> getByes(@PathVariable Long tournamentId, @RequestParam Integer round) {
        return pairingService.getByes(tournamentId, round);
    }

    // 录入对阵结果
    @PostMapping("/pairings/{pairingId}/result")
    public ResponseEntity<?> submitResult(
            @PathVariable Long tournamentId,
            @PathVariable Long pairingId,
            @RequestParam PairingResult result) {
        pairingService.submitResult(pairingId, result);
        return ResponseEntity.ok("Result saved");
    }
}
