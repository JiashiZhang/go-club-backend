//package com.goclub.xian.pairing.service;
//
//import com.goclub.xian.pairing.models.Bye;
//import com.goclub.xian.pairing.models.Pairing;
//import com.goclub.xian.pairing.models.PairingResult;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class PairingServiceTest {
//
//    @Autowired
//    private PairingService pairingService;
//
//    // 假设tournamentId为1
//    private static final Long TEST_TOURNAMENT_ID = 1L;
//
//    @Test
//    void testGeneratePairing() {
//        // 第1轮
//        pairingService.generatePairing(TEST_TOURNAMENT_ID, 1);
//
//        List<Pairing> round1 = pairingService.getPairings(TEST_TOURNAMENT_ID, 1);
//        List<Bye> byes1 = pairingService.getByes(TEST_TOURNAMENT_ID, 1);
//
//        System.out.println("第1轮对阵：");
//        for (Pairing p : round1) {
//            System.out.printf("桌%d: %d vs %d\n", p.getTableNo(), p.getPlayerAId(), p.getPlayerBId());
//        }
//        System.out.println("第1轮轮空：" + byes1);
//
//        assertFalse(round1.isEmpty());
//        assertTrue(byes1.size() <= 1);
//    }
//
//    @Test
//    void testResultSubmit() {
//        pairingService.generatePairing(TEST_TOURNAMENT_ID, 2);
//        List<Pairing> round2 = pairingService.getPairings(TEST_TOURNAMENT_ID, 2);
//
//        Pairing firstPair = round2.get(0);
//        pairingService.submitResult(firstPair.getId(), PairingResult.A_WIN);
//
//        Pairing updated = pairingService.getPairings(TEST_TOURNAMENT_ID, 2).stream()
//                .filter(p -> p.getId().equals(firstPair.getId()))
//                .findFirst().orElseThrow();
//
//        assertEquals(PairingResult.A_WIN, updated.getResult());
//    }
//}
