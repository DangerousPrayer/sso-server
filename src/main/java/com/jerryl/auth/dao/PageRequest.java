package com.jerryl.auth.dao;

/**
 * Created by liuruijie on 2017/4/6.
 * 分页查询的相关参数封装
 */
public class PageRequest {
    private int size; //查询记录的数量
    private Sort[] sorts; //排序
    private int start; //开始位置

    //page为页码
    public PageRequest(int page, int size, Sort... sorts) {
        this.size = size;
        this.sorts = sorts;
        this.start = (page-1)*size;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public Sort[] getSorts() {
        return sorts;
    }

    public void setSorts(Sort[] sorts) {
        this.sorts = sorts;
    }

    public static class Sort {
        private String type;
        private String field;

        public Sort(String field) {
            this.field = field;
            type = "ASC";
        }

        public Sort(String field, String type) {
            this.field = field;
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }
    }
}
