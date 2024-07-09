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

