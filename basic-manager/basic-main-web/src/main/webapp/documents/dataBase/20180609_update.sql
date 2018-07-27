
-- 修改常量表相关内容
alter table bas_en_constant change note remark varchar(200);
ALTER  TABLE bas_en_constant RENAME TO sys_en_constant;
update sys_en_menu set menu_identify = 'SysEnConstantList',link = 'sysEnConstant/search.do'
		where menu_identify = 'BaseConstantList';

-- 修改配置表
alter table sys_en_configuration change note remark varchar(200);

-- 修改菜单表
alter table sys_en_menu change order_code order_num int;

-- 修改功能表
alter table sys_en_function change order_code order_num int;

-- 修改角色表
alter table sys_en_role change order_code order_num int;