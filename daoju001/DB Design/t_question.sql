use daoju001;
CREATE TABLE `t_question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL COMMENT '问题名称',
  `content` varchar(1000) DEFAULT NULL COMMENT '问题内容',
  `buyer_id` int(11) DEFAULT NULL COMMENT '买家id',
  `cnt_PA` int DEFAULT NULL COMMENT '专业回答数量',
  `cnt_CA` int DEFAULT NULL COMMENT '普通回答数量',
  `state` tinyint DEFAULT NULL COMMENT '0=已发布，1=草稿',
  `gmt_create` TimeStamp COMMENT '记录创建时间',
  `gmt_modify` TimeStamp COMMENT '记录修改时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='刀具问题表';