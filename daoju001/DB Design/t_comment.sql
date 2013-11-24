use daoju001;
CREATE TABLE `t_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` tinyint DEFAULT NULL COMMENT '0=PA的评论，1=CA的评论',
  `content` varchar(100) DEFAULT NULL COMMENT '评论内容',
  `buyer_id` int(11) DEFAULT NULL COMMENT '买家id',
  `agent_id` int(11) DEFAULT NULL COMMENT '卖家id，如果type是1，应该为null',
  `parent` int(11) DEFAULT NULL COMMENT '上一条评论id',
  `answer_id` int(11) DEFAULT NULL COMMENT '回答id',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名称',
  `user_avatar` varchar(50) DEFAULT NULL COMMENT '用户头像',
  `gmt_create` TimeStamp COMMENT '记录创建时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='评论表';
afadsf