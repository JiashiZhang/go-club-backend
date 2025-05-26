package com.goclub.xian.game.service;

import com.goclub.xian.game.models.Game;
import com.goclub.xian.game.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

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
}
