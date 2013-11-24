use daoju001;
CREATE TABLE `t_goodcase` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '案例名称',
  `industry` varchar(50) DEFAULT NULL COMMENT '行业',
  `work_name` int DEFAULT NULL COMMENT '工件名称',
  `work_material` int DEFAULT NULL COMMENT '工件材质',
  `work_solidity` int DEFAULT NULL COMMENT '工件硬度',
  `work_type` int DEFAULT NULL COMMENT '加工方式',
  `agent_id` int(11) DEFAULT NULL COMMENT '代理商id',
  `agent_name` varchar(50) DEFAULT NULL COMMENT '代理商名字',
  `agent_logo` varchar(50) DEFAULT NULL COMMENT '代理商logo',
  `link` varchar(50) DEFAULT NULL COMMENT '下载链接',
  `count` varchar(50) DEFAULT NULL COMMENT '下载次数',
  `gmt_create` TimeStamp COMMENT '记录创建时间',
  `gmt_modified` TimeStamp COMMENT '记录修改时间(最近时间)',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='刀具案例表';