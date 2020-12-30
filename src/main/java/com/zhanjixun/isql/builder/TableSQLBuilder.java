package com.zhanjixun.isql.builder;

import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;
import com.google.common.collect.Lists;
import com.zhanjixun.isql.annotation.Column;
import com.zhanjixun.isql.annotation.ColumnType;
import com.zhanjixun.isql.annotation.Index;
import com.zhanjixun.isql.annotation.Table;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
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
        Table table = type.getAnnotation(Table.class);
        //表名
        boolean underScoreCase = table.underScoreCase();
        String tableName = table.prefix() + StringUtils.defaultIfBlank(table.name(), underScoreCase ? converter.convert(type.getSimpleName()) : type.getSimpleName());
        //列定义
        String columnStr = Arrays.stream(type.getDeclaredFields()).filter(f -> f.isAnnotationPresent(Column.class)).map(f -> {
            Column column = f.getAnnotation(Column.class);
            String columnName = getColumnName(type, f);
            String columnType = mappingType(f.getType(), column);
            String nullable = column.primaryKey() || !column.nullable() ? " NOT NULL" : " NULL";
            String autoIncrement = column.autoIncrement() ? " AUTO_INCREMENT" : "";
            String comment = "".equals(column.comment()) ? "" : String.format(" COMMENT '%s' ", column.comment());
            return " " + columnName + columnType + nullable + autoIncrement + comment;
        }).collect(Collectors.joining(",\n"));
        //主键
        String primaryKeyStr = Arrays.stream(type.getDeclaredFields()).filter(f -> f.isAnnotationPresent(Column.class))
                .filter(f -> f.getAnnotation(Column.class).primaryKey()).map(f -> getColumnName(type, f)).collect(Collectors.joining(",\n"));
        primaryKeyStr = primaryKeyStr.equals("") ? "" : String.format("\n PRIMARY KEY (%s)", primaryKeyStr);
        //索引
        String indexStr = Optional.of(type.getAnnotationsByType(Index.class)).map(indices -> Arrays.stream(indices).map(index -> {
            String columnNames = Arrays.stream(index.columnList().split(",")).map(f -> getColumnName(type, f)).collect(Collectors.joining(","));
            String method = index.method().getName().equals("") ? "" : "USING " + index.method().getName();
            String comment = index.comment().equals("") ? "" : "COMMENT '" + index.comment() + "'";
            return String.format("\n %s KEY %s(%s) %s %s", index.type().getName(), index.name(), columnNames, method, comment);
        }).collect(Collectors.joining(",\n"))).orElse("");

        String tableDeclare = Lists.newArrayList(columnStr, primaryKeyStr, indexStr).stream().filter(StringUtils::isNotBlank).collect(Collectors.joining(","));
        tableDeclare = table.toUpperCase() ? tableDeclare.toUpperCase() : tableDeclare;
        return String.format("CREATE TABLE %s(\n%s\n) COMMENT='%s'", tableName, tableDeclare, table.comment());
    }

    private static String getColumnName(Class<?> type, Field field) {
        Table table = type.getAnnotation(Table.class);
        Column column = field.getAnnotation(Column.class);
        return StringUtils.defaultIfBlank(column.name(), table.underScoreCase() ? converter.convert(field.getName()) : field.getName());
    }

    private static String getColumnName(Class<?> type, String fieldName) {
        for (Field field : type.getDeclaredFields()) {
            if (field.getName().equals(fieldName)) {
                Table table = type.getAnnotation(Table.class);
                Column column = field.getAnnotation(Column.class);
                return StringUtils.defaultIfBlank(column.name(), table.underScoreCase() ? converter.convert(field.getName()) : field.getName());
            }
        }
        return null;
    }

    private static String mappingType(Class<?> filedType, Column column) {
        if (column.type() != ColumnType.AUTO) {
            switch (column.type()) {
                //数值类型
                case TINYINT:
                    return String.format(" TINYINT(%s)", column.length());
                case SMALLINT:
                    return String.format(" SMALLINT(%s)", column.length());
                case MEDIUMINT:
                    return String.format(" MEDIUMINT(%s)", column.length());
                case INT:
                    return String.format(" INT(%s)", column.length());
                case INTEGER:
                    return String.format(" INTEGER(%s)", column.length());
                case BIGINT:
                    return String.format(" BIGINT(%s)", column.length());
                //字符串类型
                case CHAR:
                    return String.format(" CHAR(%s)", column.length());
                case VARCHAR:
                    return String.format(" VARCHAR(%s)", column.length());
                //日期和时间类型
                case DATE:
                    return " DATE";
                case TIME:
                    return " TIME";
                case DATETIME:
                    return String.format(" DATETIME(%s)", column.length());
                case TIMESTAMP:
                    return String.format(" TIMESTAMP(%s)", column.length());
                case YEAR:
                    return " YEAR";
                //浮点数值
                case FLOAT:
                    return String.format(" FLOAT(%s,%s)", column.length(), column.decimals());
                case DOUBLE:
                    return String.format(" DOUBLE(%s,%s)", column.length(), column.decimals());
                case DECIMAL:
                    return String.format(" DECIMAL(%s,%s)", column.length(), column.decimals());
                //二进制
                case BLOB:
                    return " BLOB";
                case TINYBLOB:
                    return " TINYBLOB";
                case MEDIUMBLOB:
                    return " MEDIUMBLOB";
                case LONGBLOB:
                    return " LONGBLOB";
                //文本类型
                case TEXT:
                    return " TEXT";
                case TINYTEXT:
                    return " TINYTEXT";
                case MEDIUMTEXT:
                    return " MEDIUMTEXT";
                case LONGTEXT:
                    return " LONGTEXT";
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
            return " DOUBLE(" + column.length() + "," + column.decimals() + ")";
        }
        if (filedType == Float.class || filedType == float.class) {
            return " FLOAT(" + column.length() + "," + column.decimals() + ")";
        }
        if (filedType == BigDecimal.class) {
            return " DECIMAL(" + column.length() + "," + column.decimals() + ")";
        }
        if (filedType == Date.class || filedType == java.sql.Date.class) {
            return " DATE";
        }
        return null;
    }

}
