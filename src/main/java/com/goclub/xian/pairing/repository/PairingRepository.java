package com.goclub.xian.pairing.repository;

import com.goclub.xian.pairing.models.Pairing;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PairingRepository extends JpaRepository<Pairing, Long> {
    List<Pairing> findByTournamentIdAndGroupIdAndRound(Long tournamentId, Long groupId, Integer round);
    List<Pairing> findByTournamentIdAndGroupId(Long tournamentId, Long groupId);
    void deleteByTournamentIdAndGroupIdAndRound(Long tournamentId, Long groupId, Integer round);
}
