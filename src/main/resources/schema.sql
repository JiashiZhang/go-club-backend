-- schema.sql

CREATE DATABASE IF NOT EXISTS go_club DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE go_club;

-- 用户表
CREATE TABLE IF NOT EXISTS user (
                                    id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL,  -- "USER" or "ADMIN"
    real_name VARCHAR(50)
    );

-- 赛事表
CREATE TABLE IF NOT EXISTS tournament (
                                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                          name VARCHAR(100) NOT NULL,
    date DATE NOT NULL,
    location VARCHAR(100)
    );

-- 报名表
CREATE TABLE IF NOT EXISTS registration (
                                            id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                            user_id BIGINT,
                                            tournament_id BIGINT,
                                            register_time DATETIME,
                                            FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (tournament_id) REFERENCES tournament(id)
    );
