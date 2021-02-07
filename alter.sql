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