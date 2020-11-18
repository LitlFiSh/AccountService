create table role(
    id int primary key not null auto_increment,
    role_name varchar(32) not null comment '角色名'
) engine=innodb auto_increment=1 comment '角色表';

create table department(
    id int primary key not null auto_increment,
    dept_name varchar(32) not null comment '部门名称'
) engine=innodb auto_increment=1 comment '部门表';

create table account(
    id int primary key not null,
    password varchar(64) not null comment '用户密码',
    is_active tinyint(1) default 1 null comment '账号是否可用',
    role_id int null comment '角色id',
    openid varchar(64) null comment 'openID',
    foreign key (`role_id`) references role(`id`) on delete set null
) engine=innodb comment '账户表';

create table userinfo(
    id int primary key not null,
    username varchar(32) not null comment '用户名',
    account_id int not null comment '外键，账号id',
    department_id int null comment '外键，部门id',
    foreign key (`account_id`) references account(`id`) on delete cascade,
    foreign key (`department_id`) references department(`id`) on delete set null
) engine=innodb comment '用户信息表';

create table menu(
    id int primary key not null,
    path varchar(64) null comment '菜单路径',
    name varchar(32) not null comment '菜单名称',
    pid int null comment '父菜单id',
    rid int null comment '菜单所属角色id',
    foreign key (`rid`) references role(`id`) on delete set null
) engine=innodb comment '菜单表';

insert into role (id, role_name) values (1, "ADMIN");
insert into role (id, role_name) values (2, "USER");

insert into department (id, dept_name) values (1, "部门1");
insert into department (id, dept_name) values (2, "部门2");

insert into account (id, password, role_id) values (0, "$2a$10$bdmKg3qXuLeJxSNzv8csIe6FkWtU1DrqfJEcEMK0gvurQyovi08Dy", 1);
insert into account (id, password, role_id) values (1, "$2a$10$ewNY4Rl1S3SjRoZdXIOSf.XZpHIUm5BAGsRwXqe9a1LkqPFTXM.Te", 2);

insert into userinfo (id, username, account_id, department_id) values (0, "admin01", 0, 1);
insert into userinfo (id, username, account_id, department_id) values (1, "user01", 1, 2);

insert into menu (id, name, rid) values (1, "用户", 2);
insert into menu (id, path, name, pid) values (11, "/info", "用户信息", 1);
insert into menu (id, name, rid) values (2, "管理员", 1);
insert into menu (id, path, name, pid) values (21, "/info1", "信息1", 2);
insert into menu (id, path, name, pid) values (22, "/info2", "信息2", 2);