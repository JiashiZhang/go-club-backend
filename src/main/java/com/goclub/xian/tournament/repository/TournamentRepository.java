package com.goclub.xian.tournament.repository;

import com.goclub.xian.tournament.models.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TournamentRepository extends JpaRepository<Tournament, Long> {
}
