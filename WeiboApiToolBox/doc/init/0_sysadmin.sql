INSERT INTO `apitoolbox`.`tcategory` (`vc2catename`, `vc2catedesc`, `vc2catehref`, `vc2cateimg`, `numenable`) VALUES ('sysadmin', '系统管理', 'NONE', '', '1');
INSERT INTO `apitoolbox`.`tmenuitem` (`vc2itemname`, `vc2itemurl`, `vc2itemdesc`, `vc2itemimg`, `numcateid`, `numenable`) VALUES ('usermana', '/admin/usermanage.zul', '用户管理', '', '1', '1');
INSERT INTO `apitoolbox`.`tmenuitem` (`vc2itemname`, `vc2itemurl`, `vc2itemdesc`, `vc2itemimg`, `numcateid`, `numenable`) VALUES ('menumana', '/admin/menumanage.zul', '菜单管理', '', '1', '1');
INSERT INTO `apitoolbox`.`tmenuitem` (`vc2itemname`, `vc2itemurl`, `vc2itemdesc`, `vc2itemimg`, `numcateid`, `numenable`) VALUES ('groupmana', '/admin/groupmanage.zul', '权限组管理', '', '1', '1');
INSERT INTO `apitoolbox`.`tgroup` (`vc2groupname`, `vc2groupdesc`, `numenable`) VALUES ('admingroup', '管理员组', '1');
INSERT INTO `apitoolbox`.`tuser` (`numuserid`, `vc2email`, `vc2username`, `vc2realname`, `vc2password`, `numenable`, `numsupervisor`) VALUES ('1', 'admin@staff.sina.com.cn', 'admin', NULL, '21232f297a57a5a743894a0e4a801fc3', '1', '1');




