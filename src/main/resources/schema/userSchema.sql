CREATE TABLE user (
                      id BIGINT PRIMARY KEY AUTO_INCREMENT,
                      uuid CHAR(36) NOT NULL UNIQUE,
                      username VARCHAR(50) NOT NULL UNIQUE,
                      real_name VARCHAR(30) NOT NULL,
                      gender VARCHAR(2),
                      birth_date DATE,
                      id_card VARCHAR(18),
                      phone VARCHAR(20),
                      email VARCHAR(100),
                      password VARCHAR(255) NOT NULL,
                      province VARCHAR(30) DEFAULT '陕西',
                      city VARCHAR(30) DEFAULT '西安',
                      district VARCHAR(30) DEFAULT '未央区',
                      school_district VARCHAR(30),
                      rank_level VARCHAR(10),
                      dan_level INT,
                      role VARCHAR(20) DEFAULT 'user',
                      last_school_district_update DATETIME,
                      created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                      updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
ALTER TABLE user ADD COLUMN elo_rating INT DEFAULT 1500;
ALTER TABLE user ADD COLUMN level_code INT;