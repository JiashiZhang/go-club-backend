package com.goclub.xian.elo.util;

public class EloUtil {
    public static final int DEFAULT_K = 32;

    /**
     * 计算新 Elo
     * @param ratingA  A当前分
     * @param ratingB  B当前分
     * @param scoreA   A得分（胜=1, 平=0.5, 负=0）
     * @param k        K值，常用32
     * @return         A的新分
     */
    public static int calculateElo(int ratingA, int ratingB, double scoreA, int k) {
        double expectedA = 1.0 / (1.0 + Math.pow(10, (ratingB - ratingA) / 400.0));
        return (int) Math.round(ratingA + k * (scoreA - expectedA));
    }
}
