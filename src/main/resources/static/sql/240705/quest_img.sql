create table quest_img
(
    quest_img_id int auto_increment
        primary key,
    reg_time     datetime(6)  null,
    update_time  datetime(6)  null,
    create_by    varchar(255) null,
    modified_by  varchar(255) null,
    img_name     varchar(255) null,
    img_url      varchar(255) null,
    ori_img_name varchar(255) null,
    repimg_yn    varchar(255) null,
    quest_id     bigint       null,
    constraint FK1pjqlx3xw2xgkmokxgdjwxd27
        foreign key (quest_id) references quest (quest_id)
);

INSERT INTO unknowndog.quest_img (quest_img_id, reg_time, update_time, create_by, modified_by, img_name, img_url, ori_img_name, repimg_yn, quest_id) VALUES (1, '2024-07-05 12:08:10.417793', '2024-07-05 12:08:10.417793', '00bom00@naver.com', '00bom00@naver.com', '0bc5e51a-d7fd-47fe-b3eb-77e93ffbacd7.jpg', '/images/quest/0bc5e51a-d7fd-47fe-b3eb-77e93ffbacd7.jpg', 'KakaoTalk_20240704_171331490_06.jpg', 'Y', 1);
INSERT INTO unknowndog.quest_img (quest_img_id, reg_time, update_time, create_by, modified_by, img_name, img_url, ori_img_name, repimg_yn, quest_id) VALUES (2, '2024-07-05 12:08:10.419791', '2024-07-05 12:08:10.419791', '00bom00@naver.com', '00bom00@naver.com', '', '', '', 'N', 1);
INSERT INTO unknowndog.quest_img (quest_img_id, reg_time, update_time, create_by, modified_by, img_name, img_url, ori_img_name, repimg_yn, quest_id) VALUES (3, '2024-07-05 12:08:10.420790', '2024-07-05 12:08:10.420790', '00bom00@naver.com', '00bom00@naver.com', '', '', '', 'N', 1);
INSERT INTO unknowndog.quest_img (quest_img_id, reg_time, update_time, create_by, modified_by, img_name, img_url, ori_img_name, repimg_yn, quest_id) VALUES (4, '2024-07-05 12:08:10.421789', '2024-07-05 12:08:10.421789', '00bom00@naver.com', '00bom00@naver.com', '', '', '', 'N', 1);
INSERT INTO unknowndog.quest_img (quest_img_id, reg_time, update_time, create_by, modified_by, img_name, img_url, ori_img_name, repimg_yn, quest_id) VALUES (5, '2024-07-05 12:08:10.422789', '2024-07-05 12:08:10.422789', '00bom00@naver.com', '00bom00@naver.com', '', '', '', 'N', 1);
INSERT INTO unknowndog.quest_img (quest_img_id, reg_time, update_time, create_by, modified_by, img_name, img_url, ori_img_name, repimg_yn, quest_id) VALUES (6, '2024-07-05 15:09:56.541436', '2024-07-05 15:09:56.541436', 'a@a.a', 'a@a.a', 'b728d6e6-5cc7-43ea-9896-5ba8432685c4.jpg', '/images/quest/b728d6e6-5cc7-43ea-9896-5ba8432685c4.jpg', 'KakaoTalk_20240620_164518986.jpg', 'Y', 2);
INSERT INTO unknowndog.quest_img (quest_img_id, reg_time, update_time, create_by, modified_by, img_name, img_url, ori_img_name, repimg_yn, quest_id) VALUES (7, '2024-07-05 15:09:56.542436', '2024-07-05 15:09:56.542436', 'a@a.a', 'a@a.a', '', '', '', 'N', 2);
INSERT INTO unknowndog.quest_img (quest_img_id, reg_time, update_time, create_by, modified_by, img_name, img_url, ori_img_name, repimg_yn, quest_id) VALUES (8, '2024-07-05 15:09:56.543435', '2024-07-05 15:09:56.543435', 'a@a.a', 'a@a.a', '', '', '', 'N', 2);
INSERT INTO unknowndog.quest_img (quest_img_id, reg_time, update_time, create_by, modified_by, img_name, img_url, ori_img_name, repimg_yn, quest_id) VALUES (9, '2024-07-05 15:09:56.544436', '2024-07-05 15:09:56.544436', 'a@a.a', 'a@a.a', '', '', '', 'N', 2);
INSERT INTO unknowndog.quest_img (quest_img_id, reg_time, update_time, create_by, modified_by, img_name, img_url, ori_img_name, repimg_yn, quest_id) VALUES (10, '2024-07-05 15:09:56.545434', '2024-07-05 15:09:56.545434', 'a@a.a', 'a@a.a', '', '', '', 'N', 2);
INSERT INTO unknowndog.quest_img (quest_img_id, reg_time, update_time, create_by, modified_by, img_name, img_url, ori_img_name, repimg_yn, quest_id) VALUES (11, '2024-07-05 15:10:34.698070', '2024-07-05 15:10:34.698070', 'a@a.a', 'a@a.a', '8fe47d39-de63-4bfb-a177-0fff5865f65d.jpg', '/images/quest/8fe47d39-de63-4bfb-a177-0fff5865f65d.jpg', 'KakaoTalk_20240627_132125055_02.jpg', 'Y', 3);
INSERT INTO unknowndog.quest_img (quest_img_id, reg_time, update_time, create_by, modified_by, img_name, img_url, ori_img_name, repimg_yn, quest_id) VALUES (12, '2024-07-05 15:10:34.699069', '2024-07-05 15:10:34.699069', 'a@a.a', 'a@a.a', '', '', '', 'N', 3);
INSERT INTO unknowndog.quest_img (quest_img_id, reg_time, update_time, create_by, modified_by, img_name, img_url, ori_img_name, repimg_yn, quest_id) VALUES (13, '2024-07-05 15:10:34.699069', '2024-07-05 15:10:34.699069', 'a@a.a', 'a@a.a', '', '', '', 'N', 3);
INSERT INTO unknowndog.quest_img (quest_img_id, reg_time, update_time, create_by, modified_by, img_name, img_url, ori_img_name, repimg_yn, quest_id) VALUES (14, '2024-07-05 15:10:34.701069', '2024-07-05 15:10:34.701069', 'a@a.a', 'a@a.a', '', '', '', 'N', 3);
INSERT INTO unknowndog.quest_img (quest_img_id, reg_time, update_time, create_by, modified_by, img_name, img_url, ori_img_name, repimg_yn, quest_id) VALUES (15, '2024-07-05 15:10:34.703069', '2024-07-05 15:10:34.703069', 'a@a.a', 'a@a.a', '', '', '', 'N', 3);
