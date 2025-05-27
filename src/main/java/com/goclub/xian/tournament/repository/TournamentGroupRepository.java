package com.goclub.xian.tournament.repository;

import com.goclub.xian.tournament.models.TournamentGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TournamentGroupRepository extends JpaRepository<TournamentGroup, Long> {
    List<TournamentGroup> findByTournamentId(Long tournamentId);
}