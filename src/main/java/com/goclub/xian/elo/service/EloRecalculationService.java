package com.goclub.xian.elo.service;

import com.goclub.xian.elo.util.EloUtil;
import com.goclub.xian.game.models.Game;
import com.goclub.xian.game.repository.GameRepository;
import com.goclub.xian.user.models.User;
import com.goclub.xian.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EloRecalculationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GameRepository gameRepository;

    @Transactional
    public void recalculateAllElo() {
        // 1. 全部用户 Elo 重置
        List<User> users = userRepository.findAll();
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(User::getId, u -> u));
        for (User user : users) user.setEloRating(1500);

        // 2. 所有对局按 playTime 排序
        List<Game> games = gameRepository.findAllByOrderByPlayTimeAsc();

        // 3. 依次回放，直接在内存操作 userMap
        for (Game game : games) {
            User playerA = userMap.get(game.getPlayerAId());
            User playerB = userMap.get(game.getPlayerBId());
            if (playerA == null || playerB == null) continue;

            int aBefore = playerA.getEloRating();
            int bBefore = playerB.getEloRating();

            double scoreA = getScoreA(game.getResult());
            double scoreB = 1 - scoreA;

            int aAfter = EloUtil.calculateElo(aBefore, bBefore, scoreA, EloUtil.DEFAULT_K);
            int bAfter = EloUtil.calculateElo(bBefore, aBefore, scoreB, EloUtil.DEFAULT_K);

            playerA.setEloRating(aAfter);
            playerB.setEloRating(bAfter);

            game.setARatingBefore(aBefore);
            game.setBRatingBefore(bBefore);
            game.setARatingAfter(aAfter);
            game.setBRatingAfter(bAfter);
        }
        // 4. 全部统一saveAll（只操作一次数据库）
        userRepository.saveAll(userMap.values());
        gameRepository.saveAll(games);
    }


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
