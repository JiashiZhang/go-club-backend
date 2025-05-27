package com.goclub.xian.tournament.service;

import com.goclub.xian.tournament.models.Group;
import com.goclub.xian.tournament.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    public Group save(Group group) {
        return groupRepository.save(group);
    }

    public Group getById(Long id) {
        return groupRepository.findById(id).orElse(null);
    }

    public Page<Group> findAll(Pageable pageable) {
        return groupRepository.findAll(pageable);
    }

    public List<Group> findByTournamentId(Long tournamentId) {
        return groupRepository.findByTournamentId(tournamentId);
    }

    public void deleteById(Long id) {
        groupRepository.deleteById(id);
    }
}
