package com.goclub.xian.user.repository;

import com.goclub.xian.user.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    Page<User> findById(Long id, Pageable pageable);
    Page<User> findByPhoneContaining(String phone, Pageable pageable);
    Page<User> findByRealNameContaining(String name, Pageable pageable);
    // 或者你用 @Query 实现模糊多条件查找

    // 多字段模糊查（用户名/真实姓名/手机号）
    @Query("SELECT u FROM User u WHERE u.username LIKE %:kw% OR u.realName LIKE %:kw% OR u.phone LIKE %:kw%")
    Page<User> findByUsernameOrRealNameOrPhone(@Param("kw") String kw, Pageable pageable);
}
