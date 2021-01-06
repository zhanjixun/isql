package com.zhanjixun.isql;

import java.util.List;

/**
 * @author zhanjixun
 * @date 2020-12-30 20:49:23
 */
public interface ILazyNavicat {

    void newTable(Class<?> type);

    void insert(List<?> list);

    void insert(Object object);

}
