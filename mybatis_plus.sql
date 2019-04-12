/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : localhost:3306
 Source Schema         : mybatis_plus

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 12/04/2019 17:06:11
*/

CREATE DATABASE IF NOT EXISTS mybatis_plus DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for core_log
-- ----------------------------
DROP TABLE IF EXISTS `core_log`;
CREATE TABLE `core_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `modelId` int(11) NULL DEFAULT NULL,
  `tableName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `module` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `paras` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `uid` int(11) NULL DEFAULT NULL,
  `createTime` datetime(0) NULL DEFAULT NULL,
  `updateTime` datetime(0) NULL DEFAULT NULL,
  `isDel` bigint(255) NULL DEFAULT NULL,
  `version` int(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_file
-- ----------------------------
DROP TABLE IF EXISTS `t_file`;
CREATE TABLE `t_file`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fileName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `filePath` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `fileSize` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `uploadName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `introduce` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `isDel` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `updateTime` datetime(0) NULL DEFAULT NULL,
  `createTime` datetime(0) NULL DEFAULT NULL,
  `version` int(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_file
-- ----------------------------
INSERT INTO `t_file` VALUES (1, '11', '11', '11', '文件名+时间戳', '测试', '0', '2019-04-08 17:00:57', '2019-04-08 17:01:01', 1);
INSERT INTO `t_file` VALUES (2, '2323', '20190408', '108KB', '20190408', '2323', '0', '2019-04-08 18:11:52', '2019-04-08 18:11:52', 1);
INSERT INTO `t_file` VALUES (3, '100', '20190408', '108KB', '20190408', '100', '0', '2019-04-08 18:17:09', '2019-04-08 18:17:09', 1);
INSERT INTO `t_file` VALUES (4, '200', 'D:\\myproject01\\src\\main\\webapp\\static\\upload\\20190408\\', '108KB', 'D:\\myproject01\\src\\main\\webapp\\static\\upload\\20190408\\', '200', '0', '2019-04-08 18:28:21', '2019-04-08 18:28:21', 1);
INSERT INTO `t_file` VALUES (5, '200', 'D:\\myproject01\\src\\main\\webapp\\static\\upload\\20190408\\', '108KB', 'D:\\myproject01\\src\\main\\webapp\\static\\upload\\20190408\\', '200', '0', '2019-04-08 18:29:40', '2019-04-08 18:29:40', 1);
INSERT INTO `t_file` VALUES (6, '333', 'D:\\myproject01\\src\\main\\webapp\\static\\upload\\20190408\\', '108KB', 'D:\\myproject01\\src\\main\\webapp\\static\\upload\\20190408\\', '333', '0', '2019-04-08 18:43:18', '2019-04-08 18:38:40', 1);
INSERT INTO `t_file` VALUES (7, '888', 'D:\\myproject01\\src\\main\\webapp\\static\\upload\\20190408\\', '108KB', '20190408185224288212.jpeg', '888', '1', '2019-04-08 18:52:32', '2019-04-08 18:52:32', 1);
INSERT INTO `t_file` VALUES (8, '2323', 'D:\\myproject01\\src\\main\\webapp\\static\\upload\\20190408\\', '108KB', '20190408185305747605.jpeg', '1111', '1', '2019-04-08 18:53:06', '2019-04-08 18:53:06', 1);
INSERT INTO `t_file` VALUES (9, '888', 'D:\\myproject01\\src\\main\\webapp\\static\\upload\\20190408\\', '108KB', '20190408185323611059.jpeg', '888', '0', '2019-04-08 18:53:37', '2019-04-08 18:53:37', 1);
INSERT INTO `t_file` VALUES (10, '11', '11', '11', '文件名+时间戳', '测试', '0', '2019-04-09 18:27:10', '2019-04-09 18:27:10', 1);
INSERT INTO `t_file` VALUES (11, '11', '11', '11', '文件名+时间戳', '测试', '0', '2019-04-09 18:27:51', '2019-04-09 18:27:51', 1);
INSERT INTO `t_file` VALUES (12, '2323', '20190408', '108KB', '20190408', '2323', '0', '2019-04-09 18:27:52', '2019-04-09 18:27:52', 1);
INSERT INTO `t_file` VALUES (13, '100', '20190408', '108KB', '20190408', '100', '0', '2019-04-09 18:27:52', '2019-04-09 18:27:52', 1);
INSERT INTO `t_file` VALUES (14, '200', 'D:\\myproject01\\src\\main\\webapp\\static\\upload\\20190408\\', '108KB', 'D:\\myproject01\\src\\main\\webapp\\static\\upload\\20190408\\', '200', '0', '2019-04-09 18:27:52', '2019-04-09 18:27:52', 1);
INSERT INTO `t_file` VALUES (15, '200', 'D:\\myproject01\\src\\main\\webapp\\static\\upload\\20190408\\', '108KB', 'D:\\myproject01\\src\\main\\webapp\\static\\upload\\20190408\\', '200', '0', '2019-04-09 18:27:52', '2019-04-09 18:27:52', 1);
INSERT INTO `t_file` VALUES (16, '333', 'D:\\myproject01\\src\\main\\webapp\\static\\upload\\20190408\\', '108KB', 'D:\\myproject01\\src\\main\\webapp\\static\\upload\\20190408\\', '333', '0', '2019-04-09 18:27:52', '2019-04-09 18:27:52', 1);
INSERT INTO `t_file` VALUES (17, '888', 'D:\\myproject01\\src\\main\\webapp\\static\\upload\\20190408\\', '108KB', '20190408185323611059.jpeg', '888', '0', '2019-04-09 18:27:52', '2019-04-09 18:27:52', 1);
INSERT INTO `t_file` VALUES (18, '11', '11', '11', '文件名+时间戳', '测试', '0', '2019-04-09 18:28:04', '2019-04-09 18:28:04', 1);
INSERT INTO `t_file` VALUES (19, '2323', '20190408', '108KB', '20190408', '2323', '0', '2019-04-09 18:28:04', '2019-04-09 18:28:04', 1);
INSERT INTO `t_file` VALUES (20, '100', '20190408', '108KB', '20190408', '100', '0', '2019-04-09 18:28:04', '2019-04-09 18:28:04', 1);
INSERT INTO `t_file` VALUES (21, '200', 'D:\\myproject01\\src\\main\\webapp\\static\\upload\\20190408\\', '108KB', 'D:\\myproject01\\src\\main\\webapp\\static\\upload\\20190408\\', '200', '0', '2019-04-09 18:28:04', '2019-04-09 18:28:04', 1);
INSERT INTO `t_file` VALUES (22, '200', 'D:\\myproject01\\src\\main\\webapp\\static\\upload\\20190408\\', '108KB', 'D:\\myproject01\\src\\main\\webapp\\static\\upload\\20190408\\', '200', '0', '2019-04-09 18:28:04', '2019-04-09 18:28:04', 1);
INSERT INTO `t_file` VALUES (23, '333', 'D:\\myproject01\\src\\main\\webapp\\static\\upload\\20190408\\', '108KB', 'D:\\myproject01\\src\\main\\webapp\\static\\upload\\20190408\\', '333', '0', '2019-04-09 18:28:04', '2019-04-09 18:28:04', 1);
INSERT INTO `t_file` VALUES (24, '888', 'D:\\myproject01\\src\\main\\webapp\\static\\upload\\20190408\\', '108KB', '20190408185323611059.jpeg', '888', '0', '2019-04-09 18:28:04', '2019-04-09 18:28:04', 1);
INSERT INTO `t_file` VALUES (25, '11', '11', '11', '文件名+时间戳', '测试', '0', '2019-04-09 19:08:24', '2019-04-09 19:08:24', 1);
INSERT INTO `t_file` VALUES (26, '2323', '20190408', '108KB', '20190408', '2323', '0', '2019-04-09 19:08:24', '2019-04-09 19:08:24', 1);
INSERT INTO `t_file` VALUES (27, '100', '20190408', '108KB', '20190408', '100', '0', '2019-04-09 19:08:24', '2019-04-09 19:08:24', 1);
INSERT INTO `t_file` VALUES (28, '200', 'D:\\myproject01\\src\\main\\webapp\\static\\upload\\20190408\\', '108KB', 'D:\\myproject01\\src\\main\\webapp\\static\\upload\\20190408\\', '200', '0', '2019-04-09 19:08:24', '2019-04-09 19:08:24', 1);
INSERT INTO `t_file` VALUES (29, '200', 'D:\\myproject01\\src\\main\\webapp\\static\\upload\\20190408\\', '108KB', 'D:\\myproject01\\src\\main\\webapp\\static\\upload\\20190408\\', '200', '0', '2019-04-09 19:08:24', '2019-04-09 19:08:24', 1);
INSERT INTO `t_file` VALUES (30, '333', 'D:\\myproject01\\src\\main\\webapp\\static\\upload\\20190408\\', '108KB', 'D:\\myproject01\\src\\main\\webapp\\static\\upload\\20190408\\', '333', '0', '2019-04-09 19:08:24', '2019-04-09 19:08:24', 1);
INSERT INTO `t_file` VALUES (31, '888', 'D:\\myproject01\\src\\main\\webapp\\static\\upload\\20190408\\', '108KB', '20190408185323611059.jpeg', '888', '0', '2019-04-09 19:08:24', '2019-04-09 19:08:24', 1);

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `aid` int(20) NULL DEFAULT NULL COMMENT '单位id',
  `realName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `status` int(11) NULL DEFAULT 0 COMMENT '状态 0-正常 1-禁用',
  `userName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账号',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `isDel` int(11) NULL DEFAULT 0 COMMENT '是否删除 0否 1是',
  `createTime` datetime(0) NULL DEFAULT NULL COMMENT '注册时间',
  `updateTime` datetime(0) NULL DEFAULT NULL,
  `version` int(255) NULL DEFAULT NULL COMMENT '版本默认是1',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_userName`(`userName`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, 11, '11', 0, '11', '11', 0, '2019-04-04 18:19:17', '2019-04-04 18:19:20', 1);

SET FOREIGN_KEY_CHECKS = 1;
