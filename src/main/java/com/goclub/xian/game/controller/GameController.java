package com.goclub.xian.game.controller;

import com.goclub.xian.game.models.Game;
import com.goclub.xian.dto.GameDTO;
import com.goclub.xian.game.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {

    @Autowired
    private GameService gameService;

    // 查询某用户所有历史对局
    @GetMapping("/user/{userId}")
    public List<Game> getGamesByUser(@PathVariable Long userId) {
        return gameService.getGamesByUser(userId);
    }

    // 查询双方对局历史
    @GetMapping("/between")
    public List<Game> getGamesBetweenUsers(@RequestParam Long userA, @RequestParam Long userB) {
        return gameService.getGamesBetweenUsers(userA, userB);
    }

    // 查询赛事所有对局
    @GetMapping("/tournament/{tournamentId}")
    public List<Game> getGamesByTournament(@PathVariable Long tournamentId) {
        return gameService.getGamesByTournament(tournamentId);
    }

    // 查询用户胜负统计
    @GetMapping("/user/{userId}/stats")
    public GameService.UserGameStats getUserStats(@PathVariable Long userId) {
        return gameService.getUserStats(userId);
    }

    @PostMapping("/record")
    public ResponseEntity<?> createGame(@RequestBody GameDTO gameDto) {
        gameService.recordGameAndUpdateElo(gameDto);
        return ResponseEntity.ok().build();
    }

}
