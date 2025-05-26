package com.goclub.xian.user.service;

import com.goclub.xian.user.models.User;
import com.goclub.xian.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<User> searchUsers(int page, int size, String q, String field) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id").descending());
        if (q == null || q.isEmpty()) {
            return userRepository.findAll(pageable);
        }
        if ("id".equals(field)) {
            return userRepository.findById(Long.parseLong(q), pageable);
        } else if ("phone".equals(field)) {
            return userRepository.findByPhoneContaining(q, pageable);
        } else if ("realName".equals(field)) {
            return userRepository.findByRealNameContaining(q, pageable);
        }
        // 默认模糊查姓名、手机号
        return userRepository.findByUsernameOrRealNameOrPhone(q, pageable);
    }
}
