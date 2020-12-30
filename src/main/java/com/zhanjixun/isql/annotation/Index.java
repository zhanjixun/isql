package com.zhanjixun.isql.annotation;


import java.lang.annotation.*;

/**
 * 创建索引
 * 默认索引名称为 idx_column
 *
 * @author :zhanjixun
 * @date : 2019/12/20 09:55
 * @contact :zhanjixun@qq.com
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Index.List.class)
public @interface Index {
    /**
     * 索引名称 默认索引名称为 idx_column
     */
    String name() default "";

    /**
     * 列名 英文逗号隔开
     */
    String columnList();

    /**
     * 索引类型
     */
    IndexType type() default IndexType.NORMAL;

    /**
     * 索引方法
     */
    IndexMethod method() default IndexMethod.EMPTY;

    /**
     * 备注
     */
    String comment() default "";

    //指定多个时使用
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        Index[] value() default {};
    }
}
