package com.goclub.xian.pairing.repository;

import com.goclub.xian.pairing.models.Bye;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ByeRepository extends JpaRepository<Bye, Long> {
    List<Bye> findByTournamentIdAndGroupIdAndRound(Long tournamentId, Long groupId, Integer round);
    List<Bye> findByTournamentIdAndGroupId(Long tournamentId, Long groupId);
    void deleteByTournamentIdAndGroupIdAndRound(Long tournamentId, Long groupId, Integer round);
}
