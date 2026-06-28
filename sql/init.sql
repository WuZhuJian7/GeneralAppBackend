-- =============================================
-- General App Backend 数据库初始化脚本
-- 数据库: MySQL 8.0
-- =============================================

-- 创建数据库
CREATE DATABASE IF NOT EXISTS general_app DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE general_app;

-- =============================================
-- 用户表
-- =============================================
DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user (
    id          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    username    VARCHAR(50)  NOT NULL                COMMENT '用户名',
    password    VARCHAR(200) NOT NULL                COMMENT '密码',
    nickname    VARCHAR(50)  DEFAULT NULL            COMMENT '昵称',
    phone       VARCHAR(20)  DEFAULT NULL            COMMENT '手机号',
    email       VARCHAR(100) DEFAULT NULL            COMMENT '邮箱',
    avatar      VARCHAR(500) DEFAULT NULL            COMMENT '头像URL',
    status      TINYINT      NOT NULL DEFAULT 1      COMMENT '状态：0-禁用，1-正常',
    deleted     TINYINT      NOT NULL DEFAULT 0      COMMENT '逻辑删除：0-未删除，1-已删除',
    version     INT          NOT NULL DEFAULT 1      COMMENT '乐观锁版本号',
    create_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username),
    KEY idx_phone (phone),
    KEY idx_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户表';

-- 插入默认管理员用户（密码: 123456，BCrypt加密）
INSERT INTO sys_user (username, password, nickname, status) VALUES
('admin', '$2a$10$PZxs6WCSjw7oTlaqil4voeI9psJtU07htRlRfeVUxn6hed0i.B8SW', '系统管理员', 1);
