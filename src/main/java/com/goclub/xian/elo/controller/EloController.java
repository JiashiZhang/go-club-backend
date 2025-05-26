package com.goclub.xian.elo.controller;

import com.goclub.xian.elo.service.EloRecalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/elo")
public class EloController {
    @Autowired
    private EloRecalculationService eloRecalculationService;

    @PostMapping("/recalculate")
    public ResponseEntity<Void> recalculate() {
        eloRecalculationService.recalculateAllElo();
        return ResponseEntity.ok().build();
    }
}
