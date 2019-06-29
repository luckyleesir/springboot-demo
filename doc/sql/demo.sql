/*
 Navicat Premium Data Transfer

 Source Server         : mysql本机
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : localhost:3306
 Source Schema         : demo

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 29/06/2019 17:34:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `permission_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '权限id',
  `pid` bigint(20) NULL DEFAULT 0 COMMENT '父级权限id',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限名称',
  `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限值（应用于controller注解）',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限对应url',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限图标',
  `description` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限描述',
  `type` tinyint(1) NULL DEFAULT NULL COMMENT '权限类型：0->目录；1->菜单；2->按钮（接口绑定权限）',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '启用状态；0->禁用；1->启用',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后修改时间',
  PRIMARY KEY (`permission_id`) USING BTREE,
  UNIQUE INDEX `idx_sys_permission_permission`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (1, 0, '用户系统', 'user', NULL, 'layui-icon-user', NULL, 1, 1, '2019-06-28 16:01:37', '2019-06-28 16:27:11');
INSERT INTO `sys_permission` VALUES (2, 1, '角色列表', 'sys:user:read', 'page/user/userList.html', '', '', 1, 1, '2019-06-28 16:27:23', '2019-06-29 17:23:46');
INSERT INTO `sys_permission` VALUES (3, 1, '权限管理', 'sys:user:read', 'page/user/permissionList.html', NULL, NULL, 1, 1, '2019-06-28 16:27:50', '2019-06-29 17:23:44');
INSERT INTO `sys_permission` VALUES (4, 0, '系统管理', 'sys:user:read', NULL, NULL, NULL, 1, 1, '2019-06-28 16:28:06', '2019-06-29 17:23:44');
INSERT INTO `sys_permission` VALUES (5, 4, '系统日志', 'sys:user:read', NULL, NULL, NULL, 1, 1, '2019-06-28 16:28:16', '2019-06-29 17:23:43');
INSERT INTO `sys_permission` VALUES (6, 4, '菜单设置', 'sys:user:read', NULL, NULL, NULL, 1, 1, '2019-06-28 16:28:29', '2019-06-29 17:23:42');
INSERT INTO `sys_permission` VALUES (7, 1, '用户列表', 'sys:user:read', 'page/user/userList.html', '', '111', 1, 1, '2019-06-28 16:28:42', '2019-06-29 10:30:21');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `description` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '启用状态；0->禁用；1->启用',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后修改时间',
  PRIMARY KEY (`role_id`) USING BTREE,
  UNIQUE INDEX `idx_sys_role_role_name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '普通用户', '啊啊啊啊', 1, '2019-06-29 09:40:56', '2019-06-29 15:49:35');
INSERT INTO `sys_role` VALUES (3, '超管', 'aaaa', 1, '2019-06-29 11:03:08', '2019-06-29 11:03:08');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色权限id',
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色id',
  `permission_id` bigint(20) NULL DEFAULT NULL COMMENT '权限id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 189 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES (176, 1, 1);
INSERT INTO `sys_role_permission` VALUES (177, 1, 2);
INSERT INTO `sys_role_permission` VALUES (178, 1, 3);
INSERT INTO `sys_role_permission` VALUES (179, 1, 7);
INSERT INTO `sys_role_permission` VALUES (180, 1, 4);
INSERT INTO `sys_role_permission` VALUES (181, 1, 5);
INSERT INTO `sys_role_permission` VALUES (182, 3, 1);
INSERT INTO `sys_role_permission` VALUES (183, 3, 2);
INSERT INTO `sys_role_permission` VALUES (184, 3, 3);
INSERT INTO `sys_role_permission` VALUES (185, 3, 7);
INSERT INTO `sys_role_permission` VALUES (186, 3, 4);
INSERT INTO `sys_role_permission` VALUES (187, 3, 5);
INSERT INTO `sys_role_permission` VALUES (188, 3, 6);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `nick` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `signature` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个性签名',
  `sex` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `age` int(5) NULL DEFAULT NULL COMMENT '年龄',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '启用状态；0->禁用；1->启用',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后修改时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `idx_sys_user_username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (3, '0', '$2a$10$GlHF8IZm4JwzB/RPax9c9uyftZf6qtOwfcSroYs6W2ZJB0CR2mGcW', 'string', 'string', NULL, 'string', 0, NULL, 0, '2019-06-19 14:39:27', '2019-06-19 14:39:27');
INSERT INTO `sys_user` VALUES (4, '1', '0', '0', '0', 'null', '女', 0, '615612@qq.com', 1, '2019-06-21 16:03:13', '2019-06-28 10:06:12');
INSERT INTO `sys_user` VALUES (10, 'liminghai5', '$2a$10$S96MXFM7eQkOKEi3bTKtmO1s.x/WILFAsROZZ6uOk8tUdP2TGBuyu', NULL, NULL, 'qwe11111', '女', NULL, '615612@qq.com', 0, '2019-06-24 09:36:59', '2019-06-28 10:06:05');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户角色id',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 3, 1);

SET FOREIGN_KEY_CHECKS = 1;
