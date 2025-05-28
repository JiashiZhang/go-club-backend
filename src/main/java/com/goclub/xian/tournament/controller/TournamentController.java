package com.goclub.xian.tournament.controller;

import com.goclub.xian.tournament.models.Tournament;
import com.goclub.xian.tournament.service.TournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tournaments")
@RequiredArgsConstructor
public class TournamentController {

    private final TournamentService tournamentService;

    @PostMapping
    public Tournament save(@RequestBody Tournament tournament) {
        return tournamentService.save(tournament);
    }

    @GetMapping("/{id}")
    public Tournament get(@PathVariable Long id) {
        return tournamentService.getById(id);
    }

    @GetMapping("/all")
    public Page<Tournament> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return tournamentService.findAll(PageRequest.of(page, size));
    }

    @GetMapping("/singleAll")
    public List<Tournament> getAllTournaments() {
        return tournamentService.findAll(); // 这里findAll()直接返回List，不分页
    }


}
