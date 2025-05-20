package com.goclub.xian.controller;

import com.goclub.xian.models.User;
import com.goclub.xian.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userRepo.deleteById(id);
    }

    @GetMapping("/page")
    public Page<User> page(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(required = false) String q) {

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id").descending());

        if (q != null && !q.isEmpty()) {
            return userRepo.findByUsernameContainingIgnoreCaseOrRealNameContainingIgnoreCase(q, q, pageable);
        } else {
            return userRepo.findAll(pageable);
        }
    }


}
