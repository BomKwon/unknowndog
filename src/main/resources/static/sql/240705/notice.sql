create table notice
(
    notice_id   bigint auto_increment
        primary key,
    reg_time    datetime(6)   null,
    update_time datetime(6)   null,
    create_by   varchar(255)  null,
    modified_by varchar(255)  null,
    content     tinytext      not null,
    title       varchar(50)   not null,
    view        int default 0 not null,
    writer      varchar(255)  null,
    user_id     bigint        null,
    constraint FKcvf4mh5se36inrxn7xlh2brfv
        foreign key (user_id) references user (user_id)
);

INSERT INTO unknowndog.notice (notice_id, reg_time, update_time, create_by, modified_by, content, title, view, writer, user_id) VALUES (1, '2024-07-05 12:56:51.935517', '2024-07-05 12:56:51.935517', '00bom00@naver.com', '00bom00@naver.com', '제곧내', '모르는 개 산책 페이지 OPEN !!', 2, '퀸', null);
