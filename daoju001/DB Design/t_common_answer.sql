use daoju001;
CREATE TABLE `t_common_answer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(1000) DEFAULT NULL COMMENT '回答内容',
  `question_id` int(11) DEFAULT NULL COMMENT '问题id',
  `buyer_id` int(11) DEFAULT NULL COMMENT '买家id',
  `buyer_name` varchar(50) DEFAULT NULL COMMENT '回答问题的买家名称',
  `buyer_avatar` varchar(50) DEFAULT NULL COMMENT '回答问题的买家头像',
  `gmt_create` TimeStamp COMMENT '记录创建时间',
  `gmt_modify` TimeStamp COMMENT '记录修改时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='刀具普通回答表';