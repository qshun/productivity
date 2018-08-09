package com.seriousplay.productivity.util;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 查询参数生成
 *
 * @author changqingshun
 */
public class MapBuilder<T> {
    private Map<String, T> map;

    MapBuilder(Map<String, T> paramMap) {
        super();
        this.map = paramMap;
    }

    MapBuilder() {
        super();
        this.map = new HashMap<>();
    }

    /**
     * @param initSize map初始size
     * @param <T>      泛型
     * @return this
     */
    public static <T> MapBuilder<T> start(int initSize) {
        Map<String, T> map = new HashMap<>(initSize);
        return new MapBuilder<>(map);
    }

    /**
     * @param initSize map 初始大小
     * @param key      key
     * @param value    value
     * @param <T>      泛型
     * @return this
     */
    public static <T> MapBuilder<T> start(int initSize, String key, T value) {
        Map<String, T> map = new HashMap<>(initSize);
        return new MapBuilder<>(map).put(key, value);
    }

    /**
     * @param key   key
     * @param value value
     * @param <T>   泛型
     * @return this
     */
    public static <T> MapBuilder<T> start(String key, T value) {
        return MapBuilder.start(10, key, value);
    }

    /**
     * @param <T> 泛型
     * @return this
     */
    public static <T> MapBuilder<T> start() {
        return new MapBuilder<T>();
    }

    /**
     * @param map map
     * @param <T> 泛型
     * @return this
     */
    public static <T> MapBuilder<T> start(Map<String, T> map) {
        if (map != null) {
            return new MapBuilder<T>(map);
        } else {
            return start();
        }
    }

    /**
     * 添加参数
     *
     * @param name  参数名
     * @param value 参数值
     * @return this
     */
    public MapBuilder<T> put(String name, T value) {
        if (StringUtils.isNoneBlank(name)) {
            this.map.put(name, value);
        }
        return this;

    }

    /***
     *删除key
     * @param key key
     * @return this
     */
    public MapBuilder<T> remove(String key) {
        this.map.remove(key);
        return this;
    }

    /**
     * 清空map
     *
     * @return this
     */
    public MapBuilder<T> clear() {
        this.map.clear();
        return this;
    }

    /**
     * 获取map大小
     *
     * @return this
     */
    public int size() {
        return this.map.size();
    }

    /**
     * 获取map对象
     *
     * @return map
     */
    public Map<String, T> get() {
        return this.map;
    }
}
