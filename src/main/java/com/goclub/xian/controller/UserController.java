package com.goclub.xian.controller;

import com.goclub.xian.models.User;
import com.goclub.xian.models.dto.UserDTO;
import com.goclub.xian.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepo;

    @GetMapping
    public List<User> list() {
        return userRepo.findAll();
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return userRepo.save(user);
    }

    // 分页+搜索
    @GetMapping("/page")
    public Map<String, Object> page(@RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "50") int size) {
        List<Map<String, Object>> users = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            Map<String, Object> user = new HashMap<>();
            user.put("id", (page - 1) * size + i);
            user.put("username", "user" + ((page - 1) * size + i));
            users.add(user);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("page", page);
        result.put("size", size);
        result.put("users", users);
        return result;
    }

    @GetMapping("/api/users/page")
    public ResponseEntity<Map<String, Object>> searchUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String field // 支持指定字段查找
    ) {
        Page<User> userPage = userService.searchUsers(page, size, q, field);
        Map<String, Object> result = new HashMap<>();
        result.put("content", userPage.getContent());
        result.put("totalElements", userPage.getTotalElements());
        return ResponseEntity.ok(result);
    }


    // 详情
    @GetMapping("/{id}")
    public UserDTO detail(@PathVariable Long id, Authentication authentication) {
        User user = userRepo.findById(id).orElseThrow();
        String currentRole = authentication.getAuthorities().toString();
        return toDTO(user, currentRole);
    }

    // 删除
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userRepo.deleteById(id);
    }
    @PostMapping("/updateSchoolDistrict")
    public void updateSchoolDistrict(@RequestBody Map<String, String> payload, Authentication auth) {
        User user = userRepo.findByUsername(auth.getName());
        Date now = new Date();
        if (user.getLastSchoolDistrictUpdate() == null ||
                Duration.between(user.getLastSchoolDistrictUpdate().toInstant(), now.toInstant()).toDays() > 365) {
            user.setSchoolDistrict(payload.get("schoolDistrict"));
            user.setLastSchoolDistrictUpdate(java.sql.Date.valueOf(LocalDate.now()));
            userRepo.save(user);
        } else {
            throw new RuntimeException("学籍区一年只能修改一次");
        }
    }

    // 敏感信息分级
    private UserDTO toDTO(User user, String currentRole) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUuid(user.getUuid());
        dto.setUsername(user.getUsername());
        dto.setRealName(user.getRealName());
        dto.setGender(user.getGender());
        dto.setProvince(user.getProvince());
        dto.setCity(user.getCity());
        dto.setDistrict(user.getDistrict());
        dto.setSchoolDistrict(user.getSchoolDistrict());
        dto.setRankLevel(user.getRankLevel());
        dto.setDanLevel(user.getDanLevel());
        dto.setRole(user.getRole());
        // 敏感信息
        if (currentRole.contains("SUPERADMIN")) {
            dto.setPhone(user.getPhone());
            dto.setEmail(user.getEmail());
            dto.setIdCard(user.getIdCard());
        } else if (currentRole.contains("ADMIN")) {
            dto.setPhone(user.getPhone());
            dto.setEmail(user.getEmail());
        }
        // 密码绝不下发
        return dto;
    }

}
