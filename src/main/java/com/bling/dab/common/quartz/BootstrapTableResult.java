package com.bling.dab.common.quartz;

import java.util.List;

/**
 * @description bootstrapTable所需的结果集
 * @author admin
 * @date 2017-11-25 18:59
 */

public class BootstrapTableResult {
    /**
     * 总记录数
     */
    private Integer total;
    /**
     * 结果集的list集合
     */
    private List rows;

    public BootstrapTableResult() {
    }

    public BootstrapTableResult(Integer total, List rows) {
        this.total = total;
        this.rows = rows;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "BootstrapTableResult{" +
                "total=" + total +
                ", rows=" + rows +
                '}';
    }
}
