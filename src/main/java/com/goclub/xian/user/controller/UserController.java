package com.goclub.xian.user.controller;

import com.goclub.xian.dto.EloHistoryDTO;
import com.goclub.xian.dto.GameHistoryDTO;
import com.goclub.xian.dto.GameSummaryDTO;
import com.goclub.xian.dto.UserDTO;
import com.goclub.xian.game.models.Game;
import com.goclub.xian.game.repository.GameRepository;
import com.goclub.xian.tournament.models.Tournament;
import com.goclub.xian.tournament.repository.TournamentRepository;
import com.goclub.xian.user.models.User;
import com.goclub.xian.user.repository.UserRepository;
import com.goclub.xian.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;
    private final GameRepository gameRepository;
    private final TournamentRepository tournamentRepository;
    @GetMapping
    public List<User> list() {
        return userRepository.findAll();
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return userRepository.save(user);
    }

    // 分页+搜索
    @GetMapping("/page")
    public ResponseEntity<Map<String, Object>> searchUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String field
    ) {
        Page<User> userPage = userService.searchUsers(page, size, q, field);
        Map<String, Object> result = new HashMap<>();
        result.put("content", userPage.getContent());
        result.put("totalElements", userPage.getTotalElements());
        return ResponseEntity.ok(result);
    }

    // 详情
    @GetMapping("/{id}")
    public UserDTO detail(@PathVariable Long id, Authentication authentication) {
        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("用户不存在"));
        String currentRole = authentication.getAuthorities().toString();
        return toDTO(user, currentRole);
    }

    // 删除
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

    // 更新学籍区（每年只允许改一次）
    @PostMapping("/updateSchoolDistrict")
    public void updateSchoolDistrict(@RequestBody Map<String, String> payload, Authentication auth) {
        User user = userRepository.findByUsername(auth.getName());
        Date now = new Date();
        if (user.getLastSchoolDistrictUpdate() == null ||
                Duration.between(user.getLastSchoolDistrictUpdate().toInstant(), now.toInstant()).toDays() > 365) {
            user.setSchoolDistrict(payload.get("schoolDistrict"));
            user.setLastSchoolDistrictUpdate(java.sql.Date.valueOf(LocalDate.now()));
            userRepository.save(user);
        } else {
            throw new RuntimeException("学籍区一年只能修改一次");
        }
    }

    // 敏感信息分级
    private UserDTO toDTO(User user, String currentRole) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUuid(user.getUuid());
        dto.setUsername(user.getUsername());
        dto.setRealName(user.getRealName());
        dto.setGender(user.getGender());
        dto.setProvince(user.getProvince());
        dto.setCity(user.getCity());
        dto.setDistrict(user.getDistrict());
        dto.setSchoolDistrict(user.getSchoolDistrict());
        dto.setRankLevel(user.getRankLevel());
        dto.setDanLevel(user.getDanLevel());
        dto.setRole(user.getRole());
        // 敏感信息分级下发
        if (currentRole.contains("SUPERADMIN")) {
            dto.setPhone(user.getPhone());
            dto.setEmail(user.getEmail());
            dto.setIdCard(user.getIdCard());
        } else if (currentRole.contains("ADMIN")) {
            dto.setPhone(user.getPhone());
            dto.setEmail(user.getEmail());
        }
        return dto;
    }

    // 1. Elo 历史时间轴
    @GetMapping("/{userId}/elo-history")
    public List<EloHistoryDTO> getEloHistory(@PathVariable Long userId) {
        List<Game> games = gameRepository.findAllByPlayerAIdOrPlayerBIdOrderByPlayTimeAsc(userId, userId);
        List<EloHistoryDTO> result = new ArrayList<>();
        for (Game game : games) {
            EloHistoryDTO dto = new EloHistoryDTO();
            dto.setPlayTime(game.getPlayTime());
            if (Objects.equals(game.getPlayerAId(), userId)) {
                dto.setElo(Optional.ofNullable(game.getARatingAfter()).orElse(0));
            } else if (Objects.equals(game.getPlayerBId(), userId)) {
                dto.setElo(Optional.ofNullable(game.getBRatingAfter()).orElse(0));
            } else {
                dto.setElo(0); // 极端容错
            }
            result.add(dto);
        }
        return result;
    }

    // 2. 对手分组统计
    @GetMapping("/{userId}/game-summary")
    public List<GameSummaryDTO> getGameSummary(@PathVariable Long userId) {
        List<Game> games = gameRepository.findAllByPlayerAIdOrPlayerBIdOrderByPlayTimeAsc(userId, userId);
        Map<Long, List<Game>> opponentMap = new HashMap<>();
        for (Game g : games) {
            Long opponentId = Objects.equals(g.getPlayerAId(), userId) ? g.getPlayerBId() : g.getPlayerAId();
            if (opponentId == null) continue; // 容错
            opponentMap.computeIfAbsent(opponentId, k -> new ArrayList<>()).add(g);
        }
        List<GameSummaryDTO> result = new ArrayList<>();
        for (Map.Entry<Long, List<Game>> entry : opponentMap.entrySet()) {
            Long opponentId = entry.getKey();
            List<Game> gList = entry.getValue();
            GameSummaryDTO dto = new GameSummaryDTO();
            dto.setOpponentId(opponentId);
            userRepository.findById(opponentId).ifPresent(op -> dto.setOpponentName(op.getRealName()));
            dto.setTotalGames(gList.size());
            int wins = 0, losses = 0, draws = 0;
            for (Game g : gList) {
                String resultStr = Optional.ofNullable(g.getResult()).orElse("");
                boolean userIsA = Objects.equals(g.getPlayerAId(), userId);
                if (userIsA) {
                    if ("A_WIN".equals(resultStr) || resultStr.startsWith("W+")) wins++;
                    else if ("B_WIN".equals(resultStr) || resultStr.startsWith("B+")) losses++;
                    else if ("DRAW".equals(resultStr)) draws++;
                } else {
                    if ("B_WIN".equals(resultStr) || resultStr.startsWith("B+")) wins++;
                    else if ("A_WIN".equals(resultStr) || resultStr.startsWith("W+")) losses++;
                    else if ("DRAW".equals(resultStr)) draws++;
                }
            }
            dto.setWins(wins);
            dto.setLosses(losses);
            dto.setDraws(draws);
            result.add(dto);
        }
        // 按对局数降序
        result.sort(Comparator.comparingInt(GameSummaryDTO::getTotalGames).reversed());
        return result;
    }

    @GetMapping("/{userId}/game-history")
    public List<GameHistoryDTO> getGameHistory(@PathVariable Long userId) {
        List<Game> games = gameRepository.findAllByPlayerAIdOrPlayerBIdOrderByPlayTimeAsc(userId, userId);
        List<GameHistoryDTO> result = new ArrayList<>();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (Game g : games) {
            GameHistoryDTO dto = new GameHistoryDTO();
            dto.setId(g.getId());

            // 判断谁是白谁是黑（假定偶数轮A执白，奇数轮B执白，你如有棋谱可精确记录，最好加个字段）
            // 这里以你数据库没有直接存“白/黑”为例，实际用时请改成真实存储方式！
            boolean aIsWhite = g.getTableNo() % 2 == 0; // **举例**：可根据你实际规则判断
            Long whiteId = aIsWhite ? g.getPlayerAId() : g.getPlayerBId();
            Long blackId = aIsWhite ? g.getPlayerBId() : g.getPlayerAId();

            // 假设在 UserController 的 getGameHistory 接口中
            User white = userRepository.findById(whiteId).orElse(null);
            User black = userRepository.findById(blackId).orElse(null);
            dto.setWhiteName(white == null ? "未知" : white.getRealName());
            dto.setBlackName(black == null ? "未知" : black.getRealName());
            dto.setDate(g.getPlayTime() != null ? g.getPlayTime().format(fmt) : "");
            // 比赛名
            String tournamentName = "";
            if (g.getTournamentId() != null) {
                Tournament t = tournamentRepository.findById(g.getTournamentId()).orElse(null);
                if (t != null) {
                    tournamentName = t.getName();
                }
            }
            dto.setTournament(tournamentName);
            dto.setRound(g.getRound());
            dto.setHandicap(g.getTableNo() != null ? g.getTableNo() : 0); // 如有handicap字段请改用handicap
            dto.setKomi(8); // 如你有komi字段请设置，否则写死
            dto.setResult(g.getResult());
            result.add(dto);
        }
        return result;
    }
}
