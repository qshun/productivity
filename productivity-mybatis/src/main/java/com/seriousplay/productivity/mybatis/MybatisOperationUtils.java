package com.seriousplay.productivity.mybatis;

import java.util.Map;

/**
 * @author changqingshun
 */
public class MybatisOperationUtils {
    /**
     * 更新操作
     *
     * @param namespace
     * @param mapper
     * @return
     */
    public static MyBatisUpdateOperation updateOperation(String namespace, String mapper) {
        return new MyBatisUpdateOperation(namespace,mapper);
    }

    /**
     * 更新操作
     *
     * @param namespace
     * @param mapper
     * @param paramMap
     * @return
     */
    public static MyBatisUpdateOperation updateOperation(String namespace, String mapper, Map<String, Object> paramMap) {
        return updateOperation(namespace, mapper).param(paramMap);
    }

    /**
     * 查询列表
     *
     * @param namespace
     * @param mapper
     * @return
     */
    public static MybatisSelectOperation selectList(String namespace, String mapper) {
        return new MybatisSelectOperation(namespace,mapper);
    }

    /**
     * 查询map
     *
     * @param namespace
     * @param mapper
     * @param mapKey
     * @return
     */
    public static MybatisSelectOperation selectMap(String namespace, String mapper, String mapKey) {
        return selectList(namespace, mapper).mapKey(mapKey);
    }

    /**
     * 查询单条数据
     *
     * @param namespace
     * @param mapper
     * @return
     */
    public static MybatisSelectOperation selectOne(String namespace, String mapper) {
        return selectList(namespace, mapper);
    }

}
