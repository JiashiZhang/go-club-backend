CREATE TABLE operation_log (
                               id BIGINT PRIMARY KEY AUTO_INCREMENT,
                               user_id BIGINT,
                               operation_type VARCHAR(32),
                               entity_type VARCHAR(32),
                               entity_id BIGINT,
                               timestamp DATETIME,
                               ip VARCHAR(64),
                               detail TEXT
);
