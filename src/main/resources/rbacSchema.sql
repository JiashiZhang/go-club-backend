CREATE TABLE role (
                      id BIGINT PRIMARY KEY AUTO_INCREMENT,
                      name VARCHAR(32) NOT NULL UNIQUE
);

CREATE TABLE user_role (
                           user_id BIGINT NOT NULL,
                           role_id BIGINT NOT NULL,
                           PRIMARY KEY (user_id, role_id),
                           FOREIGN KEY (user_id) REFERENCES user(id),
                           FOREIGN KEY (role_id) REFERENCES role(id)
);
