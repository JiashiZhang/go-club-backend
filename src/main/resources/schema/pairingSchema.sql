CREATE TABLE pairing (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         tournament_id BIGINT NOT NULL,
                         group_id BIGINT NOT NULL,      -- 分组ID（新加的字段）
                         round INT NOT NULL,
                         table_no INT NOT NULL,
                         player_a_id BIGINT NOT NULL,
                         player_b_id BIGINT NOT NULL,
                         result VARCHAR(16) DEFAULT 'NOT_PLAYED',
                         start_time DATETIME,
                         end_time DATETIME,
                         FOREIGN KEY (tournament_id) REFERENCES tournament(id),
                         FOREIGN KEY (group_id) REFERENCES tournament_group(id)   -- 建议加外键，方便约束和清理
);
