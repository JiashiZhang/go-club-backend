package com.goclub.xian.tournament.controller;

import com.goclub.xian.tournament.models.Group;
import com.goclub.xian.tournament.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    // 自动批量生成标准组别
    @PostMapping("/api/groups/auto-create")
    public ResponseEntity<?> autoCreateGroups(@RequestParam Long tournamentId,
                                              @RequestParam String type) { // type=级位 or 段位
        try {
            groupService.createDefaultGroups(tournamentId, type);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", e.getMessage()));
        }
    }


    // 新增组别
    @PostMapping
    public Group addGroup(@RequestBody Group group) {
        return groupService.save(group);
    }

    // 修改组别
    @PutMapping("/{id}")
    public Group updateGroup(@PathVariable Long id, @RequestBody Group group) {
        group.setId(id); // 保证用Path里的id
        return groupService.update(group);
    }

    // 查询组别详情
    @GetMapping("/{id}")
    public Group getGroup(@PathVariable Long id) {
        return groupService.getById(id);
    }

    // 查询某赛事下所有组别
    @GetMapping("/by-tournament")
    public List<Group> getGroupsByTournament(@RequestParam Long tournamentId) {
        return groupService.findByTournamentId(tournamentId);
    }

    // 分页查全部
    @GetMapping
    public Page<Group> list(Pageable pageable) {
        return groupService.findAll(pageable);
    }

    // 删除组别
    @DeleteMapping("/{id}")
    public void deleteGroup(@PathVariable Long id) {
        groupService.deleteById(id);
    }
}
