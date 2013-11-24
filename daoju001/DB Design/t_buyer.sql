use daoju001;
CREATE TABLE `t_buyer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nick` varchar(50) DEFAULT '' COMMENT 'QQ connect 昵称',
  `qq_connect_id` varchar(255) DEFAULT '' COMMENT 'QQ connect 唯一ID',
  `username` varchar(255) DEFAULT '' COMMENT '用户名字',
  `password` varchar(50) DEFAULT NULL COMMENT '登录密码',
  `email` varchar(30) DEFAULT NULL COMMENT '电子邮箱',
  `reg_time` TimeStamp COMMENT '注册时间',
  `gmt_create` TimeStamp COMMENT '记录创建时间',
  `gmt_modified` TimeStamp COMMENT '记录修改时间(最近登录时间)',
  PRIMARY KEY (`Id`),
  KEY (`username`),
  KEY (`nick`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='买家表';
