CREATE TABLE tournament_group (
                                  id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                  tournament_id BIGINT NOT NULL,
                                  name VARCHAR(50) NOT NULL,           -- 组别名
                                  min_level INT,                       -- 允许报名的最低级/段
                                  max_level INT,                       -- 允许报名的最高级/段
                                  remark VARCHAR(100),
                                  FOREIGN KEY (tournament_id) REFERENCES tournament(id)
);
