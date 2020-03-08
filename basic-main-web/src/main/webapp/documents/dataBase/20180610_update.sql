-- 增加echart示例
INSERT INTO sys_en_menu(id, fk_parent_id, menu_identify, text, link, qtip, is_leaf, is_expanded, is_self_url, order_num, add_time, update_time)
 VALUES ('54', '13', 'ExampleEcharts', 'echarts柱状图示例', 'exampleEcharts/echartBar.do', '', '1', '1', '1', '3', '2018-06-10 12:32:10', '2018-06-10 12:35:49');

-- 增加SearchModel示例
INSERT INTO sys_en_menu (id, fk_parent_id, menu_identify, text, link, qtip, is_leaf, is_expanded, is_self_url, order_num, add_time, update_time)
VALUES ('56', '13', 'ExampleSearchModel', 'searchModel示例', 'exampleSearchModel/searchModel.do', '', '1', '1', '1', '4', '2018-06-10 17:27:18', '2018-06-10 17:27:18');
