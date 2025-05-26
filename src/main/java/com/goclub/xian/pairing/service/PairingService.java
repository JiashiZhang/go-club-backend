package com.goclub.xian.pairing.service;

import com.goclub.xian.pairing.models.Bye;
import com.goclub.xian.pairing.models.Pairing;
import com.goclub.xian.pairing.models.PairingResult;
import com.goclub.xian.pairing.repository.ByeRepository;
import com.goclub.xian.pairing.repository.PairingRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PairingService {

    @Autowired
    private PairingRepository pairingRepository;
    @Autowired
    private ByeRepository byeRepository;

    // 模拟：所有参赛选手ID
    public List<Long> getAllPlayerIds(Long tournamentId) {
        // TODO: 这里应该查报名表（registration）
        return Arrays.asList(1L,2L,3L,4L,5L); // 假数据
    }

    // 查历史轮空名单
    public Set<Long> getAlreadyByePlayers(Long tournamentId) {
        List<Bye> byes = byeRepository.findByTournamentId(tournamentId);
        return byes.stream().map(Bye::getPlayerId).collect(Collectors.toSet());
    }

    // 自动配对并安排轮空
    @Transactional
    public void generatePairing(Long tournamentId, Integer round) {
        List<Long> allPlayerIds = getAllPlayerIds(tournamentId);

        // 已轮空名单
        Set<Long> alreadyByePlayers = getAlreadyByePlayers(tournamentId);

        // 简单积分排序（这里用原始顺序）
        List<Long> players = new ArrayList<>(allPlayerIds);

        Long byePlayer = null;
        if (players.size() % 2 == 1) {
            // 按未轮空优先
            byePlayer = players.stream().filter(id -> !alreadyByePlayers.contains(id)).findFirst().orElse(players.get(0));
            players.remove(byePlayer);
            Bye bye = new Bye();
            bye.setTournamentId(tournamentId);
            bye.setRound(round);
            bye.setPlayerId(byePlayer);
            bye.setRemark("自动轮空");
            byeRepository.save(bye);
        }

        int tableNo = 1;
        for (int i = 0; i < players.size(); i += 2) {
            Pairing pairing = new Pairing();
            pairing.setTournamentId(tournamentId);
            pairing.setRound(round);
            pairing.setTableNo(tableNo++);
            pairing.setPlayerAId(players.get(i));
            pairing.setPlayerBId(players.get(i + 1));
            pairing.setResult(PairingResult.NOT_PLAYED);
            pairingRepository.save(pairing);
        }
    }

    // 查询某轮对阵
    public List<Pairing> getPairings(Long tournamentId, Integer round) {
        return pairingRepository.findByTournamentIdAndRound(tournamentId, round);
    }

    // 查询某轮轮空
    public List<Bye> getByes(Long tournamentId, Integer round) {
        return byeRepository.findByTournamentIdAndRound(tournamentId, round);
    }

    // 录入对阵结果
    @Transactional
    public void submitResult(Long pairingId, PairingResult result) {
        Pairing pairing = pairingRepository.findById(pairingId)
                .orElseThrow(() -> new RuntimeException("Pairing not found"));
        pairing.setResult(result);
        pairingRepository.save(pairing);
    }
}
