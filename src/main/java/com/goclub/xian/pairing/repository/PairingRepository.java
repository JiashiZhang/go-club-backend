package com.goclub.xian.pairing.repository;

import com.goclub.xian.pairing.models.Pairing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PairingRepository extends JpaRepository<Pairing, Long> {
    List<Pairing> findByTournamentIdAndRound(Long tournamentId, Integer round);
    List<Pairing> findByTournamentId(Long tournamentId);
}
