package com.goclub.xian.controller;

import com.goclub.xian.models.Registration;
import com.goclub.xian.models.User;
import com.goclub.xian.models.Tournament;
import com.goclub.xian.repository.RegistrationRepository;
import com.goclub.xian.repository.UserRepository;
import com.goclub.xian.repository.TournamentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/registrations")
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationRepository registrationRepo;
    private final UserRepository userRepo;
    private final TournamentRepository tournamentRepo;

    @GetMapping
    public List<Registration> list() {
        return registrationRepo.findAll();
    }

    @PostMapping
    public Registration create(@RequestBody Registration registration) {
        Long userId = registration.getUser().getId();
        Long tournamentId = registration.getTournament().getId();

        // 1. 先查用户和赛事存在性（可选，和你之前代码一致）
        Optional<User> user = userRepo.findById(userId);
        Optional<Tournament> tournament = tournamentRepo.findById(tournamentId);

        if (user.isEmpty() || tournament.isEmpty()) {
            throw new RuntimeException("用户或赛事不存在");
        }

        // 2. **防止重复报名：加这段！**
        if (registrationRepo.existsByUserIdAndTournamentId(userId, tournamentId)) {
            throw new RuntimeException("该用户已报名本赛事，不能重复报名");
        }

        // 3. 新建报名记录
        registration.setUser(user.get());
        registration.setTournament(tournament.get());
        registration.setRegisterTime(new Timestamp(System.currentTimeMillis()));

        // 4. 保存
        return registrationRepo.save(registration);
    }


    // 这里是新加的：编辑报名信息
    @PutMapping("/{id}")
    public Registration update(@PathVariable Long id, @RequestBody Registration updateData) {
        Registration registration = registrationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("报名信息不存在"));

        // 修改用户
        if (updateData.getUser() != null && updateData.getUser().getId() != null) {
            User user = userRepo.findById(updateData.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("用户不存在"));
            registration.setUser(user);
        }
        // 修改赛事
        if (updateData.getTournament() != null && updateData.getTournament().getId() != null) {
            Tournament t = tournamentRepo.findById(updateData.getTournament().getId())
                    .orElseThrow(() -> new RuntimeException("赛事不存在"));
            registration.setTournament(t);
        }
        // 修改报名时间
        if (updateData.getRegisterTime() != null) {
            registration.setRegisterTime(updateData.getRegisterTime());
        }
        return registrationRepo.save(registration);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        registrationRepo.deleteById(id);
    }

    @GetMapping("/by-tournament")
    public Page<Registration> listByTournament(@RequestParam Long tournamentId,
                                               @RequestParam(defaultValue = "1") int page,
                                               @RequestParam(defaultValue = "100") int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id").descending());
        return registrationRepo.findByTournamentId(tournamentId, pageable);
    }

    @GetMapping("/by-tournament-all")
    public List<Registration> listByTournamentAll(@RequestParam Long tournamentId) {
        return registrationRepo.findByTournamentId(tournamentId);
    }
}
