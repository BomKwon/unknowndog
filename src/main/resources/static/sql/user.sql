create table user
(
    user_id     bigint auto_increment
        primary key,
    reg_time    datetime(6)            null,
    update_time datetime(6)            null,
    create_by   varchar(255)           null,
    modified_by varchar(255)           null,
    address     varchar(255)           null,
    birth       date                   null,
    email       varchar(255)           null,
    name        varchar(255)           null,
    nickname    varchar(255)           null,
    password    varchar(100)           not null,
    role        enum ('ADMIN', 'USER') null,
    constraint UKn4swgcf30j6bmtb4l4cjryuym
        unique (nickname),
    constraint UKob8kqyqqgmefl0aco34akdtpe
        unique (email)
);

INSERT INTO unknowndog.user (user_id, reg_time, update_time, create_by, modified_by, address, birth, email, name, nickname, password, role) VALUES (1, '2024-07-05 11:51:01.977169', '2024-07-05 11:51:01.977169', 'anonymousUser', 'anonymousUser', '경기도 부천시 신중동', '2024-07-05', '00bom00@naver.com', '권봄', '암어퀸', '$2a$10$lFHuvmfdpuuVankjBCSZp.TWHEGdDnlsmnP1B3J6Q8yqpPTABo8Rm', 'USER');
