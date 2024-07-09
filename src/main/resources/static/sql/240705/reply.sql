create table reply
(
    reply_id    bigint auto_increment
        primary key,
    reg_time    datetime(6)  null,
    update_time datetime(6)  null,
    create_by   varchar(255) null,
    modified_by varchar(255) null,
    reply_text  varchar(255) null,
    replyer     varchar(255) null,
    board_id    bigint       null,
    constraint FKcs9hiip0bv9xxfrgoj0lwv2dt
        foreign key (board_id) references board (board_id)
);

INSERT INTO unknowndog.reply (reply_id, reg_time, update_time, create_by, modified_by, reply_text, replyer, board_id) VALUES (1, '2024-07-05 14:48:45.973867', '2024-07-05 14:48:45.973867', 'a@a.a', 'a@a.a', '우왕', '민개맘', 3);
