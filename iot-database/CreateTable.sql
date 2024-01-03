-- 删除原有数据库
drop database if exists iot;

-- 创建数据库
create database iot;

-- 使用数据库
use iot;

-- 用户表
create table user (
                      id int unsigned primary key auto_increment comment 'ID',
                      username varchar(20) not null unique comment '用户名',
                      password varchar(32)  comment '密码',
                      nickname varchar(10)  default '' comment '昵称',
                      email varchar(128) default '' comment '邮箱',
                      user_pic varchar(128) default '' comment '头像',
                      create_time datetime not null comment '创建时间',
                      update_time datetime not null comment '修改时间'
) comment '用户表';

-- 设备表
create table device(
                        id int unsigned primary key auto_increment comment 'ID',
                        device_id varchar(32) not null unique comment '设备ID',
                        title varchar(32) not null comment '设备名称',
                        category varchar(32) not null comment '设备类型',
                        state varchar(3) default '未连接' comment '设备状态: 只能是[未连接]、[已连接]',
                        create_user int unsigned not null comment '创建人ID',
                        location varchar(32) default '未定位' not null comment '设备位置',
                        create_time datetime not null comment '创建时间',
                        update_time datetime not null comment '修改时间',
                        constraint fk_device_user foreign key (create_user) references user(id) -- 外键约束
)