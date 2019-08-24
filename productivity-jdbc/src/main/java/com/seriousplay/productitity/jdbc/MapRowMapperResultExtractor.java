package com.seriousplay.productitity.jdbc;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

/**
 *
 *
 * @param <K>
 * @param <V>
 */
public class MapRowMapperResultExtractor<K, V> implements ResultSetExtractor<Map<K, V>> {
    private Function<V, K> keyExtractor;
    private RowMapper<V> rowMapper;

    public MapRowMapperResultExtractor(Function<V, K> keyExtractor, RowMapper<V> rowMapper) {
        super();
        this.keyExtractor = keyExtractor;
        this.rowMapper = rowMapper;
    }

    @Override
    public Map<K, V> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<K, V> map = new LinkedHashMap<>();
        int rownum = 0;
        while (rs.next()) {
            V value = rowMapper.mapRow(rs, rownum++);
            K key = keyExtractor.apply(value);
            map.put(key, value);
        }
        return map;
    }
}
