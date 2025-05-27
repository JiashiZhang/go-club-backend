package com.goclub.xian.tournament.impl;

import com.goclub.xian.tournament.models.Tournament;
import com.goclub.xian.tournament.repository.TournamentRepository;
import com.goclub.xian.tournament.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // 必须加
public class TournamentServiceImpl implements TournamentService {

    private final TournamentRepository tournamentRepository;

    @Autowired
    public TournamentServiceImpl(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    @Override
    public Tournament save(Tournament tournament) {
        return tournamentRepository.save(tournament);
    }

    @Override
    public Tournament getById(Long id) {
        return tournamentRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Tournament> findAll(Pageable pageable) {
        return tournamentRepository.findAll(pageable);
    }

    @Override
    public List<Tournament> findAll() {
        return tournamentRepository.findAll();
    }

}
