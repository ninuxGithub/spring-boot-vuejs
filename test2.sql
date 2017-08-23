/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50537
Source Host           : 192.168.1.111:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50537
File Encoding         : 65001

Date: 2017-08-23 18:53:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `order_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `produce_name` varchar(255) DEFAULT NULL,
  `product_date` datetime DEFAULT NULL,
  `quality_guarantee_period` datetime DEFAULT NULL,
  `stock_amount` int(11) DEFAULT NULL,
  `head_portrait` varchar(255) DEFAULT NULL,
  `image_name` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=gbk;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('1', '旺仔牛奶', '2017-07-03 11:22:23', '2017-07-29 11:22:27', '100', null, null, '1.23');
INSERT INTO `product` VALUES ('2', '脉动', '2017-07-03 11:24:55', '2017-11-23 11:24:58', '200', null, null, '2.56');
INSERT INTO `product` VALUES ('3', 'javadoc', '2011-01-01 00:00:00', '2017-08-01 00:00:00', '100', null, null, '7.88');
INSERT INTO `product` VALUES ('13', '百事可乐', '2011-01-01 00:00:00', '2018-01-01 00:00:00', '100', null, null, '5.23');
INSERT INTO `product` VALUES ('14', '旺仔牛奶', '2017-07-03 00:00:00', '2022-01-01 00:00:00', '100', null, null, '46.789');
INSERT INTO `product` VALUES ('15', '百事可乐Modify', '2011-01-01 00:00:00', '2018-01-01 00:00:00', '100', null, null, '10.66');
INSERT INTO `product` VALUES ('18', 'Ice cream', '2011-01-01 00:00:00', '2018-01-01 00:00:00', '123', null, null, '20.12');
INSERT INTO `product` VALUES ('19', '绿茶', '2011-01-01 00:00:00', '2018-01-01 00:00:00', '888', null, null, '9.688');
INSERT INTO `product` VALUES ('20', '好望角', '2017-01-01 00:00:00', '2022-01-01 00:00:00', '100', null, null, '3.13');
INSERT INTO `product` VALUES ('21', 'modify', '2011-01-01 00:00:00', '2022-01-01 00:00:00', '100', null, null, '5');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `descrip` varchar(255) DEFAULT NULL,
  `role_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=375 DEFAULT CHARSET=gbk;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('311', '最高权限管理者', 'ROLE_ADMIN');
INSERT INTO `role` VALUES ('312', '普通用户', 'ROLE_USER');
INSERT INTO `role` VALUES ('313', '经理用户', 'ROLE_MANAGER');
INSERT INTO `role` VALUES ('314', '系统管理员', 'ROLE_SYS');
INSERT INTO `role` VALUES ('315', '最高权限管理者', 'ROLE_ADMIN');
INSERT INTO `role` VALUES ('316', '普通用户', 'ROLE_USER');
INSERT INTO `role` VALUES ('317', '经理用户', 'ROLE_MANAGER');
INSERT INTO `role` VALUES ('318', '系统管理员', 'ROLE_SYS');
INSERT INTO `role` VALUES ('319', '最高权限管理者', 'ROLE_ADMIN');
INSERT INTO `role` VALUES ('320', '普通用户', 'ROLE_USER');
INSERT INTO `role` VALUES ('321', '经理用户', 'ROLE_MANAGER');
INSERT INTO `role` VALUES ('322', '系统管理员', 'ROLE_SYS');
INSERT INTO `role` VALUES ('323', '最高权限管理者', 'ROLE_ADMIN');
INSERT INTO `role` VALUES ('324', '普通用户', 'ROLE_USER');
INSERT INTO `role` VALUES ('325', '经理用户', 'ROLE_MANAGER');
INSERT INTO `role` VALUES ('326', '系统管理员', 'ROLE_SYS');
INSERT INTO `role` VALUES ('327', '最高权限管理者', 'ROLE_ADMIN');
INSERT INTO `role` VALUES ('328', '普通用户', 'ROLE_USER');
INSERT INTO `role` VALUES ('329', '经理用户', 'ROLE_MANAGER');
INSERT INTO `role` VALUES ('330', '系统管理员', 'ROLE_SYS');
INSERT INTO `role` VALUES ('331', '最高权限管理者', 'ROLE_ADMIN');
INSERT INTO `role` VALUES ('332', '普通用户', 'ROLE_USER');
INSERT INTO `role` VALUES ('333', '经理用户', 'ROLE_MANAGER');
INSERT INTO `role` VALUES ('334', '系统管理员', 'ROLE_SYS');
INSERT INTO `role` VALUES ('335', '最高权限管理者', 'ROLE_ADMIN');
INSERT INTO `role` VALUES ('336', '普通用户', 'ROLE_USER');
INSERT INTO `role` VALUES ('337', '经理用户', 'ROLE_MANAGER');
INSERT INTO `role` VALUES ('338', '系统管理员', 'ROLE_SYS');
INSERT INTO `role` VALUES ('339', '最高权限管理者', 'ROLE_ADMIN');
INSERT INTO `role` VALUES ('340', '普通用户', 'ROLE_USER');
INSERT INTO `role` VALUES ('341', '经理用户', 'ROLE_MANAGER');
INSERT INTO `role` VALUES ('342', '系统管理员', 'ROLE_SYS');
INSERT INTO `role` VALUES ('343', '最高权限管理者', 'ROLE_ADMIN');
INSERT INTO `role` VALUES ('344', '普通用户', 'ROLE_USER');
INSERT INTO `role` VALUES ('345', '经理用户', 'ROLE_MANAGER');
INSERT INTO `role` VALUES ('346', '系统管理员', 'ROLE_SYS');
INSERT INTO `role` VALUES ('347', '最高权限管理者', 'ROLE_ADMIN');
INSERT INTO `role` VALUES ('348', '普通用户', 'ROLE_USER');
INSERT INTO `role` VALUES ('349', '经理用户', 'ROLE_MANAGER');
INSERT INTO `role` VALUES ('350', '系统管理员', 'ROLE_SYS');
INSERT INTO `role` VALUES ('351', '最高权限管理者', 'ROLE_ADMIN');
INSERT INTO `role` VALUES ('352', '普通用户', 'ROLE_USER');
INSERT INTO `role` VALUES ('353', '经理用户', 'ROLE_MANAGER');
INSERT INTO `role` VALUES ('354', '系统管理员', 'ROLE_SYS');
INSERT INTO `role` VALUES ('355', '最高权限管理者', 'ROLE_ADMIN');
INSERT INTO `role` VALUES ('356', '普通用户', 'ROLE_USER');
INSERT INTO `role` VALUES ('357', '经理用户', 'ROLE_MANAGER');
INSERT INTO `role` VALUES ('358', '系统管理员', 'ROLE_SYS');
INSERT INTO `role` VALUES ('359', '最高权限管理者', 'ROLE_ADMIN');
INSERT INTO `role` VALUES ('360', '普通用户', 'ROLE_USER');
INSERT INTO `role` VALUES ('361', '经理用户', 'ROLE_MANAGER');
INSERT INTO `role` VALUES ('362', '系统管理员', 'ROLE_SYS');
INSERT INTO `role` VALUES ('363', '最高权限管理者', 'ROLE_ADMIN');
INSERT INTO `role` VALUES ('364', '普通用户', 'ROLE_USER');
INSERT INTO `role` VALUES ('365', '经理用户', 'ROLE_MANAGER');
INSERT INTO `role` VALUES ('366', '系统管理员', 'ROLE_SYS');
INSERT INTO `role` VALUES ('367', '最高权限管理者', 'ROLE_ADMIN');
INSERT INTO `role` VALUES ('368', '普通用户', 'ROLE_USER');
INSERT INTO `role` VALUES ('369', '经理用户', 'ROLE_MANAGER');
INSERT INTO `role` VALUES ('370', '系统管理员', 'ROLE_SYS');
INSERT INTO `role` VALUES ('371', '最高权限管理者', 'ROLE_ADMIN');
INSERT INTO `role` VALUES ('372', '普通用户', 'ROLE_USER');
INSERT INTO `role` VALUES ('373', '经理用户', 'ROLE_MANAGER');
INSERT INTO `role` VALUES ('374', '系统管理员', 'ROLE_SYS');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `age` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=gbk;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', null, null, 'admin', 'ROLE_ADMIN', 'admin');
INSERT INTO `user` VALUES ('2', null, null, 'java', 'ROLE_ADMIN', 'tommodify');
INSERT INTO `user` VALUES ('3', null, null, '123456', 'ROLE_ADMIN', 'administrator');
INSERT INTO `user` VALUES ('4', null, null, 'java', 'ROLE_ADMIN', 'ok');
INSERT INTO `user` VALUES ('5', null, null, '111', 'ROLE_ADMIN', '李白');
INSERT INTO `user` VALUES ('6', null, null, '111', 'ROLE_ADMIN', '王维');
INSERT INTO `user` VALUES ('7', null, null, 'adfadsf', 'ROLE_ADMIN', 'afasd');
INSERT INTO `user` VALUES ('8', null, null, '123414321', 'ROLE_ADMIN', 'java');
INSERT INTO `user` VALUES ('9', null, null, 'asldkfj', 'ROLE_ADMIN', 'php');
INSERT INTO `user` VALUES ('10', null, null, 'aaa', 'ROLE_ADMIN', 'aamodify');
INSERT INTO `user` VALUES ('11', null, null, 'aaa', 'ROLE_ADMIN', 'aamodify');
INSERT INTO `user` VALUES ('12', null, null, 'aaa', 'ROLE_ADMIN', 'aamodify');
INSERT INTO `user` VALUES ('46', null, null, 'aaa', 'ROLE_ADMIN', 'aamodify');
INSERT INTO `user` VALUES ('47', null, null, 'aaa', 'ROLE_ADMIN', 'aamodify');
INSERT INTO `user` VALUES ('48', null, null, 'aaa', 'ROLE_ADMIN', 'aamodify');
INSERT INTO `user` VALUES ('49', null, null, 'aaa', 'ROLE_ADMIN', 'aamodify');

-- ----------------------------
-- Table structure for user_roles
-- ----------------------------
DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE `user_roles` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_5q4rc4fh1on6567qk69uesvyf` (`role_id`),
  KEY `FK55itppkw3i07do3h7qoclqd4k` (`user_id`),
  CONSTRAINT `FK55itppkw3i07do3h7qoclqd4k` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKrhfovtciq1l558cw6udg0h0d3` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

-- ----------------------------
-- Records of user_roles
-- ----------------------------
INSERT INTO `user_roles` VALUES ('1', '311');
INSERT INTO `user_roles` VALUES ('1', '312');
INSERT INTO `user_roles` VALUES ('1', '313');
INSERT INTO `user_roles` VALUES ('1', '314');
INSERT INTO `user_roles` VALUES ('2', '315');
INSERT INTO `user_roles` VALUES ('2', '316');
INSERT INTO `user_roles` VALUES ('2', '317');
INSERT INTO `user_roles` VALUES ('2', '318');
INSERT INTO `user_roles` VALUES ('3', '319');
INSERT INTO `user_roles` VALUES ('3', '320');
INSERT INTO `user_roles` VALUES ('3', '321');
INSERT INTO `user_roles` VALUES ('3', '322');
INSERT INTO `user_roles` VALUES ('4', '323');
INSERT INTO `user_roles` VALUES ('4', '324');
INSERT INTO `user_roles` VALUES ('4', '325');
INSERT INTO `user_roles` VALUES ('4', '326');
INSERT INTO `user_roles` VALUES ('5', '327');
INSERT INTO `user_roles` VALUES ('5', '328');
INSERT INTO `user_roles` VALUES ('5', '329');
INSERT INTO `user_roles` VALUES ('5', '330');
INSERT INTO `user_roles` VALUES ('6', '331');
INSERT INTO `user_roles` VALUES ('6', '332');
INSERT INTO `user_roles` VALUES ('6', '333');
INSERT INTO `user_roles` VALUES ('6', '334');
INSERT INTO `user_roles` VALUES ('7', '335');
INSERT INTO `user_roles` VALUES ('7', '336');
INSERT INTO `user_roles` VALUES ('7', '337');
INSERT INTO `user_roles` VALUES ('7', '338');
INSERT INTO `user_roles` VALUES ('8', '339');
INSERT INTO `user_roles` VALUES ('8', '340');
INSERT INTO `user_roles` VALUES ('8', '341');
INSERT INTO `user_roles` VALUES ('8', '342');
INSERT INTO `user_roles` VALUES ('9', '343');
INSERT INTO `user_roles` VALUES ('9', '344');
INSERT INTO `user_roles` VALUES ('9', '345');
INSERT INTO `user_roles` VALUES ('9', '346');
INSERT INTO `user_roles` VALUES ('10', '347');
INSERT INTO `user_roles` VALUES ('10', '348');
INSERT INTO `user_roles` VALUES ('10', '349');
INSERT INTO `user_roles` VALUES ('10', '350');
INSERT INTO `user_roles` VALUES ('11', '351');
INSERT INTO `user_roles` VALUES ('11', '352');
INSERT INTO `user_roles` VALUES ('11', '353');
INSERT INTO `user_roles` VALUES ('11', '354');
INSERT INTO `user_roles` VALUES ('12', '355');
INSERT INTO `user_roles` VALUES ('12', '356');
INSERT INTO `user_roles` VALUES ('12', '357');
INSERT INTO `user_roles` VALUES ('12', '358');
INSERT INTO `user_roles` VALUES ('46', '359');
INSERT INTO `user_roles` VALUES ('46', '360');
INSERT INTO `user_roles` VALUES ('46', '361');
INSERT INTO `user_roles` VALUES ('46', '362');
INSERT INTO `user_roles` VALUES ('47', '363');
INSERT INTO `user_roles` VALUES ('47', '364');
INSERT INTO `user_roles` VALUES ('47', '365');
INSERT INTO `user_roles` VALUES ('47', '366');
INSERT INTO `user_roles` VALUES ('48', '367');
INSERT INTO `user_roles` VALUES ('48', '368');
INSERT INTO `user_roles` VALUES ('48', '369');
INSERT INTO `user_roles` VALUES ('48', '370');
INSERT INTO `user_roles` VALUES ('49', '371');
INSERT INTO `user_roles` VALUES ('49', '372');
INSERT INTO `user_roles` VALUES ('49', '373');
INSERT INTO `user_roles` VALUES ('49', '374');
