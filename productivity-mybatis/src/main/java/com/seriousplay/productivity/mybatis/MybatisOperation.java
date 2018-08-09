package com.seriousplay.productivity.mybatis;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public abstract class MybatisOperation {
    private String mapper;
    private String namespace;
    private Map<String, Object> paramMap = new HashMap<>(20);

    public  MybatisOperation(String mapper, String namespace) {
        this();
        this.mapper = mapper;
        this.namespace = namespace;
    }

    public MybatisOperation() {
        super();
    }

    public String getMapper() {
        return mapper;
    }

    public void setMapper(String mapper) {
        this.mapper = mapper;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    /**
     * 获取sqlMapper全拼
     *
     * @return
     */
    public String getSqlMapper() {
        return StringUtils.isNotBlank(namespace) ? namespace + "." + mapper : mapper;
    }

    /**
     * 添加查询参数
     *
     * @param key 参数名
     * @param val 参数值
     * @return
     */
    public <T extends MybatisOperation> T param(String key, Object val) {
        if (StringUtils.isNotBlank(key)) {
            paramMap.put(key, val);
        }
        return (T) this;
    }

    /**
     * 删除参数
     *
     * @param key 参数key
     * @return
     */
    public <T extends MybatisOperation> T removeParam(String key) {
        if (StringUtils.isNotEmpty(key)) {
            this.paramMap.remove(key);
        }
        return (T) this;
    }

    /**
     * 添加参数
     *
     * @param paramMap 参数map
     * @return
     */
    public <T extends MybatisOperation> T param(Map<String, Object> paramMap) {
        if (paramMap != null) {
            this.paramMap.putAll(paramMap);
        }
        return (T) this;
    }

    /**
     * 设置命名命名
     *
     * @param namespace
     * @return
     */
    public <T extends MybatisOperation> T namespace(String namespace) {
        this.setNamespace(namespace);
        return (T) this;
    }

    /**
     * 设置mapper
     *
     * @param mapper
     * @return
     */
    public <T extends MybatisOperation> T mapper(String mapper) {
        this.setMapper(mapper);
        return (T) this;
    }

    /**
     * 重置
     *
     * @return
     */
    public <T extends MybatisOperation> T reset() {
        this.setMapper(null);
        this.setNamespace(null);
        return (T) this.clearParamMap();

    }

    /**
     * 清除查询参数
     *
     * @return
     */
    public <T extends MybatisOperation> T clearParamMap() {
        this.paramMap.clear();
        return (T) this;
    }
}
