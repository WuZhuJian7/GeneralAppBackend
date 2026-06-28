package com.general.app.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 统一响应状态码枚举
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),

    // 认证相关 401x
    UNAUTHORIZED(401, "未认证"),
    TOKEN_EXPIRED(4011, "Token已过期"),
    TOKEN_INVALID(4012, "Token无效"),
    LOGIN_FAILED(4013, "用户名或密码错误"),
    ACCOUNT_DISABLED(4014, "账号已被禁用"),
    ACCESS_DENIED(403, "无权限访问"),

    // 参数相关 400x
    BAD_REQUEST(400, "请求参数错误"),
    VALIDATION_ERROR(4001, "参数校验失败"),
    PARAM_MISSING(4002, "缺少必要参数"),

    // 业务相关 402x
    NOT_FOUND(404, "资源不存在"),
    CONFLICT(409, "数据冲突"),
    TOO_MANY_REQUESTS(429, "请求过于频繁"),

    // 服务端相关 500x
    INTERNAL_ERROR(5001, "系统内部错误"),
    SERVICE_UNAVAILABLE(503, "服务暂不可用");

    /**
     * 状态码
     */
    private final int code;

    /**
     * 提示信息
     */
    private final String message;
}
