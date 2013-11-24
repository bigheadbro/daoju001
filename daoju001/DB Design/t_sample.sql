use daoju001;
CREATE TABLE `t_sample` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '样本名称',
  `agent_id` int(11) DEFAULT NULL COMMENT '代理商id',
  `agent_name` varchar(50) DEFAULT NULL COMMENT '代理商名字',
  `agent_logo` varchar(50) DEFAULT NULL COMMENT '代理商logo',
  `link` varchar(50) DEFAULT NULL COMMENT '下载链接',
  `count` varchar(50) DEFAULT NULL COMMENT '下载次数',
  `gmt_create` TimeStamp COMMENT '记录创建时间',
  `gmt_modified` TimeStamp COMMENT '记录修改时间(最近时间)',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='刀具样本表';