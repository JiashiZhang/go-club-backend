package com.goclub.xian.registration.repository;

import com.goclub.xian.registration.models.Registration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    Page<Registration> findByTournamentId(Long tournamentId, Pageable pageable);
    List<Registration> findByTournamentId(Long tournamentId);

    Page<Registration> findByGroupId(Long groupId, Pageable pageable);
    List<Registration> findByGroupId(Long groupId);

    boolean existsByUserIdAndTournamentId(Long userId, Long tournamentId);

    Page<Registration> findByUserId(Long userId, Pageable pageable);
}