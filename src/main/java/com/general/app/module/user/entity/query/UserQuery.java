package com.general.app.module.user.entity.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户查询条件
 */
@Data
@Schema(description = "用户查询条件")
public class UserQuery implements Serializable {

    @Schema(description = "用户名（模糊查询）")
    private String username;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "状态：0-禁用，1-正常")
    private Integer status;

    @Schema(description = "当前页码")
    private Integer pageNum = 1;

    @Schema(description = "每页大小")
    private Integer pageSize = 10;
}
