create table board
(
    board_id       bigint auto_increment
        primary key,
    reg_time       datetime(6)                                       null,
    update_time    datetime(6)                                       null,
    create_by      varchar(255)                                      null,
    modified_by    varchar(255)                                      null,
    board_category enum ('FREE_TALK', 'QUESTION', 'REVIEW', 'SHARE') null,
    content        tinytext                                          not null,
    title          varchar(50)                                       not null,
    view           int default 0                                     not null,
    writer         varchar(255)                                      null,
    user_id        bigint                                            null,
    constraint FKfyf1fchnby6hndhlfaidier1r
        foreign key (user_id) references user (user_id)
);

INSERT INTO unknowndog.board (board_id, reg_time, update_time, create_by, modified_by, board_category, content, title, view, writer, user_id) VALUES (3, '2024-07-05 13:10:20.415142', '2024-07-05 13:10:20.415142', '00bom00@naver.com', '00bom00@naver.com', 'FREE_TALK', 'ㅇㅇㅇ', '모르는 개산책 자유게시판 오픈!!', 52, '퀸', null);
INSERT INTO unknowndog.board (board_id, reg_time, update_time, create_by, modified_by, board_category, content, title, view, writer, user_id) VALUES (4, '2024-07-05 15:30:10.733364', '2024-07-05 15:30:10.733364', 'a@a.a', 'a@a.a', 'QUESTION', '10kg 아이도 출입 가능한지 궁금하네요 사람도 많은지도요', '일산 멍튜브 가보신분?', 2, '민개맘', null);
