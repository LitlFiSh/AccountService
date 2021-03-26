create table orderapply(
    id char(8) primary key not null comment '申请单编号',
    apply_department varchar(16) not null comment '申请部门',
    apply_user varchar(32) not null comment '申请人',
    fund_code char(6) not null comment '采购经费代码',
    apply_date datetime not null comment '申请日期',
    total double(16, 2) comment '采购总金额',
    dept_leader_sign tinyint(1) default 0 null comment '部门领导签名状态',
    dept_leader_sign_date datetime null comment '部门领导签名日期',
    inst_leader_sign tinyint(1) default 0 null comment '主管院领导签名状态',
    inst_leader_sign_date datetime null comment '主管院领导签名日期',
    status tinyint(2) default 0 comment '申请单状态',
    file blob null comment '签名文件',
    uid char(11) not null comment '申请人id',
    withdrawal_reason text null comment '撤回原因',
    create_time timestamp null default CURRENT_TIMESTAMP comment '创建时间（自动维护）',
    update_time timestamp null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '更新时间（自动维护）'
) engine=innodb comment '申请单表';

create table orderlist(
    id char(10) primary key not null,
    name varchar(255) not null comment '物资名称',
    type varchar(255) not null comment '品牌型号',
    configuration text not null comment '配置或技术参数',
    unit char(1) not null comment '单位',
    quantity int not null comment '数量',
    budget_unit_price double(12,2) not null comment '预算单价',
    budget_total_price double(14,2) not null comment '预算总价',
    reason text not null comment '申购原因及旧设备参数状态',
    new_user varchar(32) not null comment '新设备使用人',
    from_id char(8) not null comment '对应申请单编号',
    status tinyint(2) not null default 0 comment '设备列表状态',
    purchace_id int default null comment '对应采购单id',
    opinion text default null comment '进入采购阶段后填写的设备列表各种情况或意见',
    foreign key (`from_id`) references orderapply(`id`) on delete cascade,
    foreign key (`purchace_id`) references purchace_order(`id`) on delete set null
) engine=innodb comment '申请记录表';

CREATE TABLE `settings` (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(64) NOT NULL COMMENT '描述',
  `value` varchar(64) NOT NULL COMMENT '值',
  PRIMARY KEY (`id`),
  UNIQUE KEY `description` (`description`)
) ENGINE=InnoDB COMMENT='配置表';

insert into settings(description, value) values("采购经费代码", "123456");

create table purchace_order(
    id int primary key AUTO_INCREMENT comment '主键id，自增',
    status tinyint(2) not null default 0 comment '采购单状态',
    uid char(11) not null comment '申请人id',
    create_time timestamp not null default CURRENT_TIMESTAMP comment '采购单创建时间',
    update_time timestamp not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '采购单更新时间'
) ENGINE=InnoDB COMMENT='采购单表';