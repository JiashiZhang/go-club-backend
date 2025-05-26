CREATE TABLE game (
                      id BIGINT PRIMARY KEY AUTO_INCREMENT,
                      tournament_id BIGINT NOT NULL,
                      round INT NOT NULL,
                      table_no INT,
                      player_a_id BIGINT NOT NULL,
                      player_b_id BIGINT NOT NULL,
                      result VARCHAR(10) NOT NULL,    -- 'A_WIN'/'B_WIN'/'DRAW'/'FORFEIT'
                      a_rating_before INT,
                      b_rating_before INT,
                      a_rating_after INT,
                      b_rating_after INT,
                      play_time DATETIME DEFAULT CURRENT_TIMESTAMP
);
