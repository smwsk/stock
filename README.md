# stock
根据接口配置表,可直接配置出直接访问的增删改接口,无需再写代码

## 接口配置

### 接口配置表
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
    
> 在接口配置表(table_config)中,各个字典的含义
    
    tableName:设置为自己新创建的表名称
    pathName:为接口中的路径名称
    operateType:只能为是{insert,delete,update,get,list}这五种类型
    showFields: 查询的时候需要返回给接口显示的字段的名称
    operateFields: 查询和删除中需要的条件，或者新增修改需要字段名称，以逗号隔开
    
### 接口数据    
    1	stock	stock	list	*	tid	查询列表
    2	stock	stock	insert	productName,stockNumber,inStockProductPrice,outStockProductPrice,productCategory	productName,stockNumber,inStockProductPrice,outStockProductPrice,productCategory	插入数据
    3	stock	stock	get	productName	tid	获取数据
    4	stock	stock	update	tid	productName	更新数据(更新的showField作为查询条件)
    5	stock	stock	delete		tid	删除数据


### 接口文档
> 本应用引入了Swagger文档,所以只需要启动应用后访问地址 http://localhost:8080/swagger-ui.html
就能查询通用的接口入口。只需要将table_config表的pathName填入swagger提供的接口的pathInfo上,即可获取数据.
注:由于公共接口的特殊性，还未找到在swagger中配置请求参数,输入要加参数，请到postman工具上试

### 接口例子
> 表名称(stock)库存表,表数据(initData.sql)和表结构(initTable.sql)在项目sql文件夹下面

#### 获取库存列表
    请求地址  
        http://localhost:8080/common/stock/listInfo  
    请求参数  
        支持json参数提交，form-data参数提交
    例子 
        {"tid":"2"}

### 其他接口信息
>其他接口信息参考[接口文档](https://apizza.net/pro/#/project/88237b8b4a8ae7dc3339f2104fac9689/browse)