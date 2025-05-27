package com.goclub.xian.tournament.repository;

import com.goclub.xian.tournament.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByTournamentId(Long tournamentId);
}