package com.goclub.xian.registration.service;

import com.goclub.xian.registration.models.Registration;
import com.goclub.xian.tournament.models.Tournament;
import com.goclub.xian.tournament.models.Group;
import com.goclub.xian.tournament.repository.GroupRepository;
import com.goclub.xian.tournament.repository.TournamentRepository;
import com.goclub.xian.user.models.User;

import com.goclub.xian.registration.repository.RegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final TournamentRepository tournamentRepository;
    private final GroupRepository groupRepository;
    private final RegistrationRepository registrationRepository;

    public void checkEligibility(User user, Tournament tournament, Group group) {
        // 区域校验
        if (tournament.getProvinceLimit() != null && !tournament.getProvinceLimit().isEmpty()) {
            if (!tournament.getProvinceLimit().equals(user.getProvince())) {
                throw new RuntimeException("仅限" + tournament.getProvinceLimit() + "棋手报名");
            }
        }
        if (tournament.getCityLimit() != null && !tournament.getCityLimit().isEmpty()) {
            if (!tournament.getCityLimit().equals(user.getCity())) {
                throw new RuntimeException("仅限" + tournament.getCityLimit() + "棋手报名");
            }
        }

        // 级位/段位校验
        int userLevel;
        if (user.getDanLevel() != null && user.getDanLevel() > 0) {
            userLevel = -user.getDanLevel();  // 段位为负数，越高级越小
        } else if (user.getRankLevel() != null) {
            userLevel = Integer.parseInt(user.getRankLevel());  // 级位为正数，越高级越小
        } else {
            throw new RuntimeException("用户无有效级位或段位信息");
        }
        if (group.getMinLevel() != null && userLevel > group.getMinLevel()) {
            throw new RuntimeException("您的级位/段位不符合本组报名要求");
        }
        if (group.getMaxLevel() != null && userLevel < group.getMaxLevel()) {
            throw new RuntimeException("您的级位/段位不符合本组报名要求");
        }
    }

    public Registration register(User user, Long tournamentId, Long groupId, String remark) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new RuntimeException("比赛不存在"));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("组别不存在"));

        checkEligibility(user, tournament, group);

        // 检查是否已报名
        registrationRepository.findByTournamentId(tournamentId).stream()
                .filter(r -> r.getUserId().equals(user.getId()))
                .findAny()
                .ifPresent(r -> { throw new RuntimeException("已报名本场比赛"); });

        Registration registration = new Registration();
        registration.setUserId(user.getId());
        registration.setTournamentId(tournamentId);
        registration.setGroupId(groupId);
        registration.setRegisterTime(LocalDateTime.now());
        registration.setRemark(remark);
        registration.setStatus("REGISTERED");
        return registrationRepository.save(registration);
    }

    public Registration save(Registration registration) {
        return registrationRepository.save(registration);
    }

    public Registration getById(Long id) {
        return registrationRepository.findById(id).orElse(null);
    }

    public Page<Registration> findAll(Pageable pageable) {
        return registrationRepository.findAll(pageable);
    }

    public Page<Registration> findByTournamentId(Long tournamentId, Pageable pageable) {
        return registrationRepository.findByTournamentId(tournamentId, pageable);
    }

    public Page<Registration> findByGroupId(Long groupId, Pageable pageable) {
        return (Page<Registration>) registrationRepository.findByGroupId(groupId, pageable);
    }

    public Page<Registration> findByUserId(Long userId, Pageable pageable) {
        return registrationRepository.findByUserId(userId, pageable);
    }

    public void deleteById(Long id) {
        registrationRepository.deleteById(id);
    }
}
