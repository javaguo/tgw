/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50520
Source Host           : localhost:3306
Source Database       : succ

Target Server Type    : MYSQL
Target Server Version : 50520
File Encoding         : 65001

Date: 2018-04-22 13:55:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for bas_en_constant
-- ----------------------------
DROP TABLE IF EXISTS `bas_en_constant`;
CREATE TABLE `bas_en_constant` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `code` varchar(100) NOT NULL,
  `namespace` varchar(100) NOT NULL,
  `is_valid` smallint(6) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `namespace` (`namespace`,`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bas_en_constant
-- ----------------------------

-- ----------------------------
-- Table structure for example_bean
-- ----------------------------
DROP TABLE IF EXISTS `example_bean`;
CREATE TABLE `example_bean` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `form_hidden` varchar(255) DEFAULT NULL,
  `form_text` varchar(255) DEFAULT NULL,
  `form_password` varchar(255) DEFAULT NULL,
  `form_text_area` varchar(255) DEFAULT NULL,
  `form_html_editor` varchar(1000) DEFAULT NULL,
  `form_number_short` smallint(6) DEFAULT NULL,
  `form_number_short_base` smallint(6) DEFAULT NULL,
  `form_number_integer` int(11) DEFAULT NULL,
  `form_number_int_base` int(11) DEFAULT NULL,
  `form_number_long` bigint(20) DEFAULT NULL,
  `form_number_long_base` bigint(20) DEFAULT NULL,
  `form_number_float` float DEFAULT NULL,
  `form_number_float_base` double DEFAULT NULL,
  `form_number_double` double DEFAULT NULL,
  `form_number_double_base` double DEFAULT NULL,
  `form_boolean` int(11) DEFAULT NULL,
  `is_form_boolean_flag` int(11) DEFAULT NULL,
  `form_boolean_base` int(11) DEFAULT NULL,
  `form_date_string` varchar(255) DEFAULT NULL,
  `form_date_date` date DEFAULT NULL,
  `form_datetime_ymdhm` datetime DEFAULT NULL,
  `form_datetime_string` varchar(255) DEFAULT NULL,
  `form_datetime_date` datetime DEFAULT NULL,
  `form_radio` varchar(255) DEFAULT NULL,
  `form_checkbox` varchar(255) DEFAULT NULL,
  `form_combo_box_json` varchar(255) DEFAULT NULL,
  `form_combo_box_sql` varchar(255) DEFAULT NULL,
  `form_tag_json` varchar(255) DEFAULT NULL,
  `form_tag_sql` varchar(255) DEFAULT NULL,
  `form_combo_box_cascade_a` varchar(255) DEFAULT NULL,
  `form_combo_box_cascade_b` varchar(255) DEFAULT NULL,
  `form_combo_box_cascade1` varchar(255) DEFAULT NULL,
  `form_combo_box_cascade2` varchar(255) DEFAULT NULL,
  `form_combo_box_cascade3` varchar(255) DEFAULT NULL,
  `form_combo_box_tree1` varchar(255) DEFAULT NULL,
  `form_combo_box_tree2` varchar(255) DEFAULT NULL,
  `form_combo_box_tree3` varchar(255) DEFAULT NULL,
  `form_combo_box_tree4` varchar(255) DEFAULT NULL,
  `form_combo_box_tree5` varchar(255) DEFAULT NULL,
  `form_combo_box_tree6` varchar(255) DEFAULT NULL,
  `form_display` varchar(255) DEFAULT NULL,
  `form_file1_url` varchar(255) DEFAULT NULL,
  `form_file1_orig_file_name` varchar(255) DEFAULT NULL,
  `form_file2_url` varchar(255) DEFAULT NULL,
  `form_file2_orig_file_name` varchar(255) DEFAULT NULL,
  `form_file3_url` varchar(255) DEFAULT NULL,
  `form_file3_orig_file_name` varchar(255) DEFAULT NULL,
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of example_bean
-- ----------------------------
INSERT INTO `example_bean` VALUES ('4', 'hidden隐藏域值', 'admin', 'dc483e80a7a0bd9ef71d8cf973673924', '文本域-示例数据', '富文本编辑器很强大！攒！', '8', '0', '9', '0', '10', '0', '209.58', '0', '309.58', '0', '1', '0', '0', '2017-01-01', '2018-04-21', '2018-04-21 19:12:00', '2017-01-01 09:02:03', '2017-01-01 09:01:01', '90', 'running,swimming', '90', '120000', '80', null, '140200', '140202', '130000', '130100', '130102', 'A,A2,A3', 'A2,C', '140100', '140000,140200,140201,140202,140222', '120101,120102,120103,140000', '130100', '管理员', '\\atta\\upload\\doc\\201804211913491594.doc', 'oracleNote.doc', '\\atta\\upload\\txt\\2018042119134918641.txt', '正则.txt', null, null, '2018-04-21 19:13:49', '2018-04-21 19:14:46');

-- ----------------------------
-- Table structure for example_bean_form_val
-- ----------------------------
DROP TABLE IF EXISTS `example_bean_form_val`;
CREATE TABLE `example_bean_form_val` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `alpha` varchar(255) DEFAULT NULL,
  `alphanum` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `letter` varchar(255) DEFAULT NULL,
  `upper_case` varchar(255) DEFAULT NULL,
  `lower_case` varchar(255) DEFAULT NULL,
  `letter_num` varchar(255) DEFAULT NULL,
  `letter_num_underline` varchar(255) DEFAULT NULL,
  `chinese_letter_num` varchar(255) DEFAULT NULL,
  `chinese_letter_num_underline` varchar(255) DEFAULT NULL,
  `chinese` varchar(255) DEFAULT NULL,
  `character50` varchar(255) DEFAULT NULL,
  `email_platform` varchar(255) DEFAULT NULL,
  `mobile_no` varchar(255) DEFAULT NULL,
  `fixed_phone_no` varchar(255) DEFAULT NULL,
  `phone_no` varchar(255) DEFAULT NULL,
  `id_number15` varchar(255) DEFAULT NULL,
  `id_number18` varchar(255) DEFAULT NULL,
  `id_number` varchar(255) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `date_ymd` varchar(255) DEFAULT NULL,
  `qq` varchar(255) DEFAULT NULL,
  `post_code` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `account_number` varchar(255) DEFAULT NULL,
  `general_password` varchar(255) DEFAULT NULL,
  `strong_password` varchar(255) DEFAULT NULL,
  `regex` varchar(255) DEFAULT NULL,
  `regex_vtype` varchar(255) DEFAULT NULL,
  `val_text` varchar(255) DEFAULT NULL,
  `val_text_param` varchar(255) DEFAULT NULL,
  `val_password` varchar(255) DEFAULT NULL,
  `val_password_param` varchar(255) DEFAULT NULL,
  `val_text_area` varchar(255) DEFAULT NULL,
  `val_text_area_param` varchar(255) DEFAULT NULL,
  `val_number` double DEFAULT NULL,
  `val_number_param` double DEFAULT NULL,
  `val_tag` varchar(255) DEFAULT NULL,
  `val_tag_param` varchar(255) DEFAULT NULL,
  `val_date` varchar(255) DEFAULT NULL,
  `val_date_param` varchar(255) DEFAULT NULL,
  `val_combo_box` varchar(255) DEFAULT NULL,
  `val_combo_box_param` varchar(255) DEFAULT NULL,
  `val_tree` varchar(255) DEFAULT NULL,
  `val_tree_param` varchar(255) DEFAULT NULL,
  `val_file_url` varchar(255) DEFAULT NULL,
  `val_file_orig_file_name` varchar(255) DEFAULT NULL,
  `val_file_param_url` varchar(255) DEFAULT NULL,
  `val_file_param_orig_file_name` varchar(255) DEFAULT NULL,
  `regex_vtype_validator` varchar(255) DEFAULT NULL,
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of example_bean_form_val
-- ----------------------------

-- ----------------------------
-- Table structure for example_bean_tree
-- ----------------------------
DROP TABLE IF EXISTS `example_bean_tree`;
CREATE TABLE `example_bean_tree` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=140729 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of example_bean_tree
-- ----------------------------
INSERT INTO `example_bean_tree` VALUES ('110000', '北京市', '-1');
INSERT INTO `example_bean_tree` VALUES ('110100', '市辖区', '110000');
INSERT INTO `example_bean_tree` VALUES ('110101', '东城区', '110000');
INSERT INTO `example_bean_tree` VALUES ('110102', '西城区', '110000');
INSERT INTO `example_bean_tree` VALUES ('110105', '朝阳区', '110000');
INSERT INTO `example_bean_tree` VALUES ('110106', '丰台区', '110000');
INSERT INTO `example_bean_tree` VALUES ('110107', '石景山区', '110000');
INSERT INTO `example_bean_tree` VALUES ('110108', '海淀区', '110000');
INSERT INTO `example_bean_tree` VALUES ('110109', '门头沟区', '110000');
INSERT INTO `example_bean_tree` VALUES ('110111', '房山区', '110000');
INSERT INTO `example_bean_tree` VALUES ('110112', '通州区', '110000');
INSERT INTO `example_bean_tree` VALUES ('110113', '顺义区', '110000');
INSERT INTO `example_bean_tree` VALUES ('110114', '昌平区', '110000');
INSERT INTO `example_bean_tree` VALUES ('110115', '大兴区', '110000');
INSERT INTO `example_bean_tree` VALUES ('110116', '怀柔区', '110000');
INSERT INTO `example_bean_tree` VALUES ('110117', '平谷区', '110000');
INSERT INTO `example_bean_tree` VALUES ('110118', '密云区', '110000');
INSERT INTO `example_bean_tree` VALUES ('110119', '延庆区', '110000');
INSERT INTO `example_bean_tree` VALUES ('120000', '天津市', '-1');
INSERT INTO `example_bean_tree` VALUES ('120100', '市辖区', '120000');
INSERT INTO `example_bean_tree` VALUES ('120101', '和平区', '120000');
INSERT INTO `example_bean_tree` VALUES ('120102', '河东区', '120000');
INSERT INTO `example_bean_tree` VALUES ('120103', '河西区', '120000');
INSERT INTO `example_bean_tree` VALUES ('130000', '河北省', '-1');
INSERT INTO `example_bean_tree` VALUES ('130100', '石家庄市', '130000');
INSERT INTO `example_bean_tree` VALUES ('130101', '市辖区', '130100');
INSERT INTO `example_bean_tree` VALUES ('130102', '长安区', '130100');
INSERT INTO `example_bean_tree` VALUES ('130104', '桥西区', '130100');
INSERT INTO `example_bean_tree` VALUES ('130105', '新华区', '130100');
INSERT INTO `example_bean_tree` VALUES ('130200', '唐山市', '130000');
INSERT INTO `example_bean_tree` VALUES ('130201', '市辖区', '130200');
INSERT INTO `example_bean_tree` VALUES ('130202', '路南区', '130200');
INSERT INTO `example_bean_tree` VALUES ('130203', '路北区', '130200');
INSERT INTO `example_bean_tree` VALUES ('140000', '山西省', '-1');
INSERT INTO `example_bean_tree` VALUES ('140100', '太原市', '140000');
INSERT INTO `example_bean_tree` VALUES ('140101', '市辖区', '140100');
INSERT INTO `example_bean_tree` VALUES ('140105', '小店区', '140100');
INSERT INTO `example_bean_tree` VALUES ('140106', '迎泽区', '140100');
INSERT INTO `example_bean_tree` VALUES ('140107', '杏花岭区', '140100');
INSERT INTO `example_bean_tree` VALUES ('140200', '大同市', '140000');
INSERT INTO `example_bean_tree` VALUES ('140201', '市辖区', '140200');
INSERT INTO `example_bean_tree` VALUES ('140202', '城区', '140200');
INSERT INTO `example_bean_tree` VALUES ('140222', '天镇县', '140200');
INSERT INTO `example_bean_tree` VALUES ('140400', '长治市', '140000');
INSERT INTO `example_bean_tree` VALUES ('140401', '市辖区', '140400');
INSERT INTO `example_bean_tree` VALUES ('140402', '城区', '140400');
INSERT INTO `example_bean_tree` VALUES ('140411', '郊区', '140400');
INSERT INTO `example_bean_tree` VALUES ('140421', '长治县', '140400');
INSERT INTO `example_bean_tree` VALUES ('140423', '襄垣县', '140400');
INSERT INTO `example_bean_tree` VALUES ('140424', '屯留县', '140400');
INSERT INTO `example_bean_tree` VALUES ('140425', '平顺县', '140400');
INSERT INTO `example_bean_tree` VALUES ('140426', '黎城县', '140400');
INSERT INTO `example_bean_tree` VALUES ('140427', '壶关县', '140400');
INSERT INTO `example_bean_tree` VALUES ('140428', '长子县', '140400');
INSERT INTO `example_bean_tree` VALUES ('140429', '武乡县', '140400');
INSERT INTO `example_bean_tree` VALUES ('140430', '沁县', '140400');
INSERT INTO `example_bean_tree` VALUES ('140431', '沁源县', '140400');
INSERT INTO `example_bean_tree` VALUES ('140481', '潞城市', '140400');
INSERT INTO `example_bean_tree` VALUES ('140700', '晋中市', '140000');
INSERT INTO `example_bean_tree` VALUES ('140701', '市辖区', '140700');
INSERT INTO `example_bean_tree` VALUES ('140702', '榆次区', '140700');
INSERT INTO `example_bean_tree` VALUES ('140727', '祁县', '140700');
INSERT INTO `example_bean_tree` VALUES ('140728', '平遥县', '140700');

-- ----------------------------
-- Table structure for sys_en_configuration
-- ----------------------------
DROP TABLE IF EXISTS `sys_en_configuration`;
CREATE TABLE `sys_en_configuration` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `conf_name` varchar(100) NOT NULL,
  `conf_key` varchar(100) DEFAULT NULL,
  `conf_value` varchar(500) DEFAULT NULL,
  `note` varchar(500) DEFAULT NULL,
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `fk_parent_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `conf_key` (`conf_key`),
  KEY `fk_parent_id` (`fk_parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_en_configuration
-- ----------------------------
INSERT INTO `sys_en_configuration` VALUES ('1', '管理端首页配置信息', 'manageIndexKey', 'manageIndexVal', '', '2018-04-22 13:40:39', '2018-04-22 13:40:39', '-1');
INSERT INTO `sys_en_configuration` VALUES ('2', '管理端首页欢迎语', 'manageIndexTopTitle', '欢迎使用数据管理系统', '', '2018-04-22 13:42:32', '2018-04-22 13:42:32', '1');
INSERT INTO `sys_en_configuration` VALUES ('3', '管理端首页底部版权', 'manageIndexBottomCopyright', 'Copyright 2018 ZhaoJianGuo. AllRightsReserved.', '', '2018-04-22 13:43:07', '2018-04-22 13:43:07', '1');

-- ----------------------------
-- Table structure for sys_en_function
-- ----------------------------
DROP TABLE IF EXISTS `sys_en_function`;
CREATE TABLE `sys_en_function` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_parent_id` bigint(20) DEFAULT NULL,
  `fk_sys_en_menu_id` bigint(20) DEFAULT NULL,
  `func_identify` varchar(100) NOT NULL,
  `func_name` varchar(50) NOT NULL,
  `func_code` varchar(100) NOT NULL,
  `is_leaf` smallint(6) DEFAULT NULL,
  `is_expanded` smallint(6) DEFAULT NULL,
  `order_code` varchar(50) DEFAULT NULL,
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_parent_id` (`fk_parent_id`),
  KEY `fk_sys_en_menu_id` (`fk_sys_en_menu_id`),
  CONSTRAINT `sys_en_function_ibfk_2` FOREIGN KEY (`fk_sys_en_menu_id`) REFERENCES `sys_en_menu` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_en_function
-- ----------------------------
INSERT INTO `sys_en_function` VALUES ('8', '-1', null, '', '系统管理', '', '0', '1', '002', '2017-09-17 16:55:04', '2017-09-17 16:55:04');
INSERT INTO `sys_en_function` VALUES ('9', '-1', null, '', '框架示例', '', '0', '1', '001', '2017-09-17 16:55:54', '2017-09-17 16:55:54');
INSERT INTO `sys_en_function` VALUES ('11', '9', '17', '', '表单控件示例', 'formExample', '0', '1', '001', '2017-10-11 12:50:43', '2017-10-11 12:50:43');
INSERT INTO `sys_en_function` VALUES ('12', '11', '17', '', '添加', 'baseAdd', '1', '1', '001', '2017-10-11 12:53:41', '2017-10-11 12:53:41');
INSERT INTO `sys_en_function` VALUES ('13', '11', '17', '', '编辑', 'baseEdit', '1', '1', '002', '2017-10-11 12:54:27', '2017-10-11 12:54:27');
INSERT INTO `sys_en_function` VALUES ('14', '11', '17', '', '多选删除', 'baseDelete', '1', '1', '003', '2017-10-11 12:55:14', '2017-10-11 12:56:41');
INSERT INTO `sys_en_function` VALUES ('15', '11', '17', '', '基本ajax异步请求', 'exampleBeanOpeSingDataAjaxReq', '1', '1', '504', '2017-10-11 12:56:21', '2017-10-11 13:04:52');
INSERT INTO `sys_en_function` VALUES ('16', '11', '17', '', '自定义操作(带参)', 'exampleBeanUserDefineOpe1', '1', '1', '505', '2017-10-11 13:00:16', '2017-10-11 13:04:46');
INSERT INTO `sys_en_function` VALUES ('17', '11', '17', '', '自定义操作(不带参)', 'exampleBeanUserDefineOpe2', '1', '1', '506', '2017-10-11 13:00:49', '2017-10-11 13:04:40');
INSERT INTO `sys_en_function` VALUES ('18', '11', '17', '', '打开新tab(带参，自定义页面)', 'openNewTab1', '1', '1', '507', '2017-10-11 13:01:25', '2017-10-11 13:04:34');
INSERT INTO `sys_en_function` VALUES ('19', '11', '17', '', '打开新tab(不带参，自定义页面)', 'openNewTab2', '1', '1', '508', '2017-10-11 13:02:08', '2017-10-11 13:04:25');
INSERT INTO `sys_en_function` VALUES ('20', '11', '17', '', '打开新tab(框架列表页面)', 'openNewTab3', '1', '1', '509', '2017-10-11 13:02:44', '2017-10-11 13:04:18');
INSERT INTO `sys_en_function` VALUES ('21', '11', '17', '', '打开浏览器窗口(带参)', 'openNewBrowserWindow1', '1', '1', '510', '2017-10-11 13:03:19', '2017-10-11 13:04:09');
INSERT INTO `sys_en_function` VALUES ('22', '11', '17', '', '打开浏览器窗口(不带参)', 'openNewBrowserWindow2', '1', '1', '511', '2017-10-11 13:03:57', '2017-10-11 13:03:57');
INSERT INTO `sys_en_function` VALUES ('23', '11', '17', '', '基本ajax请求', 'menu1', '1', '1', '004', '2017-10-11 13:05:41', '2017-10-11 13:05:41');
INSERT INTO `sys_en_function` VALUES ('24', '11', '17', '', '自定义操作', 'menu5', '1', '1', '008', '2017-10-11 13:06:26', '2017-10-11 13:08:58');
INSERT INTO `sys_en_function` VALUES ('25', '11', '17', '', '修改多个字段的值', 'menu2', '1', '1', '005', '2017-10-11 13:07:12', '2017-10-11 13:09:10');
INSERT INTO `sys_en_function` VALUES ('26', '11', '17', '', '修改Double值', 'menu3', '1', '1', '006', '2017-10-11 13:07:49', '2017-10-11 13:09:18');
INSERT INTO `sys_en_function` VALUES ('27', '11', '17', '', '修改TextArea值', 'menu4', '1', '1', '007', '2017-10-11 13:08:16', '2017-10-11 13:09:27');
INSERT INTO `sys_en_function` VALUES ('28', '11', '17', '', '基本ajax请求(不用勾选数据)', 'menu1_a', '1', '1', '004', '2018-04-14 14:19:40', '2018-04-14 14:19:40');

-- ----------------------------
-- Table structure for sys_en_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_en_menu`;
CREATE TABLE `sys_en_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_parent_id` bigint(20) DEFAULT NULL COMMENT '父id',
  `menu_identify` varchar(100) DEFAULT NULL COMMENT '菜单唯一字符串标识符',
  `text` varchar(50) NOT NULL COMMENT '菜单名称',
  `link` varchar(500) DEFAULT NULL COMMENT '菜单链接地址',
  `qtip` varchar(100) DEFAULT NULL COMMENT '菜单提示',
  `is_leaf` smallint(6) NOT NULL COMMENT '是否叶子节点(0否,1是)',
  `is_expanded` smallint(6) DEFAULT NULL COMMENT '是否展开子节点',
  `is_self_url` smallint(6) NOT NULL DEFAULT '1' COMMENT '是否为平台内部链接地址(1：是，0：否)',
  `order_code` varchar(50) DEFAULT NULL,
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_sem_fk_parent_id` (`fk_parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_en_menu
-- ----------------------------
INSERT INTO `sys_en_menu` VALUES ('1', '-1', null, '根结点', '', 'root', '0', '1', '0', '0', null, null);
INSERT INTO `sys_en_menu` VALUES ('13', '1', null, '框架示例', null, '高级用户使用', '0', '1', '1', '001', null, null);
INSERT INTO `sys_en_menu` VALUES ('16', '13', 'ExampleBeanFormVal', '表单验证示例', 'exampleBeanFormVal/search.do', '表单验证示例', '1', '1', '1', '002', null, null);
INSERT INTO `sys_en_menu` VALUES ('17', '13', 'ExampleBeanList', '表单控件示例', 'exampleBean/search.do', '表单控件示例', '1', '1', '1', '003', null, null);
INSERT INTO `sys_en_menu` VALUES ('20', '1', 'systemManage', '系统管理', '', '系统管理', '0', '1', '1', '000', null, '2017-09-27 22:03:10');
INSERT INTO `sys_en_menu` VALUES ('21', '20', 'SysFunctionList', '功能配置管理', 'sysFunction/search.do', '功能配置管理', '1', '1', '1', '06', null, '2018-04-21 14:10:50');
INSERT INTO `sys_en_menu` VALUES ('22', '20', 'BaseConstantList', '常量管理', 'baseConstant/search.do', '常量管理', '1', '1', '1', '01', null, '2018-04-21 14:11:46');
INSERT INTO `sys_en_menu` VALUES ('23', '20', 'SysEnConfList', '系统配置', 'sysEnConf/search.do', '系统配置', '1', '1', '1', '02', null, '2018-04-21 14:11:33');
INSERT INTO `sys_en_menu` VALUES ('24', '20', 'SysMenuList', '菜单配置管理', 'sysMenu/search.do', '菜单配置管理', '1', '1', '1', '05', null, '2018-04-21 14:11:19');
INSERT INTO `sys_en_menu` VALUES ('32', '20', 'SysRoleList', '角色管理', 'sysRole/search.do', '', '1', '1', '1', '03', '2017-09-20 22:38:17', '2018-04-21 14:12:03');
INSERT INTO `sys_en_menu` VALUES ('33', '20', 'SysUserList', '用户管理', 'sysUser/search.do', '', '1', '1', '1', '04', '2017-09-28 22:02:59', '2018-04-21 14:12:15');

-- ----------------------------
-- Table structure for sys_en_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_en_role`;
CREATE TABLE `sys_en_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_parent_id` bigint(20) DEFAULT NULL,
  `role_code` varchar(100) NOT NULL,
  `role_name` varchar(50) NOT NULL,
  `role_status` varchar(2) NOT NULL,
  `remark` varchar(500) DEFAULT NULL,
  `order_code` varchar(100) DEFAULT NULL,
  `add_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_parent_id` (`fk_parent_id`),
  KEY `role_code` (`role_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_en_role
-- ----------------------------
INSERT INTO `sys_en_role` VALUES ('1', '-1', 'superAdmin', '超级管理员', '1', '超级管理员', '001', '2016-10-30 23:20:53', '2018-04-14 14:23:25');

-- ----------------------------
-- Table structure for sys_en_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_en_user`;
CREATE TABLE `sys_en_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) NOT NULL,
  `login_name` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `user_status` varchar(2) DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_name` (`login_name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_en_user
-- ----------------------------
INSERT INTO `sys_en_user` VALUES ('1', '系统管理员', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '1', '', null, '2018-04-21 18:16:30');

-- ----------------------------
-- Table structure for sys_re_menu_func
-- ----------------------------
DROP TABLE IF EXISTS `sys_re_menu_func`;
CREATE TABLE `sys_re_menu_func` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_menu_id` bigint(20) NOT NULL,
  `fk_func_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_srmf_fk_menu_id` (`fk_menu_id`),
  KEY `fk_srmf_fk_func_id` (`fk_func_id`),
  CONSTRAINT `fk_srmf_fk_func_id` FOREIGN KEY (`fk_func_id`) REFERENCES `sys_en_function` (`id`),
  CONSTRAINT `fk_srmf_fk_menu_id` FOREIGN KEY (`fk_menu_id`) REFERENCES `sys_en_menu` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_re_menu_func
-- ----------------------------

-- ----------------------------
-- Table structure for sys_re_role_func
-- ----------------------------
DROP TABLE IF EXISTS `sys_re_role_func`;
CREATE TABLE `sys_re_role_func` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_role_id` bigint(20) NOT NULL,
  `fk_func_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_srrf_fk_role_id` (`fk_role_id`),
  KEY `fk_srrf_fk_func_id` (`fk_func_id`),
  CONSTRAINT `fk_srrf_fk_role_id` FOREIGN KEY (`fk_role_id`) REFERENCES `sys_en_role` (`id`),
  CONSTRAINT `sys_re_role_func_ibfk_1` FOREIGN KEY (`fk_func_id`) REFERENCES `sys_en_function` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_re_role_func
-- ----------------------------
INSERT INTO `sys_re_role_func` VALUES ('43', '1', '9');
INSERT INTO `sys_re_role_func` VALUES ('44', '1', '11');
INSERT INTO `sys_re_role_func` VALUES ('45', '1', '12');
INSERT INTO `sys_re_role_func` VALUES ('46', '1', '13');
INSERT INTO `sys_re_role_func` VALUES ('47', '1', '14');
INSERT INTO `sys_re_role_func` VALUES ('48', '1', '23');
INSERT INTO `sys_re_role_func` VALUES ('49', '1', '25');
INSERT INTO `sys_re_role_func` VALUES ('50', '1', '26');
INSERT INTO `sys_re_role_func` VALUES ('51', '1', '27');
INSERT INTO `sys_re_role_func` VALUES ('52', '1', '24');
INSERT INTO `sys_re_role_func` VALUES ('53', '1', '15');
INSERT INTO `sys_re_role_func` VALUES ('54', '1', '16');
INSERT INTO `sys_re_role_func` VALUES ('55', '1', '17');
INSERT INTO `sys_re_role_func` VALUES ('56', '1', '18');
INSERT INTO `sys_re_role_func` VALUES ('57', '1', '19');
INSERT INTO `sys_re_role_func` VALUES ('58', '1', '20');
INSERT INTO `sys_re_role_func` VALUES ('59', '1', '21');
INSERT INTO `sys_re_role_func` VALUES ('60', '1', '22');

-- ----------------------------
-- Table structure for sys_re_role_func_exclude
-- ----------------------------
DROP TABLE IF EXISTS `sys_re_role_func_exclude`;
CREATE TABLE `sys_re_role_func_exclude` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_role_id` bigint(20) NOT NULL,
  `fk_func_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_srrfe_fk_func_id` (`fk_func_id`),
  KEY `fk_srrfe_fk_role_id` (`fk_role_id`),
  CONSTRAINT `fk_srrfe_fk_func_id` FOREIGN KEY (`fk_func_id`) REFERENCES `sys_en_function` (`id`),
  CONSTRAINT `fk_srrfe_fk_role_id` FOREIGN KEY (`fk_role_id`) REFERENCES `sys_en_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_re_role_func_exclude
-- ----------------------------

-- ----------------------------
-- Table structure for sys_re_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_re_role_menu`;
CREATE TABLE `sys_re_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_role_id` bigint(20) NOT NULL,
  `fk_menu_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_srrm_fk_role_id` (`fk_role_id`),
  KEY `fk_srrm_fk_menu_id` (`fk_menu_id`),
  CONSTRAINT `fk_srrm_fk_menu_id` FOREIGN KEY (`fk_menu_id`) REFERENCES `sys_en_menu` (`id`),
  CONSTRAINT `fk_srrm_fk_role_id` FOREIGN KEY (`fk_role_id`) REFERENCES `sys_en_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=129 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_re_role_menu
-- ----------------------------
INSERT INTO `sys_re_role_menu` VALUES ('118', '1', '1');
INSERT INTO `sys_re_role_menu` VALUES ('119', '1', '13');
INSERT INTO `sys_re_role_menu` VALUES ('120', '1', '16');
INSERT INTO `sys_re_role_menu` VALUES ('121', '1', '17');
INSERT INTO `sys_re_role_menu` VALUES ('122', '1', '20');
INSERT INTO `sys_re_role_menu` VALUES ('123', '1', '21');
INSERT INTO `sys_re_role_menu` VALUES ('124', '1', '22');
INSERT INTO `sys_re_role_menu` VALUES ('125', '1', '23');
INSERT INTO `sys_re_role_menu` VALUES ('126', '1', '24');
INSERT INTO `sys_re_role_menu` VALUES ('127', '1', '32');
INSERT INTO `sys_re_role_menu` VALUES ('128', '1', '33');

-- ----------------------------
-- Table structure for sys_re_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_re_user_role`;
CREATE TABLE `sys_re_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_role_id` bigint(20) NOT NULL,
  `fk_user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_srru_fk_role_id` (`fk_role_id`),
  KEY `fk_srru_fk_user_id` (`fk_user_id`),
  CONSTRAINT `fk_srru_fk_role_id` FOREIGN KEY (`fk_role_id`) REFERENCES `sys_en_role` (`id`),
  CONSTRAINT `fk_srru_fk_user_id` FOREIGN KEY (`fk_user_id`) REFERENCES `sys_en_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_re_user_role
-- ----------------------------
INSERT INTO `sys_re_user_role` VALUES ('80', '1', '1');
