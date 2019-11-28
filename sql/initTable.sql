-- 接口配置表
CREATE TABLE `table_config` (
  `tid` int(11) NOT NULL AUTO_INCREMENT COMMENT '表主键id',
  `tableName` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '表名称',
  `pathName` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '路径名称',
  `operateType` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '路径url',
  `showFields` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '显示字段',
  `operateFields` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '操作选项',
  `remark` varchar(1024) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- 库存表
CREATE TABLE `stock` (
  `tid` int(11) NOT NULL AUTO_INCREMENT COMMENT '表主键Id',
  `productName` varchar(1024) DEFAULT NULL COMMENT '产品名称',
  `stockNumber` int(11) DEFAULT NULL COMMENT '库存数量',
  `inStockProductPrice` decimal(11,2) DEFAULT NULL COMMENT '入库价格',
  `outStockProductPrice` decimal(11,2) DEFAULT NULL COMMENT '出库价格',
  `productCategory` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '产品分类',
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- 字典表
CREATE TABLE `dictionary` (
  `tid` int(11) NOT NULL AUTO_INCREMENT,
  `parentId` int(11) DEFAULT NULL COMMENT '父id',
  `codeName` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '字典名称',
  `codeValue` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '字典值',
  `sortIndex` int(11) DEFAULT NULL COMMENT '排序',
  `remark` varchar(1024) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;



