CREATE TABLE registration (
                              id BIGINT PRIMARY KEY AUTO_INCREMENT,
                              user_id BIGINT,
                              tournament_id BIGINT,
                              register_time DATETIME,
                              remark VARCHAR(255),
                              UNIQUE KEY uniq_user_tournament (user_id, tournament_id)
);
