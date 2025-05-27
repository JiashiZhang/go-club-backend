package com.goclub.xian.game.repository;

import com.goclub.xian.game.models.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findByPlayerAIdOrPlayerBIdOrderByPlayTimeDesc(Long playerAId, Long playerBId);

    List<Game> findByTournamentId(Long tournamentId);

    // 查双方历史对局
    List<Game> findByPlayerAIdAndPlayerBIdOrPlayerAIdAndPlayerBId(
            Long a1, Long b1, Long a2, Long b2);
    // 分页可加

    // 按playTime正序排序返回所有Game
    List<Game> findAllByOrderByPlayTimeAsc();

    // 你也可以写分页版
    Page<Game> findAllByOrderByPlayTimeAsc(Pageable pageable);
    List<Game> findAllByPlayerAIdOrPlayerBIdOrderByPlayTimeAsc(Long playerAId, Long playerBId);

    // 如果想查某个用户所有对局（历史战绩）
}
