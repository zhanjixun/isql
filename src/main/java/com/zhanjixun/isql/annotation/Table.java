package com.zhanjixun.isql.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据表映射实体
 *
 * @author :zhanjixun
 * @date : 2019/12/20 09:52
 * @contact :zhanjixun@qq.com
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
    /**
     * 表名前缀
     * 例如tb_
     */
    String prefix() default "";

    /**
     * 表名
     **/
    String name() default "";

    /**
     * 注释
     **/
    String comment() default "";

    /**
     * 转换Java的驼峰命名法为下划线命名法
     */
    boolean underScoreCase() default true;

    /**
     * 是否大写字段名
     */
    boolean toUpperCase() default false;
    

}