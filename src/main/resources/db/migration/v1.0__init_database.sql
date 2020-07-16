drop table if exists tb_user;

create table tb_user
(
    uid      int auto_increment primary key,
    username varchar(32)  not null comment '用户名',
    password varchar(128) not null comment '密码',
    roles    varchar(64)  null comment '角色',
    constraint tb_user_username_unique unique (username)
);

insert into tb_user(uid, username, password, roles)
values (1, 'Yananart', '$2a$10$/NeBKahL0F1WEwtmz3D2feLSNfnogjYBn5a4rVWDOIevzwufFMVPK', 'ADMIN');