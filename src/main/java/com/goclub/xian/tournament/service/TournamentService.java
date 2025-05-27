package com.goclub.xian.tournament.service;

import com.goclub.xian.tournament.models.Tournament;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TournamentService {
    Tournament save(Tournament tournament);
    Tournament getById(Long id);
    Page<Tournament> findAll(Pageable pageable);
    void deleteById(Long id);
}
