-- -----
ALTER TABLE `demo`.`notice`
ADD COLUMN `oid` char(8) NULL DEFAULT NULL COMMENT '消息对应申清单' AFTER `uid`;
ALTER TABLE `demo`.`role`
ADD COLUMN `role_description` varchar(32) NOT NULL COMMENT '角色描述' AFTER `role_name`;
UPDATE `demo`.`role` SET role_description = '管理员' WHERE id = 1;
UPDATE `demo`.`role` SET role_description = '主管院领导' WHERE id = 2;
UPDATE `demo`.`role` SET role_description = '部门领导' WHERE id = 3;
UPDATE `demo`.`role` SET role_description = '普通用户' WHERE id = 4;
-- 2021/02/01
-- -----
ALTER TABLE `demo`.`orderapply`
ADD COLUMN `version` bigint NOT NULL COMMENT '申请单版本（乐观锁）' AFTER `update_time`;
ALTER TABLE `demo`.`orderapply`
MODIFY COLUMN `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间（自动维护）' AFTER `withdrawal_reason`,
MODIFY COLUMN `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间（自动维护）' AFTER `create_time`;
-- 2021/02/06
-- -----
ALTER TABLE `demo`.`orderlist`
MODIFY COLUMN `unit` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '单位' AFTER `configuration`;
-- 2021/02/06
-- -----
insert into demo.menu (id, path, name, pid) values (32, "/listuser", "用户管理", 3);
UPDATE demo.menu SET path = '/addUser' WHERE id = '31';
UPDATE demo.menu SET path = '/listUser' WHERE	id = '32';

delete from demo.menu where id = '4' or pid = '4';
-- 删除了原有的审批菜单，将审批菜单分别放到部门菜单和主管菜单中，路径没有改变
insert into menu (id, name, rid) values (4, "部门", 3);
insert into menu (id, path, name, pid) values (41, "/approval", "待审批", 4);
insert into menu (id, path, name, pid) values (42, "/deptOrder", "查看部门申请", 4);

insert into menu (id, name, rid) values (5, "主管", 2);
insert into menu (id, path, name, pid) values (51, "/approval", "待审批", 5);
insert into menu (id, path, name, pid) values (52, "/instOrder", "查看所有申请", 5);
-- 2021/03/06
-- -----
delete from demo.menu where id = '31';
update demo.menu set id = '31' where id = '32';
-- 2021/03/07
-- -----
CREATE TABLE `settings` (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(64) NOT NULL COMMENT '描述',
  `value` varchar(64) NOT NULL COMMENT '值',
  PRIMARY KEY (`id`),
  UNIQUE KEY `description` (`description`)
) ENGINE=InnoDB COMMENT='配置表';

insert into settings(description, value) values("采购经费代码", "123456");
-- 2021/03/21
-- -----
create table purchace_order(
    id int primary key AUTO_INCREMENT comment '主键id，自增',
    status tinyint(2) not null default 0 comment '采购单状态',
    uid char(11) not null comment '申请人id',
    create_time timestamp null default CURRENT_TIMESTAMP comment '采购单创建时间',
    update_time timestamp null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '采购单更新时间'
) ENGINE=InnoDB COMMENT='采购单表';
alter table orderlist add column opinion text default null comment '进入采购阶段后填写的设备列表各种情况或意见';
alter table orderlist add column status tinyint(2) not null default 0 comment '设备列表状态';
alter table orderlist add column purchace_id int default null comment '对应采购单id';
alter table orderlist add constraint `fk_orderlist_purchaceorder` foreign key (`purchace_id`) references purchace_order(`id`);
-- 2021年3月26日
-- ---
insert into menu (id, name, rid) values (6, "设备列表", 1);
insert into menu (id, path, name, pid) values (61, "/orderlist", "所有申请设备", 6);

insert into menu (id, name, rid) values (7, "采购单", 1);
insert into menu (id, path, name, pid) values (71, "/purchaceOrder", "查看采购单", 7);
-- 2021年3月29日
-- ---
update menu set rid=null where id=7;
-- 2021年4月1日
-- ---
insert into menu (id, path, name, pid) values (32, "/code", "采购经费代码设置", 3);
-- 2021年4月7日
-- ---
create table file(
    id bigint not null auto_increment,
    oid char(8) not null comment '对应申请单id',
    file mediumblob default null comment '附件内容',
    description varchar(100) not null comment '附件对应阶段',
    create_time timestamp null default CURRENT_TIMESTAMP comment '创建时间',
    update_time timestamp null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '更新时间',
    primary key (id)
) engine=innodb comment '附件表';
-- 2021年6月9日
-- ---
insert into menu (id, path, name, pid) values (33, "/department", "部门管理", 3);
-- 2021年6月13日
-- ---
update menu set rid=null where id=6;
insert into menu (id, path, name, pid) values (34, "/status", "申请单状态管理", 3);
insert into menu (id, name, rid) values (8, "申请单管理", 4);
insert into menu (id, path, name, pid) values (81, "/listOrderApply", "所有申请单", 8);
-- 2021年6月14日
-- ---
update menu set rid=null where id=8;
--2021年6月15日
-- ---
alter table settings drop index `description`
--2021年6月16日