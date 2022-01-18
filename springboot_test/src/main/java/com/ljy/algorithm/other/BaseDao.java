package com.ljy.algorithm.other;/**
 * Project: springboot_test
 * Package: com.ljy.algorithm.other
 * Version: 1.0
 * Created by ljy on 2022-1-17 10:18
 */

/**
 * @ClassName
 * @Author: ljy on 2022-1-17 10:18
 * @Version: 1.0
 * @Description
 */
public interface BaseDao<T> {
    public T get(String id);
}
