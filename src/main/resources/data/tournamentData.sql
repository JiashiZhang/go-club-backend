-- 创建一场测试比赛
INSERT INTO tournament (name, total_rounds) VALUES ('测试比赛', 7);

-- 比如tournament表自增id生成了1

-- 添加5个选手（假设user表，或报名表registration）
INSERT INTO user (id, username, password, real_name) VALUES (101, 'a', '123', '张三');
INSERT INTO user (id, username, password, real_name) VALUES (102, 'b', '123', '李四');
INSERT INTO user (id, username, password, real_name) VALUES (103, 'c', '123', '王五');
INSERT INTO user (id, username, password, real_name) VALUES (104, 'd', '123', '赵六');
INSERT INTO user (id, username, password, real_name) VALUES (105, 'e', '123', '孙七');

-- 假设你有报名表 registration（推荐以tournament_id和user_id为主键）
INSERT INTO registration (tournament_id, user_id) VALUES (1, 101);
INSERT INTO registration (tournament_id, user_id) VALUES (1, 102);
INSERT INTO registration (tournament_id, user_id) VALUES (1, 103);
INSERT INTO registration (tournament_id, user_id) VALUES (1, 104);
INSERT INTO registration (tournament_id, user_id) VALUES (1, 105);
