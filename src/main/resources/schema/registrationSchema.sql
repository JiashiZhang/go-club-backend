CREATE TABLE registration
(
    id            BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id       BIGINT,
    tournament_id BIGINT,
    register_time DATETIME,
    remark        VARCHAR(255),
    UNIQUE KEY uniq_user_tournament (user_id, tournament_id)
);
ALTER TABLE registration
ADD COLUMN group_id     BIGINT,
ADD COLUMN sign_in_time DATETIME,
ADD COLUMN status       VARCHAR(20) DEFAULT 'UNREGISTERED',
ADD CONSTRAINT fk_registration_group FOREIGN KEY (group_id) REFERENCES tournament_group (id);
ALTER TABLE registration ADD COLUMN level_code INT;