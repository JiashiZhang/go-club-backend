//package com.goclub.xian.testData;
//
//import com.goclub.xian.game.models.Game;
//import com.goclub.xian.game.repository.GameRepository;
//import com.goclub.xian.pairing.models.Pairing;
//import com.goclub.xian.pairing.models.PairingResult;
//import com.goclub.xian.pairing.repository.PairingRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//import java.util.Random;
//
//@Component
//public class GameTestDataGenerator implements CommandLineRunner {
//
//    @Autowired
//    private GameRepository gameRepository;
//    @Autowired
//    private PairingRepository pairingRepository;
//
//    @Override
//    public void run(String... args) throws Exception {
//        long tournamentId = 1L;
//        int totalPlayers = 200;
//        int totalRounds = 5;
//        Random random = new Random();
//
//        // 简单 Swiss Pairing，每轮不考虑实际匹配，只是测试数据
//        for (int round = 1; round <= totalRounds; round++) {
//            for (int board = 1; board <= totalPlayers / 2; board++) {
//                long blackId = board;
//                long whiteId = totalPlayers - board + 1;
//
//                boolean randomBlack = random.nextBoolean();
//                // Pairing
//                Pairing p = new Pairing();
//                p.setTournamentId(tournamentId);
//                p.setRound(round);
//                p.setTableNo(board);
//                p.setPlayerAId(blackId);
//                p.setPlayerBId(whiteId);
//                p.setResult(randomBlack ? PairingResult.A_WIN : PairingResult.B_WIN);
//                pairingRepository.save(p);
//
//                // Game（假装已经有结果）
//                Game g = new Game();
//                g.setTournamentId(tournamentId);
//                g.setRound(round);
//                g.setTableNo(board);
//                g.setPlayerAId(blackId);
//                g.setPlayerBId(whiteId);
//                g.setResult(randomBlack ? "B+R" : "W+5.5");
//                g.setPlayTime(LocalDateTime.now().minusDays(random.nextInt(7)));
//                gameRepository.save(g);
//            }
//        }
//    }
//}
