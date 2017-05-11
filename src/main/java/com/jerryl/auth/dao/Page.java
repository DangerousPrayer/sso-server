package com.jerryl.auth.dao;

import java.util.List;

/**
 * Created by liuruijie on 2017/4/6.
 * 分页查询的返回值
 */
public class Page<T> {
    private long totalRows; //总记录数
    private int totalPages; //总页数
    private List<T> rows; //查询到的记录

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public long getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(long totalRows) {
        this.totalRows = totalRows;
    }
}
