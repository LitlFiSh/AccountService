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