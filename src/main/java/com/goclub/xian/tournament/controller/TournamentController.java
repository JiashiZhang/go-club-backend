package com.goclub.xian.tournament.controller;

import com.goclub.xian.tournament.models.Tournament;
import com.goclub.xian.tournament.repository.TournamentRepository;
import com.goclub.xian.tournament.service.TournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tournaments")
@RequiredArgsConstructor
public class TournamentController {
    private final TournamentRepository tournamentRepo;
    @Autowired
    private TournamentService tournamentService;
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

    @PostMapping
    public Tournament save(@RequestBody Tournament tournament) {
        return tournamentService.save(tournament);
    }

    @GetMapping("/{id}")
    public Tournament get(@PathVariable Long id) {
        return tournamentService.getById(id);
    }

    @GetMapping
    public Page<Tournament> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return tournamentService.findAll(PageRequest.of(page, size));
    }

}
