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
) engine=innodb comment '申请单表';

create table orderlist(
    id char(10) primary key not null,
    no char(2) not null comment '序号',
    name varchar(255) not null comment '物资名称',
    type varchar(255) not null comment '品牌型号',
    configuration text not null comment '配置或技术参数',
    unit char(1) not null comment '单位',
    quantity int not null comment '数量',
    budget_unit_price double(12,2) not null comment '预算单价',
    budget_total_price double(14,2) not null comment '预算总价',
    reason text not null comment '申购原因及旧设备参数状态',
    new_user varchar(32) not null comment '新设备使用人',
    status tinyint(2) default 1 comment '申请记录状态',
    from_id char(8) not null comment '对应申请单编号',
    foreign key (`from_id`) references orderapply(`id`) on delete cascade
) engine=innodb comment '申请记录表';

insert into orderapply(id, apply_department, apply_user, fund_code, apply_date, total, uid) values
("20200102", "办公室", "申请人1", "123456", "2020-12-11 00:00:00", 0, "12345678911");
insert into orderlist(id, no, name, type, configuration, unit, quantity, budget_unit_price, budget_total_price, reason, new_user, from_id)
values ("2020010201", "01", "设备1", "类型1", "配置参数", "个", 5, 0, 0, "原因", "新使用人", "20200102");
insert into orderlist(id, no, name, type, configuration, unit, quantity, budget_unit_price, budget_total_price, reason, new_user, from_id)
values ("2020010202", "02", "设备2", "类型2", "配置参数", "台", 1, 0, 0, "原因", "新使用人", "20200102");
insert into orderlist(id, no, name, type, configuration, unit, quantity, budget_unit_price, budget_total_price, reason, new_user, from_id)
values ("2020010203", "03", "设备3", "类型3", "配置参数", "部", 1, 0, 0, "原因", "新使用人", "20200102");

insert into orderapply(id, apply_department, apply_user, fund_code, apply_date, total, uid, status) values
("20200103", "办公室", "办公室用户1", "123456", "2020-12-11 00:00:00", 100, "12345678911", 1);
insert into orderlist(id, no, name, type, configuration, unit, quantity, budget_unit_price, budget_total_price, reason, new_user, from_id)
values ("2020010301", "01", "Peral Mini", "Epiphan", "视频输入接口支持3种3G SDI、 HDMI、(3) USB UVC，视频输出接口有HDMI，视频处理编码支持H.264，可网络管理，主机自带7寸触摸显示屏幕，可录制1080p高清视频，支持录制到SD存储卡，可自动上传文件保存到其他服务器",
"个", 1, 100, 100, "必要性及用途：录像盒子主要是录制周末面授辅导课，每个学期300多门辅导课程的录制工作；使用状况：目前使用的同品牌录像盒子使用已超过6年时间，期间也经历了维修、更换等情况，为保证录课质量需要对录像盒子进行更新换代。旧盒子已达报废年限，可进行报废处理。", "新使用人", "20200103");
