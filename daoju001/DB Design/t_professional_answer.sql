use daoju001;
CREATE TABLE `t_professional_answer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL COMMENT '回答名称',
  `content` varchar(1000) DEFAULT NULL COMMENT '回答内容',
  `price` int DEFAULT NULL COMMENT '报价',
  `state` tinyint DEFAULT NULL COMMENT '0=已发布，1=草稿',
  `buyer_id` int(11) DEFAULT NULL COMMENT '买家id',
  `question_id` int(11) DEFAULT NULL COMMENT '问题id',
  `agent_id` int(11) DEFAULT NULL COMMENT '代理商id',
  `agent_name` varchar(50) DEFAULT NULL COMMENT '代理商名字',
  `agent_logo` varchar(50) DEFAULT NULL COMMENT '代理商logo',
  `gmt_create` TimeStamp COMMENT '记录创建时间',
  `gmt_modify` TimeStamp COMMENT '记录修改时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='刀具专业回答表';