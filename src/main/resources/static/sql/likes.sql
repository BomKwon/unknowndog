create table likes
(
    likes_id    bigint auto_increment
        primary key,
    reg_time    datetime(6)  null,
    update_time datetime(6)  null,
    create_by   varchar(255) null,
    modified_by varchar(255) null,
    user_id     bigint       null,
    constraint UK3yfb9vqbvu7vae6u28wpe73ux
        unique (user_id),
    constraint FKi2wo4dyk4rok7v4kak8sgkwx0
        foreign key (user_id) references user (user_id)
);

