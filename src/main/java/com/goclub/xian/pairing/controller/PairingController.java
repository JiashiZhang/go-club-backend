package com.goclub.xian.pairing.controller;

import com.goclub.xian.pairing.models.Bye;
import com.goclub.xian.pairing.models.Pairing;
import com.goclub.xian.pairing.service.PairingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pairings")
@RequiredArgsConstructor
public class PairingController {

    private final PairingService pairingService;

    // 生成对阵和轮空
    @PostMapping("/generate")
    public void generatePairing(@RequestParam Long tournamentId, @RequestParam Long groupId, @RequestParam Integer round) {
        pairingService.generatePairing(tournamentId, groupId, round);
    }

    // 查询某轮对阵
    @GetMapping
    public List<Pairing> getPairings(@RequestParam Long tournamentId, @RequestParam Long groupId, @RequestParam Integer round) {
        return pairingService.getPairings(tournamentId, groupId, round);
    }

    // 查询某轮轮空
    @GetMapping("/bye")
    public List<Bye> getByes(@RequestParam Long tournamentId, @RequestParam Long groupId, @RequestParam Integer round) {
        return pairingService.getByes(tournamentId, groupId, round);
    }

    // 录入成绩
    @PostMapping("/submitResult")
    public void submitResult(@RequestParam Long pairingId, @RequestParam String result) {
        pairingService.submitResult(pairingId, result);
    }

    // 判断本轮是否全部成绩录入
    @GetMapping("/finished")
    public boolean isRoundFinished(@RequestParam Long tournamentId, @RequestParam Long groupId, @RequestParam Integer round) {
        return pairingService.isRoundFinished(tournamentId, groupId, round);
    }

    // 撤销某组某轮
    @DeleteMapping("/round")
    public void removeRound(@RequestParam Long tournamentId, @RequestParam Long groupId, @RequestParam Integer round) {
        pairingService.removeRound(tournamentId, groupId, round);
    }
}
