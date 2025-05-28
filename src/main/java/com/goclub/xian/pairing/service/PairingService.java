package com.goclub.xian.pairing.service;

import com.goclub.xian.pairing.models.Bye;
import com.goclub.xian.pairing.models.Pairing;
import com.goclub.xian.pairing.repository.ByeRepository;
import com.goclub.xian.pairing.repository.PairingRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PairingService {

    @Autowired
    private PairingRepository pairingRepository;

    @Autowired
    private ByeRepository byeRepository;

    // 获取已报名的用户ID（你需要实现自己的报名逻辑）
    public List<Long> getAllPlayerIds(Long tournamentId, Long groupId) {
        // TODO: 替换为真实报名表查询
        return Arrays.asList(1L, 2L, 3L, 4L, 5L);
    }

    // 获取历史已轮空用户
    public Set<Long> getAlreadyByePlayers(Long tournamentId, Long groupId) {
        List<Bye> byes = byeRepository.findByTournamentIdAndGroupId(tournamentId, groupId);
        return byes.stream().map(Bye::getUserId).collect(Collectors.toSet());
    }

    // 自动生成对阵表与轮空
    @Transactional
    public void generatePairing(Long tournamentId, Long groupId, Integer round) {
        List<Long> allPlayerIds = getAllPlayerIds(tournamentId, groupId);
        Set<Long> alreadyByePlayers = getAlreadyByePlayers(tournamentId, groupId);

        List<Long> players = new ArrayList<>(allPlayerIds);

        Long byePlayer = null;
        if (players.size() % 2 == 1) {
            byePlayer = players.stream().filter(id -> !alreadyByePlayers.contains(id)).findFirst().orElse(players.get(0));
            players.remove(byePlayer);
            Bye bye = new Bye();
            bye.setTournamentId(tournamentId);
            bye.setGroupId(groupId);
            bye.setRound(round);
            bye.setUserId(byePlayer);
            bye.setRemark("自动轮空");
            byeRepository.save(bye);
        }

        int tableNo = 1;
        for (int i = 0; i < players.size(); i += 2) {
            Pairing pairing = new Pairing();
            pairing.setTournamentId(tournamentId);
            pairing.setGroupId(groupId);
            pairing.setRound(round);
            pairing.setTableNo(tableNo++);
            pairing.setPlayerAId(players.get(i));
            pairing.setPlayerBId(players.get(i + 1));
            pairing.setResult("NOT_PLAYED");
            pairingRepository.save(pairing);
        }
    }

    // 查询某轮对阵
    public List<Pairing> getPairings(Long tournamentId, Long groupId, Integer round) {
        return pairingRepository.findByTournamentIdAndGroupIdAndRound(tournamentId, groupId, round);
    }

    // 查询某轮轮空
    public List<Bye> getByes(Long tournamentId, Long groupId, Integer round) {
        return byeRepository.findByTournamentIdAndGroupIdAndRound(tournamentId, groupId, round);
    }

    // 录入成绩
    @Transactional
    public void submitResult(Long pairingId, String result) {
        Pairing pairing = pairingRepository.findById(pairingId)
                .orElseThrow(() -> new RuntimeException("Pairing not found"));
        pairing.setResult(result);
        pairingRepository.save(pairing);
    }

    // 判断某轮是否全部录入成绩
    public boolean isRoundFinished(Long tournamentId, Long groupId, Integer round) {
        List<Pairing> pairings = getPairings(tournamentId, groupId, round);
        return pairings.stream().allMatch(p -> !"NOT_PLAYED".equals(p.getResult()));
    }

    // 撤销某组某轮所有对阵和轮空
    @Transactional
    public void removeRound(Long tournamentId, Long groupId, Integer round) {
        pairingRepository.deleteByTournamentIdAndGroupIdAndRound(tournamentId, groupId, round);
        byeRepository.deleteByTournamentIdAndGroupIdAndRound(tournamentId, groupId, round);
    }
}
