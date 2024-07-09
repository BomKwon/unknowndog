create table orders
(
    orders_id    bigint auto_increment
        primary key,
    reg_time     datetime(6)              null,
    update_time  datetime(6)              null,
    create_by    varchar(255)             null,
    modified_by  varchar(255)             null,
    order_date   datetime(6)              null,
    order_status enum ('CANCEL', 'ORDER') null,
    user_id      bigint                   null,
    constraint FKel9kyl84ego2otj2accfd8mr7
        foreign key (user_id) references user (user_id)
);

