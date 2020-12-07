create table orderapply(
    id char(8) primary key not null comment '申请单编号',
    apply_department char(2) not null comment '申请部门',
    apply_user varchar(32) not null comment '申请人',
    fund_code char(6) not null comment '采购经费代码',
    apply_date datetime not null comment '申请日期',
    total double(16, 2) comment '采购总金额',
    dept_leader_sign tinyint(1) default 0 null comment '部门领导签名状态',
    dept_leader_sign_date datetime null comment '部门领导签名日期',
    inst_leader_sign tinyint(1) default 0 null comment '主管院领导签名状态',
    inst_leader_sign_date datetime null comment '主管院领导签名日期',
    status tinyint(2) default 0 comment '申请单状态',
    file blob null comment '签名文件'
) engine=innodb comment '申请单表';

create table orderlist(
    id char(10) primary key not null,
    no char(2) not null comment '序号',
    name varchar(255) not null comment '物资名称',
    type varchar(255) not null comment '品牌型号',
    configuration text not null comment '配置或技术参数',
    quantity int not null comment '数量',
    budget_unit_price double(12,2) not null comment '预算单价',
    budget_total_price double(14,2) not null comment '预算总价',
    reason text not null comment '申购原因及旧设备参数状态',
    new_user varchar(32) not null comment '新设备使用人',
    status tinyint(2) default 1 comment '申请记录状态',
    from_id char(8) not null comment '对应申请单编号',
    user_id char(11) not null comment '申请人id',
    foreign key (`from_id`) references orderapply(`id`) on delete cascade
) engine=innodb comment '申请记录表';