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