CREATE TABLE bye (
                     id BIGINT PRIMARY KEY AUTO_INCREMENT,
                     tournament_id BIGINT NOT NULL,
                     round INT NOT NULL,         -- 轮次（如第几轮轮空）
                     group_id BIGINT,            -- 分组ID（如果有分组，选填）
                     user_id BIGINT NOT NULL,    -- 轮空的用户ID
                     remark VARCHAR(255),        -- 备注，可选
                     created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                     updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                     CONSTRAINT bye_ibfk_1 FOREIGN KEY (tournament_id) REFERENCES tournament(id) ON DELETE CASCADE
    -- 可根据需要添加更多外键
    -- ,CONSTRAINT bye_ibfk_2 FOREIGN KEY (user_id) REFERENCES user(id)
    -- ,CONSTRAINT bye_ibfk_3 FOREIGN KEY (group_id) REFERENCES tournament_group(id)
);
