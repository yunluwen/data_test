package com.zyh.utils;

import java.util.List;
import java.util.Map;

public class TableInfo {

    private String tablename;
    private String pri;
    private List<FieldInfo> children;

    public String getTablename() {
        return tablename;
    }

    public String getPri() {
        return pri;
    }

    public List<FieldInfo> getChildren() {
        return children;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public void setPri(String pri) {
        this.pri = pri;
    }

    public void setChildren(List<FieldInfo> children) {
        this.children = children;
    }
}
