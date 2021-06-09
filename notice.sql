create table notice(
    id int unsigned primary key not null AUTO_INCREMENT,
    title varchar(255) not null comment '消息标题',
    content text not null comment '消息内容',
    state boolean not null comment '是否未读',
    uid char(11) not null comment '消息对应用户',
    oid char(8) null default null comment '消息对应申清单',
    time datetime not null comment '消息创建时间',
    foreign key (`uid`) references userinfo(`id`) on delete cascade,
) engine=innodb comment '消息表';

create table file(
    id bigint not null auto_increment,
    oid char(8) not null comment '对应申请单id',
    file mediumblob default null comment '附件内容',
    description varchar(100) not null comment '附件对应阶段',
    create_time timestamp null default CURRENT_TIMESTAMP comment '创建时间',
    update_time timestamp null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '更新时间',
    primary key (id)
) engine=innodb comment '附件表';