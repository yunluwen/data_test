package com.zyh.utils;

public class FieldInfo {

    private String COLUMN_NAME;
    private String COLUMN_TYPE;
    private String HIVE_TYPE;
    private String COLUMN_KEY;

    public String getCOLUMN_NAME() {
        return COLUMN_NAME;
    }

    public String getCOLUMN_TYPE() {
        return COLUMN_TYPE;
    }

    public String getHIVE_TYPE() {
        return HIVE_TYPE;
    }

    public String getCOLUMN_KEY() {
        return COLUMN_KEY;
    }

    public void setCOLUMN_NAME(String COLUMN_NAME) {
        this.COLUMN_NAME = COLUMN_NAME;
    }

    public void setCOLUMN_TYPE(String COLUMN_TYPE) {
        this.COLUMN_TYPE = COLUMN_TYPE;
    }

    public void setHIVE_TYPE(String HIVE_TYPE) {
        this.HIVE_TYPE = HIVE_TYPE;
    }

    public void setCOLUMN_KEY(String COLUMN_KEY) {
        this.COLUMN_KEY = COLUMN_KEY;
    }
}
