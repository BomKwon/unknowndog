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

