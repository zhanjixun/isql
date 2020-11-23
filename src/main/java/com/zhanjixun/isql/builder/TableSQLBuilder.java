package com.zhanjixun.isql.builder;

import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;
import com.zhanjixun.isql.annotation.Column;
import com.zhanjixun.isql.annotation.ColumnType;
import com.zhanjixun.isql.annotation.Table;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * 构建sql语句
 *
 * @author zhanjixun
 * @date 2020-11-21 14:50:29
 */
public class TableSQLBuilder {

    private final static Converter<String, String> converter = CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.LOWER_UNDERSCORE);

    public static String buildCreateSQL(Class<?> type) {
        StringBuilder builder = new StringBuilder("CREATE TABLE ");
        Table table = type.getAnnotation(Table.class);
        //表名
        boolean underScoreCase = table.underScoreCase();
        String tableName = table.prefix() + StringUtils.defaultIfBlank(table.name(), underScoreCase ? converter.convert(type.getSimpleName()) : type.getSimpleName());
        //主键

        String primaryKeyStr = Arrays.stream(type.getDeclaredFields()).filter(f -> f.isAnnotationPresent(Column.class))
                .filter(f -> f.getAnnotation(Column.class).primaryKey()).map(f -> {
                    Column annotation = f.getAnnotation(Column.class);
                    return StringUtils.defaultIfBlank(annotation.name(), underScoreCase ? converter.convert(f.getName()) : f.getName());
                }).collect(Collectors.joining(","));
        //列定义
        String columnStr = Arrays.stream(type.getDeclaredFields()).filter(f -> f.isAnnotationPresent(Column.class)).map(f -> {
            Column column = f.getAnnotation(Column.class);
            String columnName = StringUtils.defaultIfBlank(column.name(), underScoreCase ? converter.convert(f.getName()) : f.getName());
            String columnType = mappingType(f.getType(), column);
            String nullable = column.primaryKey() || !column.nullable() ? " NOT NULL" : " NULL";
            String autoIncrement = column.autoIncrement() ? " AUTO_INCREMENT" : "";
            String comment = String.format(" COMMENT '%s'", column.comment());
            return columnName + columnType + nullable + autoIncrement + comment;
        }).collect(Collectors.joining(","));

        return builder.toString();
    }

    private static String mappingType(Class<?> filedType, Column column) {
        if (column.type() != ColumnType.AUTO) {
            switch (column.type()) {
                case TINYINT:
                    break;
                case SMALLINT:
                    break;
                case INT:
                    break;
                case BIGINT:
                    break;
                case BLOB:
                    break;
                case CHAR:
                    break;
                case DATE:
                    return " DATE";
                case TEXT:
                    return " text";
                case TIME:
                    break;
                case YEAR:
                    break;
                case FLOAT:
                    break;
                case DOUBLE:
                    break;
                case DECIMAL:
                    break;
                case INTEGER:
                    break;
                case VARCHAR:
                    break;
                case DATETIME:
                    return " DATETIME";
                case LONGBLOB:
                    break;
                case LONGTEXT:
                    break;
                case TINYBLOB:
                    break;
                case TINYTEXT:
                    break;
                case MEDIUMINT:
                    break;
                case TIMESTAMP:
                    break;
                case MEDIUMBLOB:
                    break;
                case MEDIUMTEXT:
                    break;
            }
        }
        if (filedType == String.class) {
            return " VARCHAR(" + column.length() + ")";
        }
        if (filedType == Integer.class || filedType == int.class) {
            return " INT(" + column.length() + ")";
        }
        if (filedType == Long.class || filedType == long.class) {
            return " INT(" + column.length() + ")";
        }
        if (filedType == Double.class || filedType == double.class) {
            return " DOUBLE(" + column.precision() + "," + column.scale() + ")";
        }
        if (filedType == Float.class || filedType == float.class) {
            return " FLOAT(" + column.precision() + "," + column.scale() + ")";
        }
        if (filedType == BigDecimal.class) {
            return " DECIMAL(" + column.precision() + "," + column.scale() + ")";
        }
        if (filedType == Date.class || filedType == java.sql.Date.class) {
            return " DATE";
        }
        return null;
    }

}
