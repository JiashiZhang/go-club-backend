package com.goclub.xian.tournament.controller;

import com.goclub.xian.tournament.models.Tournament;
import com.goclub.xian.tournament.repository.TournamentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tournaments")
@RequiredArgsConstructor
public class TournamentController {
    private final TournamentRepository tournamentRepo;

    // 查询所有赛事
    @GetMapping
    public List<Tournament> list() {
        return tournamentRepo.findAll();
    }

    // 创建新赛事
    @PostMapping
    public Tournament create(@RequestBody Tournament tournament) {
        return tournamentRepo.save(tournament);
    }

    // 删除赛事
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        tournamentRepo.deleteById(id);
    }
}
