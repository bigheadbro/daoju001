use daoju001;
CREATE TABLE `t_buyer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nick` varchar(50) DEFAULT '' COMMENT 'QQ connect 昵称',
  `qq_connect_id` varchar(255) DEFAULT '' COMMENT 'QQ connect 唯一ID',
  `username` varchar(50) DEFAULT '' COMMENT '用户名字',
  `password` varchar(50) DEFAULT NULL COMMENT '登录密码',
  `email` varchar(30) DEFAULT NULL COMMENT '电子邮箱',
  `logo` varchar(50) DEFAULT NULL COMMENT '头像',
  `company_name` varchar(30) DEFAULT NULL COMMENT '公司名字',
  `company_address` varchar(200) DEFAULT NULL COMMENT '工资地址',
  `company_phone` varchar(30) DEFAULT NULL COMMENT '公司电话',
  `contact_name` varchar(30) DEFAULT NULL COMMENT '联系人姓名',
  `contact_phone` varchar(30) DEFAULT NULL COMMENT '联系人电话',
  `contact_qq` varchar(30) DEFAULT NULL COMMENT '联系人QQ',
  `reg_time` TimeStamp COMMENT '注册时间',
  `gmt_create` TimeStamp COMMENT '记录创建时间',
  `gmt_modified` TimeStamp COMMENT '记录修改时间(最近登录时间)',
  PRIMARY KEY (`Id`),
  KEY (`email`),
  KEY (`nick`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='买家表';
