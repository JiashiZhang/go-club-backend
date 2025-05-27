package com.goclub.xian.tournament.controller;

import com.goclub.xian.tournament.models.Group;
import com.goclub.xian.tournament.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GroupController {
    @Autowired
    private GroupService groupService;

    // 新建/编辑
    @PostMapping("/api/groups")
    public Group save(@RequestBody Group group) {
        return groupService.save(group);
    }

    // 查详情
    @GetMapping("/api/groups/{id}")
    public Group get(@PathVariable Long id) {
        return groupService.getById(id);
    }

    // 分页列表
    @GetMapping("/api/groups")
    public Page<Group> list(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size) {
        return groupService.findAll(PageRequest.of(page, size));
    }

    // 推荐：按赛事ID查全部组
    @GetMapping("/api/tournaments/{tournamentId}/groups")
    public List<Group> byTournament(@PathVariable Long tournamentId) {
        return groupService.findByTournamentId(tournamentId);
    }

    // 删除
    @DeleteMapping("/api/groups/{id}")
    public void delete(@PathVariable Long id) {
        groupService.deleteById(id);
    }
}
