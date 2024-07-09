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

