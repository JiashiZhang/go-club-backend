package com.goclub.xian.pairing.repository;

import com.goclub.xian.pairing.models.Bye;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ByeRepository extends JpaRepository<Bye, Long> {
    List<Bye> findByTournamentIdAndRound(Long tournamentId, Integer round);
    List<Bye> findByTournamentId(Long tournamentId);
}
