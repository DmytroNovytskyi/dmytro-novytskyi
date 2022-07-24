INSERT INTO category(id, status)
VALUES (1001, 'OPENED');
INSERT INTO category(id, status)
VALUES (1002, 'OPENED');
INSERT INTO category(id, status)
VALUES (1003, 'OPENED');
INSERT INTO category(id, status)
VALUES (1004, 'OPENED');
INSERT INTO translation(id, lang, name, category_id)
VALUES (1005, 'en', 'category1', 1001);
INSERT INTO translation(id, lang, name, category_id)
VALUES (1006, 'ua', 'категорія1', 1001);
INSERT INTO translation(id, lang, name, category_id)
VALUES (1007, 'en', 'category2', 1002);
INSERT INTO translation(id, lang, name, category_id)
VALUES (1008, 'ua', 'категорія2', 1002);
INSERT INTO translation(id, lang, name, category_id)
VALUES (1009, 'en', 'category3', 1003);
INSERT INTO translation(id, lang, name, category_id)
VALUES (1010, 'ua', 'категорія3', 1003);
INSERT INTO translation(id, lang, name, category_id)
VALUES (1011, 'en', 'category4', 1004);
INSERT INTO translation(id, lang, name, category_id)
VALUES (1012, 'ua', 'категорія4', 1004);
INSERT INTO activity(id, description, name, status, category_id)
VALUES (1013, 'activity1 description', 'activity1', 'OPENED', 1001);
INSERT INTO activity(id, description, name, status, category_id)
VALUES (1014, 'activity2 description', 'activity2', 'OPENED', 1002);
INSERT INTO activity(id, description, name, status, category_id)
VALUES (1015, 'activity3 description', 'activity3', 'OPENED', 1004);
INSERT INTO user(id, email, password, role, status, username)
VALUES (1016, 'username1@gmail.com', 'password1!', 'WORKER', 'ACTIVE', 'username1');
INSERT INTO user(id, email, password, role, status, username)
VALUES (1017, 'username2@gmail.com', 'password2@', 'ADMIN', 'ACTIVE', 'username2');
INSERT INTO user(id, email, password, role, status, username)
VALUES (1018, 'username3@gmail.com', 'password3#', 'WORKER', 'ACTIVE', 'username3');
INSERT INTO user_has_activity(id, creation_date, end_time, start_time, status, activity_id, user_id)
VALUES (1019, '2022-07-24 15:42:49', '2022-07-24 15:42:52', '2022-07-24 15:42:50', 'COMPLETED', 1013, 1016);
INSERT INTO user_has_activity(id, creation_date, end_time, start_time, status, activity_id, user_id)
VALUES (1020, '2022-07-24 15:43:49', null, null, 'PENDING_ASSIGN', 1014, 1017);
INSERT INTO user_has_activity(id, creation_date, end_time, start_time, status, activity_id, user_id)
VALUES (1021, '2022-07-24 15:44:49', '2022-07-24 15:44:52', '2022-07-24 15:44:50', 'COMPLETED', 1013, 1016);