create table quest
(
    quest_id      bigint auto_increment
        primary key,
    reg_time      datetime(6)                   null,
    update_time   datetime(6)                   null,
    create_by     varchar(255)                  null,
    modified_by   varchar(255)                  null,
    area          varchar(255)                  not null,
    quest_detail  tinytext                      not null,
    quest_status  enum ('SUCCESS', 'UNSUCCESS') null,
    salary        int                           not null,
    salary_option varchar(255)                  not null,
    stock_number  int default 1                 null,
    title         varchar(50)                   not null,
    view          int default 0                 not null,
    writer        varchar(255)                  null,
    user_id       bigint                        null,
    constraint FKlgsc5aj1jqdfwj8v3pkhbidnx
        foreign key (user_id) references user (user_id)
);

INSERT INTO unknowndog.quest (quest_id, reg_time, update_time, create_by, modified_by, area, quest_detail, quest_status, salary, salary_option, stock_number, title, view, writer, user_id) VALUES (1, '2024-07-05 12:08:10.400408', '2024-07-05 12:08:10.400408', '00bom00@naver.com', '00bom00@naver.com', '경기도 부천시 신중동', '오픈했슴다 ㅎㅎ', 'UNSUCCESS', 0, '함께하개', 1, '!! 모르는 개 산책 OPEN !!', 17, '퀸', null);
INSERT INTO unknowndog.quest (quest_id, reg_time, update_time, create_by, modified_by, area, quest_detail, quest_status, salary, salary_option, stock_number, title, view, writer, user_id) VALUES (2, '2024-07-05 15:09:56.501952', '2024-07-05 15:09:56.501952', 'a@a.a', 'a@a.a', '경기도 부천시 성곡동', '응ㅇ애응애', 'UNSUCCESS', 100000, '일급', 1, '응애 민개 돌봐주세요오ㅛ오오오옹', 22, '민개맘', null);
INSERT INTO unknowndog.quest (quest_id, reg_time, update_time, create_by, modified_by, area, quest_detail, quest_status, salary, salary_option, stock_number, title, view, writer, user_id) VALUES (3, '2024-07-05 15:10:34.695072', '2024-07-05 15:10:34.695072', 'a@a.a', 'a@a.a', '경기도 시흥시 죽율동', '이름은 구름이에요', 'UNSUCCESS', 30000, '시급', 1, '우리아가랑 같이 놀아주세요!!', 2, '민개맘', null);
