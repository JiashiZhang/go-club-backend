package com.goclub.xian.registration.repository;

import com.goclub.xian.registration.models.Registration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    Page<Registration> findByTournamentId(Long tournamentId, Pageable pageable);
    List<Registration> findByTournamentId(Long tournamentId);

    boolean existsByUserIdAndTournamentId(Long userId, Long tournamentId);
}
