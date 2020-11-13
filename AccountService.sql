create table account(
    id int primary key not null,
    password varchar(64) not null comment '用户密码',
    is_active tinyint(1) default 1 null comment '账号是否可用',
    openid varchar(64) null comment 'openID'
) engine=innodb comment '账户表';

create table role(
    id int primary key not null auto_increment,
    role_name varchar(32) not null comment '角色名'
) engine=innodb auto_increment=1 comment '角色表';

create table department(
    id int primary key not null auto_increment,
    dept_name varchar(32) not null comment '部门名称'
) engine=innodb auto_increment=1 comment '部门表';

create table userinfo(
    id int primary key not null,
    username varchar(32) not null comment '用户名',
    account_id int not null comment '外键，账号id',
    department_id int null comment '外键，部门id',
    foreign key (`account_id`) references account(`id`) on delete cascade,
    foreign key (`department_id`) references department(`id`) on delete set null
) engine=innodb comment '用户信息表';

insert into account (id, password) values (0, 123456);
insert into account (id, password) values (1, 123456);

insert into role (id, role_name) values (1, "Admin");
insert into role (id, role_name) values (2, "User");

insert into department (id, dept_name) values (1, "部门1");
insert into department (id, dept_name) values (2, "部门2");

insert into userinfo (id, username, account_id, department_id) values (0, "admin01", 0, 1);
insert into userinfo (id, username, account_id, department_id) values (1, "user01", 1, 2);