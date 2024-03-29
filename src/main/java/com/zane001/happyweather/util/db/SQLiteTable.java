package com.zane001.happyweather.util.db;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zane001 on 2014/9/7.
 */

public class SQLiteTable {
    String mTableName;

    List<Column> columnsDefinitions = new ArrayList<Column>();

    public String getTableName() {
        return mTableName;
    }

    public SQLiteTable(String tableName) {
        mTableName = tableName;
        columnsDefinitions.add(new Column(BaseColumns._ID, Column.Constraint.PRIMARY_KEY,
                Column.DataType.INTEGER));
    }

    public SQLiteTable addColumn(Column columnsDefinition) {
        columnsDefinitions.add(columnsDefinition);
        return this;
    }

    public SQLiteTable addColumn(String columnName, Column.DataType dataType) {
        columnsDefinitions.add(new Column(columnName, null, dataType));
        return this;
    }

    public SQLiteTable addColumn(String columnName, Column.Constraint constraint,
                                 Column.DataType dataType) {
        columnsDefinitions.add(new Column(columnName, constraint, dataType));
        return this;
    }

    public void create(SQLiteDatabase db) {
        String formatter = " %s";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CREATE TABLE IF NOT EXISTS ");
        stringBuilder.append(mTableName);
        stringBuilder.append("(");
        int columnCount = columnsDefinitions.size();
        int index = 0;
        for (Column columnsDefinition : columnsDefinitions) {
            stringBuilder.append(columnsDefinition.getColumnName()).append(
                    String.format(formatter, columnsDefinition.getDataType().name()));
            Column.Constraint constraint = columnsDefinition.getConstraint();

            if (constraint != null) {
                stringBuilder.append(String.format(formatter, constraint.toString()));
            }
            if (index < columnCount - 1) {
                stringBuilder.append(",");
            }
            index++;
        }
        stringBuilder.append(");");
        db.execSQL(stringBuilder.toString());
    }

    public void delete(final SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS" + mTableName);
    }
}
