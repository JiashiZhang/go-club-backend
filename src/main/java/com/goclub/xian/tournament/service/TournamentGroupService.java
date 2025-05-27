package com.goclub.xian.tournament.service;

import com.goclub.xian.tournament.models.TournamentGroup;
import com.goclub.xian.tournament.repository.TournamentGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TournamentGroupService {

    @Autowired
    private TournamentGroupRepository groupRepository;

    public TournamentGroup save(TournamentGroup group) {
        return groupRepository.save(group);
    }

    public TournamentGroup getById(Long id) {
        return groupRepository.findById(id).orElse(null);
    }

    public Page<TournamentGroup> findAll(Pageable pageable) {
        return groupRepository.findAll(pageable);
    }

    public List<TournamentGroup> findByTournamentId(Long tournamentId) {
        return groupRepository.findByTournamentId(tournamentId);
    }

    public void deleteById(Long id) {
        groupRepository.deleteById(id);
    }
}
