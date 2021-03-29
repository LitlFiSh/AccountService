create table role(
    id int primary key not null auto_increment,
    role_name varchar(32) not null comment '角色名',
    role_description varchar(32) not null comment '角色描述'
) engine=innodb auto_increment=1 comment '角色表';

create table department(
    id char(2) primary key not null,
    dept_name varchar(32) not null comment '部门名称'
) engine=innodb comment '部门表';

create table account(
    id char(11) primary key not null,
    password varchar(64) not null comment '用户密码',
    is_active boolean default 1 null comment '账号是否可用',
    role_id int null comment '角色id',
    openid varchar(64) null comment 'openID',
    foreign key (`role_id`) references role(`id`) on delete set null
) engine=innodb comment '账户表';

create table userinfo(
    id char(11) primary key not null,
    username varchar(32) not null comment '用户名',
    account_id char(11) not null comment '外键，账号id',
    department_id char(2) null comment '外键，部门id',
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

insert into role (id, role_name) values (1, "ADMIN", "管理员");
insert into role (id, role_name) values (2, "INSTLEAD", "主管院领导");
insert into role (id, role_name) values (3, "DEPTLEAD", "部门领导");
insert into role (id, role_name) values (4, "APPLYUSER", "普通用户");

insert into department (id, dept_name) values ("01", "办公室");
insert into department (id, dept_name) values ("02", "财务部");
insert into department (id, dept_name) values ("03", "指导中心");
insert into department (id, dept_name) values ("04", "招生部");
insert into department (id, dept_name) values ("05", "规划部");
insert into department (id, dept_name) values ("06", "研究中心");
insert into department (id, dept_name) values ("07", "教学部");
insert into department (id, dept_name) values ("08", "资源部");
insert into department (id, dept_name) values ("09", "技术部");
insert into department (id, dept_name) values ("10", "培训中心");

insert into account (id, password, role_id, is_active) values ("12345678910", "$2a$10$Uyeh8RHUoOsA3yVg9FFHMeW.Kp9gOdnDQyC7Ll0qpTjULvGwA7y4G", 1, 1);
insert into account (id, password, role_id, is_active) values ("12345678911", "$2a$10$Uyeh8RHUoOsA3yVg9FFHMeW.Kp9gOdnDQyC7Ll0qpTjULvGwA7y4G", 4, 1);
insert into account (id, password, role_id, is_active) values ("12345678912", "$2a$10$Uyeh8RHUoOsA3yVg9FFHMeW.Kp9gOdnDQyC7Ll0qpTjULvGwA7y4G", 4, 1);
insert into account (id, password, role_id, is_active) values ("12345678913", "$2a$10$Uyeh8RHUoOsA3yVg9FFHMeW.Kp9gOdnDQyC7Ll0qpTjULvGwA7y4G", 4, 1);
insert into account (id, password, role_id, is_active) values ("12345678914", "$2a$10$Uyeh8RHUoOsA3yVg9FFHMeW.Kp9gOdnDQyC7Ll0qpTjULvGwA7y4G", 3, 1);

insert into userinfo (id, username, account_id, department_id) values ("12345678910", "TestAdmin", "12345678910", "01");
insert into userinfo (id, username, account_id, department_id) values ("12345678911", "办公室用户1", "12345678911", "01");
insert into userinfo (id, username, account_id, department_id) values ("12345678912", "办公室用户2", "12345678912", "01");
insert into userinfo (id, username, account_id, department_id) values ("12345678913", "财务部用户1", "12345678913", "02");
insert into userinfo (id, username, account_id, department_id) values ("12345678914", "办公室领导", "12345678914", "01");

insert into menu (id, name, rid) values (1, "用户", 4);
insert into menu (id, path, name, pid) values (11, "/info", "用户信息", 1);

insert into menu (id, name, rid) values (2, "申请单", 4);
insert into menu (id, path, name, pid) values (21, "/listOrder", "查看申请", 2);
insert into menu (id, path, name, pid) values (22, "/addOrder", "新建申请", 2);

insert into menu (id, name, rid) values (3, "管理", 1);
insert into menu (id, path, name, pid) values (31, "/listUser", "用户管理", 3);

insert into menu (id, name, rid) values (4, "部门", 3);
insert into menu (id, path, name, pid) values (41, "/approval", "待审批", 4);
insert into menu (id, path, name, pid) values (42, "/deptOrder", "查看部门申请", 4);

insert into menu (id, name, rid) values (5, "主管", 2);
insert into menu (id, path, name, pid) values (51, "/approval", "待审批", 5);
insert into menu (id, path, name, pid) values (52, "/instOrder", "查看所有申请", 5);

insert into menu (id, name, rid) vlues (6, "设备列表", 1);
insert into menu (id, path, name, pid) value (61, "/orderlist", "所有申请设备", 6);

insert into menu (id, name, rid) values (7, "采购单", 1);
insert into menu (id, path, name, pid) values (71, "/purchaceOrder", "查看采购单", 7);