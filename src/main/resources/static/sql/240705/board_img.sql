create table board_img
(
    board_img_id int auto_increment
        primary key,
    reg_time     datetime(6)  null,
    update_time  datetime(6)  null,
    create_by    varchar(255) null,
    modified_by  varchar(255) null,
    img_name     varchar(255) null,
    img_url      varchar(255) null,
    ori_img_name varchar(255) null,
    repimg_yn    varchar(255) null,
    board_id     bigint       null,
    constraint FKey99sxtgmf5fpa0dfs2r2hxaa
        foreign key (board_id) references board (board_id)
);

INSERT INTO unknowndog.board_img (board_img_id, reg_time, update_time, create_by, modified_by, img_name, img_url, ori_img_name, repimg_yn, board_id) VALUES (11, '2024-07-05 13:10:20.418141', '2024-07-05 13:10:20.418141', '00bom00@naver.com', '00bom00@naver.com', '2e294815-619e-443c-975c-ae50aae7fe0b.jpg', '/images/board/2e294815-619e-443c-975c-ae50aae7fe0b.jpg', 'KakaoTalk_20240703_092354419.jpg', 'Y', 3);
INSERT INTO unknowndog.board_img (board_img_id, reg_time, update_time, create_by, modified_by, img_name, img_url, ori_img_name, repimg_yn, board_id) VALUES (12, '2024-07-05 13:10:20.420140', '2024-07-05 13:10:20.420140', '00bom00@naver.com', '00bom00@naver.com', '', '', '', 'N', 3);
INSERT INTO unknowndog.board_img (board_img_id, reg_time, update_time, create_by, modified_by, img_name, img_url, ori_img_name, repimg_yn, board_id) VALUES (13, '2024-07-05 13:10:20.421139', '2024-07-05 13:10:20.421139', '00bom00@naver.com', '00bom00@naver.com', '', '', '', 'N', 3);
INSERT INTO unknowndog.board_img (board_img_id, reg_time, update_time, create_by, modified_by, img_name, img_url, ori_img_name, repimg_yn, board_id) VALUES (14, '2024-07-05 13:10:20.422138', '2024-07-05 13:10:20.422138', '00bom00@naver.com', '00bom00@naver.com', '', '', '', 'N', 3);
INSERT INTO unknowndog.board_img (board_img_id, reg_time, update_time, create_by, modified_by, img_name, img_url, ori_img_name, repimg_yn, board_id) VALUES (15, '2024-07-05 13:10:20.422138', '2024-07-05 13:10:20.422138', '00bom00@naver.com', '00bom00@naver.com', '', '', '', 'N', 3);
INSERT INTO unknowndog.board_img (board_img_id, reg_time, update_time, create_by, modified_by, img_name, img_url, ori_img_name, repimg_yn, board_id) VALUES (16, '2024-07-05 15:30:10.769341', '2024-07-05 15:30:10.769341', 'a@a.a', 'a@a.a', '94694344-c3ba-4ae0-b03b-d1c1bced9da7.jpg', '/images/board/94694344-c3ba-4ae0-b03b-d1c1bced9da7.jpg', 'KakaoTalk_20240620_151752794.jpg', 'Y', 4);
INSERT INTO unknowndog.board_img (board_img_id, reg_time, update_time, create_by, modified_by, img_name, img_url, ori_img_name, repimg_yn, board_id) VALUES (17, '2024-07-05 15:30:10.771342', '2024-07-05 15:30:10.771342', 'a@a.a', 'a@a.a', '', '', '', 'N', 4);
INSERT INTO unknowndog.board_img (board_img_id, reg_time, update_time, create_by, modified_by, img_name, img_url, ori_img_name, repimg_yn, board_id) VALUES (18, '2024-07-05 15:30:10.773339', '2024-07-05 15:30:10.773339', 'a@a.a', 'a@a.a', '', '', '', 'N', 4);
INSERT INTO unknowndog.board_img (board_img_id, reg_time, update_time, create_by, modified_by, img_name, img_url, ori_img_name, repimg_yn, board_id) VALUES (19, '2024-07-05 15:30:10.774339', '2024-07-05 15:30:10.774339', 'a@a.a', 'a@a.a', '', '', '', 'N', 4);
INSERT INTO unknowndog.board_img (board_img_id, reg_time, update_time, create_by, modified_by, img_name, img_url, ori_img_name, repimg_yn, board_id) VALUES (20, '2024-07-05 15:30:10.775339', '2024-07-05 15:30:10.775339', 'a@a.a', 'a@a.a', '', '', '', 'N', 4);
