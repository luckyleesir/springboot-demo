create table `account` (
	`user_id` bigint ( 20 ) not null auto_increment comment '账号id',
	`user_name` varchar ( 50 ) not null comment '账号',
	`password` varchar ( 50 ) not null comment '密码',
	`name` varchar ( 50 ) not null comment '姓名',
	`nick` varchar ( 50 ) comment '昵称',
	`sex` varchar ( 50 ) comment '性别',
	`age` int ( 5 ) comment '年龄',
	`create_time` datetime DEFAULT CURRENT_TIMESTAMP comment '创建时间',
	`update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP comment '最后修改时间',
	primary key ( `user_id` )
) engine = innodb default charset = utf8 comment = '账户表';