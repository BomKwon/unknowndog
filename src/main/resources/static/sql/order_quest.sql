create table order_quest
(
    order_quest_id bigint auto_increment
        primary key,
    reg_time       datetime(6)  null,
    update_time    datetime(6)  null,
    create_by      varchar(255) null,
    modified_by    varchar(255) null,
    count          int          not null,
    order_price    int          not null,
    orders_id      bigint       null,
    quest_id       bigint       null,
    constraint FKbopy9diwav9kfo8okwetpcyt2
        foreign key (quest_id) references quest (quest_id),
    constraint FKj0g5wqmatfxyp7u802k1m34gp
        foreign key (orders_id) references orders (orders_id)
);

