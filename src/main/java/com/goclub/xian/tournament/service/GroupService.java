package com.goclub.xian.tournament.service;

import com.goclub.xian.tournament.models.Group;
import com.goclub.xian.tournament.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    // 保存（新增或更新）
    public Group save(Group group) {
        return groupRepository.save(group);
    }

    // 查询单个
    public Group getById(Long id) {
        return groupRepository.findById(id).orElse(null);
    }

    // 分页查全部
    public Page<Group> findAll(Pageable pageable) {
        return groupRepository.findAll(pageable);
    }

    // 查某赛事下所有组别
    public List<Group> findByTournamentId(Long tournamentId) {
        return groupRepository.findByTournamentId(tournamentId);
    }

    // 删除
    public void deleteById(Long id) {
        groupRepository.deleteById(id);
    }

    // 更新组别
    public Group update(Group group) {
        // 直接 save 即可（id 存在则更新）
        return groupRepository.save(group);
    }

    // 自动批量生成标准组别（18k-1k, 1d-5d, 4升5段），返回插入后的所有组别
    @Transactional
    public List<Group> createDefaultGroups(Long tournamentId, String type) {
        List<Group> toCreate = new ArrayList<>();
        List<Group> existingGroups = groupRepository.findByTournamentId(tournamentId);
        List<String> existingNames = new ArrayList<>();
        for (Group g : existingGroups) existingNames.add(g.getName());

        if ("级位".equals(type)) {
            int[] kLevels = {10, 5, 2, 1};
            for (int k : kLevels) {
                String name = k + "k组";
                if (!existingNames.contains(name)) {
                    Group g = new Group();
                    g.setTournamentId(tournamentId);
                    g.setName(name);
                    g.setMinLevel(k);
                    g.setMaxLevel(k);
                    toCreate.add(g);
                }
            }
        } else if ("段位".equals(type)) {
            int[] dLevels = {1, 2, 3, 4, 5}; // 支持到几段可灵活配置
            for (int d : dLevels) {
                String name = d + "段组";
                if (!existingNames.contains(name)) {
                    Group g = new Group();
                    g.setTournamentId(tournamentId);
                    g.setName(name);
                    g.setMinLevel(-d); // 段位负数约定
                    g.setMaxLevel(-d);
                    toCreate.add(g);
                }
            }
            // 升段组
            String riseName = "4升5段组";
            if (!existingNames.contains(riseName)) {
                Group riseGroup = new Group();
                riseGroup.setTournamentId(tournamentId);
                riseGroup.setName(riseName);
                riseGroup.setMinLevel(-4);
                riseGroup.setMaxLevel(-5);
                riseGroup.setRemark("升段专用");
                toCreate.add(riseGroup);
            }
        }

        if (toCreate.isEmpty()) {
            throw new RuntimeException("所有默认组别已存在，无需重复生成");
        }
        return groupRepository.saveAll(toCreate);
    }


}
