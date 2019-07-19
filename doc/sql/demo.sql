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

 Date: 19/07/2019 17:57:34
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
  `type` tinyint(4) NULL DEFAULT NULL COMMENT '权限类型：0->目录；1->菜单；2->按钮（接口绑定权限）',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '启用状态；0->禁用；1->启用',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后修改时间',
  PRIMARY KEY (`permission_id`) USING BTREE,
  UNIQUE INDEX `idx_sys_permission_permission`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (1, 0, '用户系统', 'user:user', NULL, 'layui-icon-user', '用户系统菜单', 1, 1, '2019-06-28 16:01:37', '2019-07-08 18:31:15');
INSERT INTO `sys_permission` VALUES (2, 1, '角色管理', 'sys:role:read', 'page/user/roleList.html', '', '角色管理菜单', 1, 1, '2019-06-28 16:27:23', '2019-07-08 18:35:13');
INSERT INTO `sys_permission` VALUES (3, 1, '权限管理', 'sys:permission:read', 'page/user/permissionList.html', NULL, '权限管理菜单', 1, 1, '2019-06-28 16:27:50', '2019-07-08 18:32:10');
INSERT INTO `sys_permission` VALUES (4, 0, '系统管理', 'sys', '', '', '系统管理菜单', 1, 1, '2019-06-28 16:28:06', '2019-07-08 18:37:07');
INSERT INTO `sys_permission` VALUES (5, 4, '系统日志', 'sys:log:read', '', '', '系统日志菜单', 1, 1, '2019-06-28 16:28:16', '2019-07-08 18:37:38');
INSERT INTO `sys_permission` VALUES (6, 4, '菜单设置', 'sys:user:read', NULL, NULL, '菜单设置', 1, 1, '2019-06-28 16:28:29', '2019-07-15 17:17:14');
INSERT INTO `sys_permission` VALUES (7, 1, '用户管理', 'sys:user:read', 'page/user/userList.html', '', '用户管理菜单', 1, 1, '2019-06-28 16:28:42', '2019-07-08 18:33:34');
INSERT INTO `sys_permission` VALUES (8, 7, '添加用户', 'sys:user:add', NULL, NULL, '添加用户按钮', 2, 1, '2019-07-08 18:28:30', '2019-07-08 18:33:16');
INSERT INTO `sys_permission` VALUES (9, 7, '删除用户', 'sys:user:delete', NULL, NULL, '删除用户按钮', 2, 1, '2019-07-08 18:29:07', '2019-07-08 18:33:37');
INSERT INTO `sys_permission` VALUES (10, 7, '编辑用户', 'sys:user:edit', NULL, NULL, '编辑用户按钮', 2, 1, '2019-07-08 18:30:41', '2019-07-08 18:33:46');
INSERT INTO `sys_permission` VALUES (11, 3, '添加权限', 'sys:permission:add', NULL, NULL, '添加权限按钮', 2, 1, '2019-07-08 18:33:06', '2019-07-08 18:33:06');
INSERT INTO `sys_permission` VALUES (12, 3, '编辑权限', 'sys:permission:edit', NULL, NULL, '编辑权限按钮', 2, 1, '2019-07-08 18:34:13', '2019-07-08 18:34:13');
INSERT INTO `sys_permission` VALUES (13, 3, '删除权限', 'sys:permission:delete', NULL, NULL, '删除权限按钮', 2, 1, '2019-07-08 18:34:37', '2019-07-08 18:34:37');
INSERT INTO `sys_permission` VALUES (14, 2, '添加角色', 'sys:role:add', NULL, NULL, '添加角色按钮', 2, 1, '2019-07-08 18:35:44', '2019-07-08 18:35:44');
INSERT INTO `sys_permission` VALUES (15, 2, '编辑角色', 'sys:role:edit', NULL, NULL, '编辑角色按钮', 2, 1, '2019-07-08 18:36:04', '2019-07-08 18:36:04');
INSERT INTO `sys_permission` VALUES (16, 2, '删除角色', 'sys:role:delete', NULL, NULL, '删除角色按钮', 2, 1, '2019-07-08 18:36:31', '2019-07-08 18:36:31');

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
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '普通用户', '啊啊啊啊', 1, '2019-06-29 09:40:56', '2019-06-29 15:49:35');
INSERT INTO `sys_role` VALUES (3, '超级管理员', '最高权利，开发人员权限，需要改代码Controller控制权限', 1, '2019-06-29 11:03:08', '2019-07-08 19:25:44');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色权限id',
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色id',
  `permission_id` bigint(20) NULL DEFAULT NULL COMMENT '权限id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 225 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES (208, 1, 7);
INSERT INTO `sys_role_permission` VALUES (209, 3, 1);
INSERT INTO `sys_role_permission` VALUES (210, 3, 2);
INSERT INTO `sys_role_permission` VALUES (211, 3, 14);
INSERT INTO `sys_role_permission` VALUES (212, 3, 15);
INSERT INTO `sys_role_permission` VALUES (213, 3, 16);
INSERT INTO `sys_role_permission` VALUES (214, 3, 3);
INSERT INTO `sys_role_permission` VALUES (215, 3, 11);
INSERT INTO `sys_role_permission` VALUES (216, 3, 12);
INSERT INTO `sys_role_permission` VALUES (217, 3, 13);
INSERT INTO `sys_role_permission` VALUES (218, 3, 7);
INSERT INTO `sys_role_permission` VALUES (219, 3, 8);
INSERT INTO `sys_role_permission` VALUES (220, 3, 9);
INSERT INTO `sys_role_permission` VALUES (221, 3, 10);
INSERT INTO `sys_role_permission` VALUES (222, 3, 4);
INSERT INTO `sys_role_permission` VALUES (223, 3, 5);
INSERT INTO `sys_role_permission` VALUES (224, 3, 6);

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
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (3, '0', '$2a$10$GlHF8IZm4JwzB/RPax9c9uyftZf6qtOwfcSroYs6W2ZJB0CR2mGcW', 'string', 'string', 'null', '男', 0, '615612@qq.com', 0, '2019-06-19 14:39:27', '2019-07-16 15:43:24');
INSERT INTO `sys_user` VALUES (4, '1', '0', '0', '0', 'null', '女', 0, '615612@qq.com', 1, '2019-06-21 16:03:13', '2019-06-28 10:06:12');
INSERT INTO `sys_user` VALUES (11, 'admin', '$2a$10$1cdVyY30sR4IuYX54jPTNeEm4UsO/e9wxwqtY734SNqdV1lY7liM6', 'string', 'string', 'string111', '男', 0, 'string@qq.com', 1, '2019-06-28 10:39:37', '2019-07-06 14:47:04');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户角色id',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (2, 11, 3);
INSERT INTO `sys_user_role` VALUES (3, 11, 1);
INSERT INTO `sys_user_role` VALUES (6, 3, 1);
INSERT INTO `sys_user_role` VALUES (7, 13, 1);
INSERT INTO `sys_user_role` VALUES (8, 13, 3);

SET FOREIGN_KEY_CHECKS = 1;
