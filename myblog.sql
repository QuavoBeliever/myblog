# Host: localhost  (Version 5.5.6-rc)
# Date: 2020-08-08 15:10:50
# Generator: MySQL-Front 6.1  (Build 1.26)


#
# Structure for table "blog"
#

DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `bid` varchar(200) NOT NULL COMMENT '博客id',
  `title` varchar(200) NOT NULL COMMENT '博客标题',
  `content` longtext NOT NULL COMMENT '博客内容',
  `sort` int(1) NOT NULL DEFAULT '0' COMMENT '排序0普通 1置顶',
  `views` int(10) NOT NULL DEFAULT '0' COMMENT '浏览量',
  `author_id` varchar(200) NOT NULL COMMENT '作者id',
  `author_name` varchar(200) NOT NULL COMMENT '作者名',
  `author_avatar` varchar(200) NOT NULL COMMENT '作者头像',
  `category_id` int(10) NOT NULL COMMENT '问题分类id',
  `category_name` varchar(50) NOT NULL COMMENT '问题分类名称',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_update` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

#
# Data for table "blog"
#

INSERT INTO `blog` VALUES (1,'05d8afc753e4471b95c68dc67486f5e1','你好',' spring111',0,6,'6aba4abde7c7499597aa91e7f154ca2c','111','/images/avatar/3.jpg',1,'java','2020-08-06 16:28:51','2020-08-06 16:35:16'),(2,'c2eb77724a92454baf4ff9d3dd50f11f','222的blog',' 222',0,0,'6aba4abde7c7499597aa91e7f154ca2c','111','/images/avatar/3.jpg',3,'php','2020-08-08 09:33:20','2020-08-08 09:33:20'),(3,'05e7d056c1d9485aa9b1b5c37e8ca79d','222的blog',' 111',0,0,'284f66df13624600b1e1af9c5b657c90','222','/images/avatar/1.jpg',3,'php','2020-08-08 14:46:54','2020-08-08 14:46:54');

#
# Structure for table "blog_category"
#

DROP TABLE IF EXISTS `blog_category`;
CREATE TABLE `blog_category` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `category` varchar(50) NOT NULL COMMENT '博客分类',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

#
# Data for table "blog_category"
#

INSERT INTO `blog_category` VALUES (1,'java'),(2,'c++'),(3,'php'),(4,'c#');

#
# Structure for table "comment"
#

DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `comment_id` varchar(200) NOT NULL COMMENT '评论唯一id',
  `topic_category` int(1) NOT NULL COMMENT '1博客2问答',
  `topic_id` varchar(200) NOT NULL COMMENT '评论主题id',
  `user_id` varchar(200) NOT NULL COMMENT '评论者id',
  `user_name` varchar(200) NOT NULL COMMENT '评论者昵称',
  `user_avatar` varchar(500) NOT NULL COMMENT '评论者头像',
  `content` longtext NOT NULL COMMENT '评论内容',
  `gmt_create` datetime NOT NULL COMMENT '评论创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

#
# Data for table "comment"
#

INSERT INTO `comment` VALUES (2,'4680ce0d8e224ba6bbf443591c1f524c',2,'0c79378e7b904311a8f72897a1d6a632','6aba4abde7c7499597aa91e7f154ca2c','111','/images/avatar/3.jpg','好问题\r\n','2020-08-07 10:35:12'),(3,'558a4ed6eddc400b9e29f750113de61d',2,'f9c174714a8e4826a4224d3b53e7bfd9','6aba4abde7c7499597aa91e7f154ca2c','111','/images/avatar/3.jpg','啊','2020-08-07 15:45:29');

#
# Structure for table "download"
#

DROP TABLE IF EXISTS `download`;
CREATE TABLE `download` (
  `dname` varchar(100) NOT NULL COMMENT '资源名',
  `ddesc` varchar(500) NOT NULL COMMENT '资源链接',
  `dcode` varchar(50) NOT NULL COMMENT '提取码'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "download"
#


#
# Structure for table "invite"
#

DROP TABLE IF EXISTS `invite`;
CREATE TABLE `invite` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `code` varchar(200) NOT NULL COMMENT '邀请码',
  `uid` varchar(200) DEFAULT NULL COMMENT '用户id',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '0未使用 1使用',
  `active_time` datetime DEFAULT NULL COMMENT '激活时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

#
# Data for table "invite"
#

INSERT INTO `invite` VALUES (1,'111','6aba4abde7c7499597aa91e7f154ca2c',1,'2020-08-06 10:29:07','0000-00-00 00:00:00'),(2,'222','284f66df13624600b1e1af9c5b657c90',1,'2020-08-06 10:39:58','0000-00-00 00:00:00'),(3,'123','e82b4db91a5644f1aeb47ca6401b2ec2',1,'2020-08-07 16:20:02','0000-00-00 00:00:00');

#
# Structure for table "question"
#

DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `qid` varchar(200) NOT NULL COMMENT '问题id',
  `title` varchar(200) NOT NULL COMMENT '问题标题',
  `content` longtext NOT NULL COMMENT '问题内容',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '0未解决 1解决',
  `sort` int(1) NOT NULL DEFAULT '0' COMMENT '0普通 1置顶',
  `views` int(10) NOT NULL DEFAULT '0' COMMENT '浏览量',
  `author_id` varchar(200) NOT NULL COMMENT '作者id',
  `author_name` varchar(200) NOT NULL COMMENT '作者名字',
  `author_avatar` varchar(500) NOT NULL COMMENT '作者头像',
  `category_id` int(10) NOT NULL COMMENT '问题分类id',
  `category_name` varchar(50) NOT NULL COMMENT '问题分类名称',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_update` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

#
# Data for table "question"
#

INSERT INTO `question` VALUES (2,'f9c174714a8e4826a4224d3b53e7bfd9','spring是啥',' 111',1,0,4,'6aba4abde7c7499597aa91e7f154ca2c','111','/images/avatar/3.jpg',3,'springmvc','2020-08-07 15:45:20','2020-08-07 15:45:20');

#
# Structure for table "question_category"
#

DROP TABLE IF EXISTS `question_category`;
CREATE TABLE `question_category` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `category` varchar(50) NOT NULL COMMENT '问题分类',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

#
# Data for table "question_category"
#

INSERT INTO `question_category` VALUES (1,'spring'),(2,'springboot'),(3,'springmvc');

#
# Structure for table "say"
#

DROP TABLE IF EXISTS `say`;
CREATE TABLE `say` (
  `id` varchar(200) NOT NULL COMMENT '唯一id',
  `title` varchar(200) NOT NULL COMMENT '标题',
  `content` varchar(5000) NOT NULL COMMENT '内容',
  `gmt_create` datetime NOT NULL COMMENT '时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "say"
#

INSERT INTO `say` VALUES ('4853e314fc0044f69c407a83e91cb0c1','我是管理员',' 管理','2020-08-07 16:23:30'),('e0ee35aa24a34f05af89f09738e9a0d0','新公告','大家好 ','2020-08-07 16:25:10');

#
# Structure for table "user"
#

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `uid` varchar(200) NOT NULL COMMENT '用户编号',
  `role_id` int(10) NOT NULL COMMENT '角色编号',
  `username` varchar(100) NOT NULL COMMENT '用户名',
  `password` varchar(200) NOT NULL COMMENT '密码',
  `avatar` varchar(500) NOT NULL DEFAULT '/images/avatar/avatar-1.jpg' COMMENT '头像',
  `login_date` datetime NOT NULL COMMENT '登陆时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

#
# Data for table "user"
#

INSERT INTO `user` VALUES (1,'6aba4abde7c7499597aa91e7f154ca2c',2,'111','$2a$10$R4rX.JCCRzRbhx1j.q7zpOVDA65cQaKcE4WNhUUImHIUvJG7UoZ6a','/images/avatar/3.jpg','2020-08-06 10:29:07','2020-08-06 10:29:07'),(2,'284f66df13624600b1e1af9c5b657c90',2,'222','$2a$10$XvnwXUYPnW.RjbpsCvcNUuGLf.BBqBkY61rR8E7Pft.zeAburNNNW','/images/avatar/1.jpg','2020-08-06 10:39:58','2020-08-06 10:39:58'),(3,'e82b4db91a5644f1aeb47ca6401b2ec2',1,'lzy','$2a$10$YFYCg15d.wtaiBgPAz.qXOem0p2m6s6jiN1Y/I0ht4yUZVMKjY50C','/images/avatar/avatar-1.jpg','2020-08-07 16:20:01','2020-08-07 16:20:01');

#
# Structure for table "user_donate"
#

DROP TABLE IF EXISTS `user_donate`;
CREATE TABLE `user_donate` (
  `uid` varchar(200) NOT NULL COMMENT '用户id',
  `danate_json` varchar(200) NOT NULL COMMENT '赞赏二维码信息'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "user_donate"
#


#
# Structure for table "user_info"
#

DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `uid` varchar(200) NOT NULL COMMENT '用户id',
  `nickname` varchar(80) DEFAULT NULL COMMENT '用户昵称',
  `realname` varchar(80) DEFAULT NULL COMMENT '真实姓名',
  `qq` varchar(20) DEFAULT NULL COMMENT 'QQ',
  `wechat` varchar(200) DEFAULT NULL COMMENT '微信',
  `email` varchar(500) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机',
  `work` varchar(200) DEFAULT NULL COMMENT '工作',
  `address` varchar(500) DEFAULT NULL COMMENT '地址',
  `hobby` varchar(500) DEFAULT NULL COMMENT '爱好',
  `intro` varchar(2000) DEFAULT NULL COMMENT '自我介绍'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "user_info"
#

INSERT INTO `user_info` VALUES ('6aba4abde7c7499597aa91e7f154ca2c','昵称','姓名','qq','微信','邮箱','手机','工作','中国','代码,唱歌,跳舞','介绍'),('284f66df13624600b1e1af9c5b657c90',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('e82b4db91a5644f1aeb47ca6401b2ec2',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

#
# Structure for table "user_role"
#

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '角色编号',
  `name` varchar(200) NOT NULL COMMENT '角色名称',
  `description` varchar(500) NOT NULL DEFAULT '无描述...' COMMENT '角色描述',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

#
# Data for table "user_role"
#

INSERT INTO `user_role` VALUES (1,'管理员','管理员','0000-00-00 00:00:00'),(2,'普通用户','无描述...','0000-00-00 00:00:00');
