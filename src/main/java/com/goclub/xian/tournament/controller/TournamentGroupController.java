package com.goclub.xian.tournament.controller;

import com.goclub.xian.tournament.models.TournamentGroup;
import com.goclub.xian.tournament.service.TournamentGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class TournamentGroupController {

    @Autowired
    private TournamentGroupService groupService;

    @PostMapping
    public TournamentGroup save(@RequestBody TournamentGroup group) {
        return groupService.save(group);
    }

    @GetMapping("/{id}")
    public TournamentGroup get(@PathVariable Long id) {
        return groupService.getById(id);
    }

    @GetMapping
    public Page<TournamentGroup> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return groupService.findAll(PageRequest.of(page, size));
    }

    @GetMapping("/byTournament/{tournamentId}")
    public List<TournamentGroup> byTournament(@PathVariable Long tournamentId) {
        return groupService.findByTournamentId(tournamentId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        groupService.deleteById(id);
    }
}
