create table orderapply(
    id int(6) primary key not null comment '申请单编号',
    apply_department int(2) not null comment '申请部门',
    apply_user int not null comment '申请人',
    fund_code int comment '采购经费代码',
    apply_date datetime not null comment '申请日期',
    total decimal(10, 2) comment '采购总金额',
    dept_leader_sign tinyint(1) default 0 null comment '部门领导签名状态',
    dept_leader_sign_date datetime not null comment '部门领导签名日期',
    inst_leader_sign tinyint(1) default 0 null comment '主管院领导签名状态',
    inst_leader_sign_date datetime not null comment '主管院领导签名日期',
    status tinyint(2) default 0 comment '申请单状态'
) engine=innodb comment '申请单表';

create table orderlist(
    id int primary key not null,
    no tinyint(2) not null comment '序号',
    name varchar(64) not null comment '物资名称',
    type varchar(64) not null comment '品牌型号',
    configuration varchar(64) not null comment '配置或技术参数',
    unit varchar(32) not null comment '单位',
    quantity int(6) not null comment '数量',
    budget_unit_price int(10) not null comment '预算单价',
    budget_total_price int(16) not null comment '预算总价',
    reason text not null comment '申购原因',
    new_user varchar(32) not null comment '新设备使用人',
    old text not null comment '旧设备资产号、名称、设备领用人、目前所在地及设备状态',
    from_id int(6) not null comment '对应申请单编号',
    status tinyint(2) default 1 comment '申请记录状态',
    foreign key (`from_id`) references orderapply(`id`) on delete cascade
) engine=innodb comment '申请记录表';