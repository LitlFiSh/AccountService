create table notice(
    id int unsigned primary key not null AUTO_INCREMENT,
    title varchar(255) not null comment '消息标题',
    content text not null comment '消息内容',
    state boolean not null comment '是否未读',
    uid char(11) not null comment '消息对应用户',
    time datetime not null comment '消息创建时间',
    foreign key (`uid`) references userinfo(`id`) on delete cascade,
) engine=innodb comment '消息表';