package com.goclub.xian.repository;

import com.goclub.xian.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    Page<User> findById(Long id, Pageable pageable);
    Page<User> findByPhoneContaining(String phone, Pageable pageable);
    Page<User> findByRealNameContaining(String name, Pageable pageable);
// 或者你用 @Query 实现模糊多条件查找

    Page<User> findByUsernameContainingIgnoreCaseOrRealNameContainingIgnoreCase(String username, String realName, Pageable pageable);
}
