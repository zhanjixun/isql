package com.zhanjixun.isql.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author :zhanjixun
 * @date : 2019/12/20 09:35
 * @contact :zhanjixun@qq.com
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
    /**
     * 列名
     */
    String name() default "";

    /**
     * 列数据类型 默认自动匹配
     */
    ColumnType type() default ColumnType.AUTO;

    /**
     * 列默认值
     *
     * @return
     */
    String defaultValue() default "default";

    /**
     * 长度
     */
    int length() default 255;

    /**
     * 小数点
     * 支持浮点型和定点型
     */
    int decimals() default 0;

    /**
     * 是否可以为null
     */
    boolean nullable() default true;

    /**
     * 是否主键 一个实体可以多个字段指定为主键
     */
    boolean primaryKey() default false;

    /**
     * 注释
     */
    String comment() default "";

    /**
     * 无符号
     * 支持数值类型
     */
    boolean unsigned() default false;

    /**
     * 自动递增
     * 支持整数类型
     */
    boolean autoIncrement() default false;

    /**
     * 字符集
     */
    String charset() default "";
}