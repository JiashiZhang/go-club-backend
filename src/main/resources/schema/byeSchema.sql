CREATE TABLE bye (
                     id BIGINT PRIMARY KEY AUTO_INCREMENT,
                     tournament_id BIGINT NOT NULL,
                     round INT NOT NULL,
                     player_id BIGINT NOT NULL,
                     remark VARCHAR(255),
                     FOREIGN KEY (tournament_id) REFERENCES tournament(id)
);