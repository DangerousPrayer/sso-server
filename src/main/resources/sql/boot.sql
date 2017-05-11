/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50709
Source Host           : 127.0.0.1:3306
Source Database       : mybatisboot

Target Server Type    : MYSQL
Target Server Version : 50709
File Encoding         : 65001

Date: 2017-04-12 20:41:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_action
-- ----------------------------
DROP TABLE IF EXISTS `sys_action`;
CREATE TABLE `sys_action` (
  `action_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `action_name` varchar(12) NOT NULL DEFAULT '',
  PRIMARY KEY (`action_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_action
-- ----------------------------
INSERT INTO `sys_action` VALUES ('1', 'add');
INSERT INTO `sys_action` VALUES ('2', 'edit');
INSERT INTO `sys_action` VALUES ('3', 'del');
INSERT INTO `sys_action` VALUES ('4', 'show');

-- ----------------------------
-- Table structure for sys_module
-- ----------------------------
DROP TABLE IF EXISTS `sys_module`;
CREATE TABLE `sys_module` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `module_name` varchar(32) NOT NULL DEFAULT '',
  `sort` int(11) NOT NULL DEFAULT '0',
  `url` varchar(255) NOT NULL DEFAULT '',
  `type` varchar(32) NOT NULL,
  `create_time` datetime NOT NULL,
  `create_passport` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `module_passport` (`create_passport`),
  CONSTRAINT `module_passport` FOREIGN KEY (`create_passport`) REFERENCES `sys_passport` (`id`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_module
-- ----------------------------
INSERT INTO `sys_module` VALUES ('1', '权限管理', '0', '', 'module', '2017-04-12 19:36:44', 'admin');

-- ----------------------------
-- Table structure for sys_passport
-- ----------------------------
DROP TABLE IF EXISTS `sys_passport`;
CREATE TABLE `sys_passport` (
  `id` varchar(12) NOT NULL,
  `password` varchar(32) NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '0禁用，1启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_passport
-- ----------------------------
INSERT INTO `sys_passport` VALUES ('admin', 'admin', '1');

-- ----------------------------
-- Table structure for sys_passport_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_passport_role`;
CREATE TABLE `sys_passport_role` (
  `passport_id` varchar(12) NOT NULL,
  `role_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`passport_id`,`role_id`),
  KEY `passport_role_role` (`role_id`),
  CONSTRAINT `passport_role_passport` FOREIGN KEY (`passport_id`) REFERENCES `sys_passport` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `passport_role_role` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_passport_role
-- ----------------------------
INSERT INTO `sys_passport_role` VALUES ('admin', '1');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(10) unsigned NOT NULL,
  `role_name` varchar(12) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', 'base_admin');
INSERT INTO `sys_role` VALUES ('2', 'user');

-- ----------------------------
-- Table structure for sys_role_actions
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_actions`;
CREATE TABLE `sys_role_actions` (
  `role_id` int(10) unsigned NOT NULL,
  `module_id` int(10) unsigned NOT NULL,
  `actions` varchar(32) NOT NULL DEFAULT '',
  PRIMARY KEY (`role_id`,`module_id`),
  KEY `role_actions_module` (`module_id`),
  CONSTRAINT `role_actions_module` FOREIGN KEY (`module_id`) REFERENCES `sys_module` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `role_actions_role` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_actions
-- ----------------------------
INSERT INTO `sys_role_actions` VALUES ('1', '1', '1,2,3,4');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `nick_name` varchar(12) NOT NULL DEFAULT '',
  `email` varchar(50) NOT NULL DEFAULT '',
  `phone` varchar(20) NOT NULL DEFAULT '',
  `passport_id` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_passport` (`passport_id`),
  CONSTRAINT `user_passport` FOREIGN KEY (`passport_id`) REFERENCES `sys_passport` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '基础管理员', '', '', 'admin');
