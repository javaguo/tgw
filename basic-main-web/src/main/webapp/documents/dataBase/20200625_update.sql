-- 增加Redis示例菜单
INSERT INTO sys_en_menu (id, fk_parent_id, menu_identify, text, link, qtip, is_leaf, is_expanded, is_self_url, order_num, add_time, update_time)
VALUES ('58', '13', 'ExampleRedisList', 'Redis示例', 'exampleRedis/search.do', '', '1', '1', '1', '5', '2020-06-07 16:53:07', '2020-06-07 17:03:03');

-- 增加Redis示例菜单与角色关系
INSERT INTO sys_re_role_menu (id, fk_role_id, fk_menu_id) VALUES ('908', '1', '58');

-- 增加dubbo示例菜单
INSERT INTO sys_en_menu (id, fk_parent_id, menu_identify, text, link, qtip, is_leaf, is_expanded, is_self_url, order_num, add_time, update_time)
VALUES ('59', '13', 'ExampleDubboList', 'dubbo示例', 'consumer/search.do', '', '1', '1', '1', '6', '2020-06-25 19:42:36', '2020-06-25 19:42:36');

-- 增加dubbo示例菜单与角色关系
INSERT INTO sys_re_role_menu (id, fk_role_id, fk_menu_id) VALUES ('909', '1', '58');