package com.general.app.common.result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页查询结果封装
 */
@Data
@Schema(description = "分页查询结果")
public class PageResult<T> implements Serializable {

    @Schema(description = "当前页码")
    private long current;

    @Schema(description = "每页大小")
    private long size;

    @Schema(description = "总记录数")
    private long total;

    @Schema(description = "数据列表")
    private List<T> records;

    public PageResult() {
    }

    public PageResult(long current, long size, long total, List<T> records) {
        this.current = current;
        this.size = size;
        this.total = total;
        this.records = records;
    }

    /**
     * 创建分页结果
     */
    public static <T> PageResult<T> of(long current, long size, long total, List<T> records) {
        return new PageResult<>(current, size, total, records);
    }
}
