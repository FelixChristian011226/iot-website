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
                        connect boolean default false comment '是否连接',
                        create_user int unsigned not null comment '创建人ID',
                        location varchar(32) default '未定位' not null comment '设备位置',
                        create_time datetime not null comment '创建时间',
                        update_time datetime not null comment '修改时间',
                        constraint fk_device_user foreign key (create_user) references user(id) -- 外键约束
) comment '设备表';

-- 数据表
create table device_data(
                            id int unsigned primary key auto_increment comment 'ID',
                            alert boolean not null comment '告警',
                            client_id varchar(32) not null comment '设备ID',
                            info varchar(256) not null comment '信息',
                            lat double not null comment '纬度',
                            lng double not null comment '经度',
                            create_time datetime not null comment '创建时间',
                            timestamp bigint not null comment '时间戳',
                            value int not null comment '值',
                            constraint fk_device_data foreign key (client_id) references device(device_id) -- 外键约束
) comment '数据表';

-- 模拟设备数据
-- insert into device_data (alert, client_id, info, lat, lng, create_time, timestamp, value) values (0, 'device0003', 'Device Data 2024/01/08 00:40:25', 30.16538896560669, 120.20675151348115, '2024-01-08 00:40:25', 1704645625324, 40);
-- insert into device_data (alert, client_id, info, lat, lng, create_time, timestamp, value) values (0, 'device0005', 'Device Data 2024/01/08 00:40:26', 30.277563905715944, 120.15835677385331, '2024-01-08 00:40:26', 1704645626306, 62);
-- insert into device_data (alert, client_id, info, lat, lng, create_time, timestamp, value) values (0, 'device0002', 'Device Data 2024/01/08 00:40:29', 30.15111930370331, 120.18551185131074, '2024-01-08 00:40:29', 1704645629306, 2);
-- insert into device_data (alert, client_id, info, lat, lng, create_time, timestamp, value) values (0, 'device0004', 'Device Data 2024/01/08 00:40:30', 30.49905753135681, 120.43199824094773, '2024-01-08 00:40:30', 1704645630306, 48);
-- insert into device_data (alert, client_id, info, lat, lng, create_time, timestamp, value) values (0, 'device0005', 'Device Data 2024/01/08 00:40:30', 30.474795842170717, 119.97404792308808, '2024-01-08 00:40:30', 1704645630308, 67);
-- insert into device_data (alert, client_id, info, lat, lng, create_time, timestamp, value) values (1, 'device0005', 'Device Data 2024/01/08 00:40:30', 30.46572093963623, 120.44631308317184, '2024-01-08 00:40:30', 1704645630310, 91);
-- insert into device_data (alert, client_id, info, lat, lng, create_time, timestamp, value) values (0, 'device0004', 'Device Data 2024/01/08 00:40:31', 30.446239256858828, 120.08167645931245, '2024-01-08 00:40:31', 1704645631310, 76);
-- insert into device_data (alert, client_id, info, lat, lng, create_time, timestamp, value) values (1, 'device0005', 'Device Data 2024/01/08 00:40:31', 30.171579813957216, 119.98634345531464, '2024-01-08 00:40:31', 1704645631312, 98);
-- insert into device_data (alert, client_id, info, lat, lng, create_time, timestamp, value) values (1, 'device0001', 'Device Data 2024/01/08 00:40:32', 30.16159269809723, 120.41960400342941, '2024-01-08 00:40:32', 1704645632306, 96);
-- insert into device_data (alert, client_id, info, lat, lng, create_time, timestamp, value) values (0, 'device0005', 'Device Data 2024/01/08 00:40:33', 30.10712425708771, 120.1748765707016, '2024-01-08 00:40:33', 1704645633323, 10);
-- insert into device_data (alert, client_id, info, lat, lng, create_time, timestamp, value) values (0, 'device0001', 'Device Data 2024/01/08 00:40:34', 30.234686326980594, 120.17988761663437, '2024-01-08 00:40:34', 1704645634308, 51);
-- insert into device_data (alert, client_id, info, lat, lng, create_time, timestamp, value) values (0, 'device0003', 'Device Data 2024/01/08 00:40:34', 30.33927903175354, 120.03346511125565, '2024-01-08 00:40:34', 1704645634395, 45);
-- insert into device_data (alert, client_id, info, lat, lng, create_time, timestamp, value) values (0, 'device0002', 'Device Data 2024/01/08 00:40:35', 30.122341108322146, 120.12078378200532, '2024-01-08 00:40:35', 1704645635310, 73);
-- insert into device_data (alert, client_id, info, lat, lng, create_time, timestamp, value) values (0, 'device0001', 'Device Data 2024/01/08 00:40:36', 30.183255672454834, 119.94467258453369, '2024-01-08 00:40:36', 1704645636311, 69);
-- insert into device_data (alert, client_id, info, lat, lng, create_time, timestamp, value) values (0, 'device0002', 'Device Data 2024/01/08 00:40:36', 30.413076448440552, 120.10100588798524, '2024-01-08 00:40:36', 1704645636313, 78);
-- insert into device_data (alert, client_id, info, lat, lng, create_time, timestamp, value) values (0, 'device0001', 'Device Data 2024/01/08 00:40:36', 30.413525247573855, 120.12830237150193, '2024-01-08 00:40:36', 1704645636313, 28);
-- insert into device_data (alert, client_id, info, lat, lng, create_time, timestamp, value) values (0, 'device0003', 'Device Data 2024/01/08 00:40:36', 30.26489808559418, 119.99546822309495, '2024-01-08 00:40:36', 1704645636399, 48);
-- insert into device_data (alert, client_id, info, lat, lng, create_time, timestamp, value) values (0, 'device0004', 'Device Data 2024/01/08 00:40:39', 30.122185134887697, 120.05211676359177, '2024-01-08 00:40:39', 1704645639313, 48);
-- insert into device_data (alert, client_id, info, lat, lng, create_time, timestamp, value) values (0, 'device0003', 'Device Data 2024/01/08 00:40:39', 30.33720271587372, 120.23233270645142, '2024-01-08 00:40:39', 1704645639405, 68);
-- insert into device_data (alert, client_id, info, lat, lng, create_time, timestamp, value) values (0, 'device0005', 'Device Data 2024/01/08 00:40:40', 30.30561926364899, 119.92189830541611, '2024-01-08 00:40:40', 1704645640325, 30);
-- insert into device_data (alert, client_id, info, lat, lng, create_time, timestamp, value) values (0, 'device0004', 'Device Data 2024/01/08 00:40:41', 30.273767495155337, 120.13000882863999, '2024-01-08 00:40:41', 1704645641320, 40);
-- insert into device_data (alert, client_id, info, lat, lng, create_time, timestamp, value) values (1, 'device0005', 'Device Data 2024/01/08 00:40:43', 30.310971426963807, 119.91134856939317, '2024-01-08 00:40:43', 1704645643326, 86);
-- insert into device_data (alert, client_id, info, lat, lng, create_time, timestamp, value) values (0, 'device0002', 'Device Data 2024/01/08 00:40:44', 30.316187834739686, 120.34171894788743, '2024-01-08 00:40:44', 1704645644319, 45);
-- insert into device_data (alert, client_id, info, lat, lng, create_time, timestamp, value) values (0, 'device0001', 'Device Data 2024/01/08 00:40:45', 30.295432639122012, 120.14308030605316, '2024-01-08 00:40:45', 1704645645319, 54);
-- insert into device_data (alert, client_id, info, lat, lng, create_time, timestamp, value) values (0, 'device0001', 'Device Data 2024/01/08 00:40:45', 30.13426661491394, 120.13035819530488, '2024-01-08 00:40:45', 1704645645322, 33);
-- insert into device_data (alert, client_id, info, lat, lng, create_time, timestamp, value) values (0, 'device0003', 'Device Data 2024/01/08 00:40:45', 30.48313009738922, 120.13334167003632, '2024-01-08 00:40:45', 1704645645412, 49);
-- insert into device_data (alert, client_id, info, lat, lng, create_time, timestamp, value) values (0, 'device0003', 'Device Data 2024/01/08 00:40:46', 30.419018888473513, 120.16795979738237, '2024-01-08 00:40:46', 1704645646423, 72);
-- insert into device_data (alert, client_id, info, lat, lng, create_time, timestamp, value) values (0, 'device0003', 'Device Data 2024/01/08 00:40:46', 30.382641339302065, 120.37422857284547, '2024-01-08 00:40:46', 1704645646425, 4);
-- insert into device_data (alert, client_id, info, lat, lng, create_time, timestamp, value) values (0, 'device0003', 'Device Data 2024/01/08 00:40:46', 30.223008513450623, 120.3007020354271, '2024-01-08 00:40:46', 1704645646426, 37);
-- insert into device_data (alert, client_id, info, lat, lng, create_time, timestamp, value) values (0, 'device0004', 'Device Data 2024/01/08 00:40:47', 30.24341492652893, 120.34120160341263, '2024-01-08 00:40:47', 1704645647330, 80);
-- insert into device_data (alert, client_id, info, lat, lng, create_time, timestamp, value) values (0, 'device0004', 'Device Data 2024/01/08 00:40:48', 30.1786541223526, 120.27686760425568, '2024-01-08 00:40:48', 1704645648344, 69);
-- insert into device_data (alert, client_id, info, lat, lng, create_time, timestamp, value) values (0, 'device0003', 'Device Data 2024/01/08 00:40:48', 30.423259758949282, 119.99454278945923, '2024-01-08 00:40:48', 1704645648436, 9);
-- insert into device_data (alert, client_id, info, lat, lng, create_time, timestamp, value) values (0, 'device0002', 'Device Data 2024/01/08 00:40:49', 30.160141181945804, 120.10529488325119, '2024-01-08 00:40:49', 1704645649322, 69);
-- insert into device_data (alert, client_id, info, lat, lng, create_time, timestamp, value) values (0, 'device0003', 'Device Data 2024/01/08 00:40:49', 30.372579669952394, 120.02566667795182, '2024-01-08 00:40:49', 1704645649448, 43);
-- insert into device_data (alert, client_id, info, lat, lng, create_time, timestamp, value) values (0, 'device0003', 'Device Data 2024/01/08 00:40:49', 30.27830491065979, 120.29727331399918, '2024-01-08 00:40:49', 1704645649449, 61);
-- insert into device_data (alert, client_id, info, lat, lng, create_time, timestamp, value) values (1, 'device0005', 'Device Data 2024/01/08 00:40:51', 30.28399500846863, 120.27743297815323, '2024-01-08 00:40:51', 1704645651332, 94);
