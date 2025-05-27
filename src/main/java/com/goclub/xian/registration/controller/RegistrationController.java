package com.goclub.xian.registration.controller;

import com.goclub.xian.registration.models.Registration;
import com.goclub.xian.registration.repository.RegistrationRepository;
import com.goclub.xian.user.repository.UserRepository;
import com.goclub.xian.tournament.repository.TournamentRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@Tag(name = "Registration", description = "报名相关接口")
@RestController
@RequestMapping("/api/registrations")
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationRepository registrationRepo;
    private final UserRepository userRepo;
    private final TournamentRepository tournamentRepo;

    // 列出所有报名（不分页，不推荐大数据量时用）
    @GetMapping
    public List<Registration> list() {
        return registrationRepo.findAll();
    }

    // 创建报名
    @PostMapping
    public Registration create(@RequestBody Registration registration) {
        Long userId = registration.getUserId();
        Long tournamentId = registration.getTournamentId();

        // 校验用户、赛事存在性
        if (!userRepo.existsById(userId)) {
            throw new RuntimeException("用户不存在");
        }
        if (!tournamentRepo.existsById(tournamentId)) {
            throw new RuntimeException("赛事不存在");
        }

        // 防止重复报名
        if (registrationRepo.existsByUserIdAndTournamentId(userId, tournamentId)) {
            throw new RuntimeException("该用户已报名本赛事，不能重复报名");
        }

        // 设置报名时间、状态（如前端没传可加默认值）
        registration.setRegisterTime(new Timestamp(System.currentTimeMillis()).toLocalDateTime());
        if (registration.getStatus() == null) {
            registration.setStatus("UNREGISTERED");
        }
        return registrationRepo.save(registration);
    }

    // 修改报名
    @PutMapping("/{id}")
    public Registration update(@PathVariable Long id, @RequestBody Registration updateData) {
        Registration registration = registrationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("报名信息不存在"));

        // 可以按需选择哪些字段允许更新
        if (updateData.getUserId() != null) {
            if (!userRepo.existsById(updateData.getUserId())) {
                throw new RuntimeException("用户不存在");
            }
            registration.setUserId(updateData.getUserId());
        }
        if (updateData.getTournamentId() != null) {
            if (!tournamentRepo.existsById(updateData.getTournamentId())) {
                throw new RuntimeException("赛事不存在");
            }
            registration.setTournamentId(updateData.getTournamentId());
        }
        if (updateData.getRegisterTime() != null) {
            registration.setRegisterTime(updateData.getRegisterTime());
        }
        if (updateData.getRemark() != null) {
            registration.setRemark(updateData.getRemark());
        }
        if (updateData.getGroupId() != null) {
            registration.setGroupId(updateData.getGroupId());
        }
        if (updateData.getSignInTime() != null) {
            registration.setSignInTime(updateData.getSignInTime());
        }
        if (updateData.getStatus() != null) {
            registration.setStatus(updateData.getStatus());
        }
        if (updateData.getLevelCode() != null) {
            registration.setLevelCode(updateData.getLevelCode());
        }
        return registrationRepo.save(registration);
    }

    // 删除报名
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        registrationRepo.deleteById(id);
    }

    // 按赛事分页查询
    @GetMapping("/by-tournament")
    public Page<Registration> listByTournament(@RequestParam Long tournamentId,
                                               @RequestParam(defaultValue = "1") int page,
                                               @RequestParam(defaultValue = "100") int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id").descending());
        return registrationRepo.findByTournamentId(tournamentId, pageable);
    }

    // 按赛事查全部
    @GetMapping("/by-tournament-all")
    public List<Registration> listByTournamentAll(@RequestParam Long tournamentId) {
        return registrationRepo.findByTournamentId(tournamentId);
    }

    // 按组分页查
    @GetMapping("/by-group")
    public Page<Registration> listByGroup(@RequestParam Long groupId,
                                          @RequestParam(defaultValue = "1") int page,
                                          @RequestParam(defaultValue = "100") int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id").descending());
        return registrationRepo.findByGroupId(groupId, pageable);
    }

    // 按组查全部
    @GetMapping("/by-group-all")
    public List<Registration> listByGroupAll(@RequestParam Long groupId) {
        return registrationRepo.findByGroupId(groupId);
    }
}
