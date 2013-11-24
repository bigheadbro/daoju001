use daoju001;
CREATE TABLE `t_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL COMMENT '消息名称',
  `content` varchar(1000) DEFAULT NULL COMMENT '消息内容',
  `sender_id` int(11) DEFAULT NULL COMMENT '发送人id',
  `receiver_id` int(11) DEFAULT NULL COMMENT '接收人id',
  `gmt_create` TimeStamp COMMENT '记录创建时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='消息表';