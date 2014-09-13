package com.zane001.happyweather.util.db;

/**
 * Created by zane001 on 2014/9/7.
 */
public class Column {
    public static enum Constraint {
        UNIQUE("UNIQUE"), NOT("NOT"), NULL("NULL"), CHECK("CHECK"), FOREIGN_KEY("FOREIGN KEY"), PRIMARY_KEY(
                "PRIMARY KEY");

        private String value;
        private Constraint(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    public static enum DataType {
        NULL, INTEGER, REAL, TEXT, BLOB
    }

    private String columnName;
    private Constraint constraint;
    private DataType dataType;

    public Column(String columnName, Constraint constraint, DataType dataType) {
        this.columnName = columnName;
        this.constraint = constraint;
        this.dataType = dataType;
    }

    public String getColumnName() {
        return columnName;
    }

    public Constraint getConstraint() {
        return constraint;
    }

    public DataType getDataType() {
        return dataType;
    }
}
