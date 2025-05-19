-- data.sql

USE go_club;

-- 插入用户
INSERT INTO user (username, password, role, real_name) VALUES
                                                           ('admin', 'admin123', 'ADMIN', '管理员'),
                                                           ('alice', 'alice123', 'USER', '爱丽丝'),
                                                           ('bob', 'bob123', 'USER', '鲍勃');

-- 插入赛事
INSERT INTO tournament (name, date, location) VALUES
                                                  ('2025春季围棋赛', '2025-04-15', '西安市棋院'),
                                                  ('2025夏季业余赛', '2025-07-10', '高新围棋馆');

-- 插入报名（假设用户和赛事id从1自增）
INSERT INTO registration (user_id, tournament_id, register_time) VALUES
                                                                     (2, 1, '2025-04-01 10:00:00'),
                                                                     (3, 1, '2025-04-01 10:05:00'),
                                                                     (2, 2, '2025-06-15 14:00:00');
