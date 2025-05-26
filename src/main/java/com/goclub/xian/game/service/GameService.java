package com.goclub.xian.game.service;

import com.goclub.xian.elo.util.EloUtil;
import com.goclub.xian.game.models.Game;
import com.goclub.xian.game.models.GameDTO;
import com.goclub.xian.game.repository.GameRepository;
import com.goclub.xian.models.User;
import com.goclub.xian.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GameService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GameRepository gameRepository;

    // 录入比赛历史（对局结果保存）
    public Game recordGame(Long tournamentId, Integer round, Integer tableNo,
                           Long playerAId, Long playerBId, String result,
                           Integer aRatingBefore, Integer bRatingBefore,
                           Integer aRatingAfter, Integer bRatingAfter) {
        Game game = new Game();
        game.setTournamentId(tournamentId);
        game.setRound(round);
        game.setTableNo(tableNo);
        game.setPlayerAId(playerAId);
        game.setPlayerBId(playerBId);
        game.setResult(result);
        game.setARatingBefore(aRatingBefore);
        game.setBRatingBefore(bRatingBefore);
        game.setARatingAfter(aRatingAfter);
        game.setBRatingAfter(bRatingAfter);
        game.setPlayTime(java.time.LocalDateTime.now());
        return gameRepository.save(game);
    }

    // 查询用户所有历史对局
    public List<Game> getGamesByUser(Long userId) {
        return gameRepository.findByPlayerAIdOrPlayerBIdOrderByPlayTimeDesc(userId, userId);
    }

    // 查询双方历史对局
    public List<Game> getGamesBetweenUsers(Long userA, Long userB) {
        return gameRepository.findByPlayerAIdAndPlayerBIdOrPlayerAIdAndPlayerBId(
                userA, userB, userB, userA);
    }

    // 查询赛事全部对局
    public List<Game> getGamesByTournament(Long tournamentId) {
        return gameRepository.findByTournamentId(tournamentId);
    }

    // 用户胜负统计
    public UserGameStats getUserStats(Long userId) {
        List<Game> games = getGamesByUser(userId);
        int win = 0, lose = 0, draw = 0, total = games.size();
        for (Game g : games) {
            if ((g.getPlayerAId().equals(userId) && "A_WIN".equals(g.getResult())) ||
                    (g.getPlayerBId().equals(userId) && "B_WIN".equals(g.getResult()))) {
                win++;
            } else if ((g.getPlayerAId().equals(userId) && "B_WIN".equals(g.getResult())) ||
                    (g.getPlayerBId().equals(userId) && "A_WIN".equals(g.getResult()))) {
                lose++;
            } else if ("DRAW".equals(g.getResult())) {
                draw++;
            }
        }
        return new UserGameStats(total, win, lose, draw);
    }

    // 内部统计结果结构
    public static class UserGameStats {
        public int total, win, lose, draw;
        public UserGameStats(int total, int win, int lose, int draw) {
            this.total = total; this.win = win; this.lose = lose; this.draw = draw;
        }
    }

    public Game recordGameAndUpdateElo(GameDTO dto) {
        User playerA = userRepository.findById(dto.getPlayerAId())
                .orElseThrow(() -> new RuntimeException("Player A not found"));
        User playerB = userRepository.findById(dto.getPlayerBId())
                .orElseThrow(() -> new RuntimeException("Player B not found"));

        int aBefore = playerA.getEloRating();
        int bBefore = playerB.getEloRating();

        double scoreA = getScoreA(dto.getResult()); // 见下方
        double scoreB = 1 - scoreA;

        int aAfter = EloUtil.calculateElo(aBefore, bBefore, scoreA, EloUtil.DEFAULT_K);
        int bAfter = EloUtil.calculateElo(bBefore, aBefore, scoreB, EloUtil.DEFAULT_K);

        // 更新用户 Elo
        playerA.setEloRating(aAfter);
        playerB.setEloRating(bAfter);
        userRepository.save(playerA);
        userRepository.save(playerB);

        // 存储对局
        Game game = new Game();
        game.setTournamentId(dto.getTournamentId());
        game.setRound(dto.getRound());
        game.setTableNo(dto.getTableNo());
        game.setPlayerAId(dto.getPlayerAId());
        game.setPlayerBId(dto.getPlayerBId());
        game.setResult(dto.getResult());
        game.setARatingBefore(aBefore);
        game.setBRatingBefore(bBefore);
        game.setARatingAfter(aAfter);
        game.setBRatingAfter(bAfter);
        game.setPlayTime(LocalDateTime.now());
        game.setGameRecord(dto.getGameRecordUrl());
        game.setRemark(dto.getRemark());
        return gameRepository.save(game);
    }

    // 比赛结果转得分
    private double getScoreA(String result) {
        // 1. 标准枚举处理
        switch (result) {
            case "A_WIN": return 1.0;
            case "B_WIN": return 0.0;
            case "DRAW": return 0.5;
            case "FORFEIT": return 0.0; // 或 1.0
        }
        // 2. 通用围棋结果解析，如 W+5.5, B+R 等
        if (result.startsWith("W+")) {
            return 1.0;  // 白胜（假设A是白棋）
        } else if (result.startsWith("B+")) {
            return 0.0;  // 黑胜（假设A是白棋，B是黑棋）
        }
        throw new IllegalArgumentException("Unknown result: " + result);
    }

}
