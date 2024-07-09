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

