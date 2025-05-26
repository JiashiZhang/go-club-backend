CREATE TABLE tournament (
                            id BIGINT PRIMARY KEY AUTO_INCREMENT,
                            name VARCHAR(64),
                            type VARCHAR(20),
                            start_time DATETIME,
                            end_time DATETIME,
                            location VARCHAR(128),
                            rules TEXT,
                            status VARCHAR(20),
                            created_by BIGINT
);
