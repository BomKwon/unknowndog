create table like_quest
(
    like_quest_id bigint auto_increment
        primary key,
    reg_time      datetime(6)  null,
    update_time   datetime(6)  null,
    create_by     varchar(255) null,
    modified_by   varchar(255) null,
    count         int          not null,
    likes_id      bigint       null,
    quest_id      bigint       null,
    constraint FKfwi1hky0p47h4uyhpw0mw0vvb
        foreign key (likes_id) references likes (likes_id),
    constraint FKlunesibp412kcbvqps8d27pu2
        foreign key (quest_id) references quest (quest_id)
);

