package com.goclub.xian.repository;

import com.goclub.xian.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    Page<User> findByUsernameContainingIgnoreCaseOrRealNameContainingIgnoreCase(String username, String realName, Pageable pageable);
}
