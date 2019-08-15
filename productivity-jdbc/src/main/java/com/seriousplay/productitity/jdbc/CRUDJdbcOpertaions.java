package com.seriousplay.productitity.jdbc;

import com.seriousplay.productitity.jdbc.exceptions.TooManyResultsException;
import com.seriousplay.productitity.jdbc.metadata.TableColumnMetaData;
import com.seriousplay.productitity.jdbc.metadata.TableMetaData;
import com.seriousplay.productitity.jdbc.query.DeleteQuery;
import com.seriousplay.productitity.jdbc.query.SelectQuery;
import com.seriousplay.productitity.jdbc.query.UpdateQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.NotReadablePropertyException;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.util.*;
import java.util.function.Function;

/**
 *
 */
@Repository
public class CRUDJdbcOpertaions {
    /**
     * Default maximum number of entries for this template's SQL cache: 256.
     */
    public static final int DEFAULT_CACHE_LIMIT = 256;
    private volatile int cacheLimit = DEFAULT_CACHE_LIMIT;
    private final Map<String, RowMapper<?>> rowMapperCache = new LinkedHashMap<String, RowMapper<?>>(DEFAULT_CACHE_LIMIT, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, RowMapper<?>> eldest) {
            return size() > cacheLimit;
        }
    };


    private static final Logger logger = LoggerFactory.getLogger(CRUDJdbcOpertaions.class);
    /**
     * 默认批量操作数量
     */
    public static final int DEFAULT_BATCH_SIZE = 500;
    private int batchSize = DEFAULT_BATCH_SIZE;

    public int getBatchSize() {
        return batchSize;
    }

    public CRUDJdbcOpertaions setBatchSize(int batchSize) {
        this.batchSize = batchSize;
        return this;
    }

    @Autowired
    private JdbcOperations jdbcOperations;

    /**
     * @param mappedClass
     * @param sql
     * @param <T>
     * @return
     */
    public <T> T selectOne(Class<T> mappedClass, String sql) {
        return selectOne(mappedClass, sql, null);
    }

    /**
     * @param mappedClass
     * @param sql
     * @param args
     * @param <T>
     * @return
     */
    public <T> T selectOne(Class<T> mappedClass, String sql, Object... args) {
        List<T> list;
        if (args == null || args.length == 0) {
            list = jdbcOperations.query(sql, this.getRowMapper(mappedClass));

        } else {
            list = jdbcOperations.query(sql, this.getRowMapper(mappedClass), args);
        }
        if (CollectionUtils.isEmpty(list)) {
            return null;
        } else if (list.size() > 1) {
            throw new TooManyResultsException("Expected one result (or null) to be returned by selectOne(), but found: " + list.size());
        }
        return list.get(0);

    }

    public JdbcOperations getJdbcOperations() {
        return jdbcOperations;
    }

    /**
     * @param table
     * @param column
     * @param id
     * @param <T>
     * @return
     */
    public <T> T selectById(Class<T> mappedClass, String table, String column, Serializable id) {
        String sql = "select * from " + table + " where " + column + "=?";
        return selectOne(mappedClass, sql, id);
    }

    /**
     * @param mappedClass
     * @param selectQuery
     * @param <T>
     * @return
     */
    public <T> List<T> selectList(Class<T> mappedClass, SelectQuery selectQuery) {
        return selectList(mappedClass, selectQuery.toSQL(), selectQuery.getParamNameValuePairs().values().toArray());
    }

    /**
     * @param mappedClass
     * @param sql
     * @param <T>
     * @return
     */
    public <T> List<T> selectList(Class<T> mappedClass, String sql) {
        return selectList(mappedClass, sql, null);

    }

    /**
     * @param mappedClass
     * @param sql
     * @param args
     * @param <T>
     * @return
     */
    public <T> List<T> selectList(Class<T> mappedClass, String sql, Object... args) {
        if (args == null || args.length == 0) {
            return jdbcOperations.query(sql, getRowMapper(mappedClass));
        }
        return jdbcOperations.query(sql, getRowMapper(mappedClass), args);
    }

    /**
     * @param mappedClass
     * @param selectQuery
     * @param keyExtractor
     * @param <K>
     * @param <V>
     * @return
     */
    public <K, V> Map<K, V> selectMap(Class<V> mappedClass, SelectQuery selectQuery, Function<V, K> keyExtractor) {
        String sql = selectQuery.toSQL();
        return selectMap(mappedClass, sql, keyExtractor, selectQuery.getParameters());

    }

    /**
     * @param mappedClass
     * @param sql
     * @param keyExtractor
     * @param <K>
     * @param <V>
     * @return
     */
    public <K, V> Map<K, V> selectMap(Class<V> mappedClass, String sql, Function<V, K> keyExtractor) {
        return selectMap(mappedClass, sql, keyExtractor, null);
    }

    /**
     * @param mappedClass
     * @param sql
     * @param keyExtractor
     * @param args
     * @param <K>
     * @param <V>
     * @return
     */
    public <K, V> Map<K, V> selectMap(Class<V> mappedClass, String sql, Function<V, K> keyExtractor, Object... args) {
        final Map<K, V> resultMap = new LinkedHashMap<>();
        RowMapper<V> rowMapper = getRowMapper(mappedClass);
        if (args == null || args.length == 0) {
            jdbcOperations.query(sql, rs -> {
                V entity = rowMapper.mapRow(rs, rs.getRow());
                K key = keyExtractor.apply(entity);
                resultMap.put(key, entity);
            });
        } else {
            jdbcOperations.query(sql, rs -> {
                V entity = rowMapper.mapRow(rs, rs.getRow());
                K key = keyExtractor.apply(entity);
                resultMap.put(key, entity);
            }, args);
        }

        return resultMap;
    }

    /**
     * @param entity
     * @param table
     * @param <T>
     * @return
     */
    public <T> int insert(T entity, TableMetaData table) {
        if (table == null) {
            return 0;
        }
        return insert(entity, table.getSchemaName(), table.getTableName(), table.getColumns());
    }

    /**
     * 插入数据
     *
     * @param tableName    表名
     * @param entity       实体对象
     * @param tableColumns 表列定义信息
     * @param <T>
     * @return
     */
    public <T> int insert(T entity, String tableName, TableColumnMetaData[] tableColumns) {
        return insert(entity, null, tableName, tableColumns);
    }

    /**
     * 插入数据
     *
     * @param schemaName
     * @param tableName
     * @param entity
     * @param tableColumns
     * @param <T>
     * @return
     */
    public <T> int insert(T entity, String schemaName, String tableName, TableColumnMetaData[] tableColumns) {
        return insert(entity, schemaName, tableName, tableColumns, false, null);
    }

    /**
     * @param entity
     * @param table
     * @param <T>
     * @return
     */
    public <T> int insertWithSelective(T entity, TableMetaData table) {
        return insertWithSelective(entity, table.getSchemaName(), table.getTableName(), table.getColumns(), null);
    }

    /**
     * @param tableName
     * @param entity
     * @param tableColumns
     * @param <T>
     * @return
     */
    public <T> int insertWithSelective(T entity, String tableName, TableColumnMetaData[] tableColumns) {
        return insertWithSelective(entity, null, tableName, tableColumns, null);
    }

    /**
     * @param schemaName
     * @param tableName
     * @param entity
     * @param tableColumns
     * @param <T>
     * @return
     */
    public <T> int insertWithSelective(T entity, String schemaName, String tableName, TableColumnMetaData[] tableColumns) {
        return insertWithSelective(entity, schemaName, tableName, tableColumns, null);
    }

    /**
     * @param entity
     * @param table
     * @param defaultFieldStrategy
     * @param <T>
     * @return
     */
    public <T> int insertWithSelective(T entity, TableMetaData table, FieldStrategy defaultFieldStrategy) {
        return insertWithSelective(entity, table.getSchemaName(), table.getTableName(), table.getColumns(), defaultFieldStrategy);
    }

    /**
     * @param tableName
     * @param entity
     * @param tableColumns
     * @param defaultFieldStrategy
     * @param <T>
     * @return
     */
    public <T> int insertWithSelective(T entity, String tableName, TableColumnMetaData[] tableColumns, FieldStrategy defaultFieldStrategy) {
        return insertWithSelective(entity, null, tableName, tableColumns, defaultFieldStrategy);
    }

    /**
     * @param schemaName
     * @param tableName
     * @param entity
     * @param tableColumns
     * @param defaultFieldStrategy
     * @param <T>
     * @return
     */
    public <T> int insertWithSelective(T entity, String schemaName, String tableName, TableColumnMetaData[] tableColumns, FieldStrategy defaultFieldStrategy) {
        return insert(entity, schemaName, tableName, tableColumns, true, defaultFieldStrategy);
    }

    /**
     * 批量插入
     *
     * @param entities
     * @param schemaName
     * @param tableName
     * @param tableColumns
     * @param <T>
     * @return
     */
    public <T> List<Integer> batchInsert(Collection<T> entities, String schemaName, String tableName, TableColumnMetaData[] tableColumns) {
        if (CollectionUtils.isEmpty(entities) || tableColumns == null || tableColumns.length == 0) {
            return null;
        }
        Set<TableColumnMetaData> keys = new LinkedHashSet<>(tableColumns.length);
        List<TableColumnMetaData> columnMetaDataList = new ArrayList<>(tableColumns.length);
        processColumn(columnMetaDataList, keys);
        String insertSql = createInsertString(schemaName, tableName, columnMetaDataList, keys);
        int[][] ints = jdbcOperations.batchUpdate(insertSql, entities, batchSize, (ps, entity) -> {
            int i = 1;
            BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(entity);
            for (TableColumnMetaData one : columnMetaDataList) {
                StatementCreatorUtils.setParameterValue(ps, i + 1, SqlTypeValue.TYPE_UNKNOWN, getPropertyValue(one, wrapper));
            }
        });
        ArrayList<Integer> res = new ArrayList<>(entities.size());
        for (int[] anInt : ints) {
            for (int i : anInt) {
                res.add(i);
            }
        }
        return res;
    }

    void processColumn(Collection<TableColumnMetaData> tableColumns, Collection<TableColumnMetaData> keys) {
        for (TableColumnMetaData column : tableColumns) {
            if (column.isGeneratedValue()) {
                keys.add(column);
            } else {
                tableColumns.add(column);
            }
        }
    }

    /**
     * @return
     */
    String createInsertString(String schemaName, String tableName, Collection<TableColumnMetaData> tableColumns, Collection<TableColumnMetaData> keys) {
        if (CollectionUtils.isEmpty(tableColumns)) {
            throw new InvalidDataAccessApiUsageException("Unable to locate columns for table '" + tableName + "' so an insert statement can't be generated");
        }
        StringBuilder insertStatement = new StringBuilder();
        insertStatement.append("INSERT INTO ");
        if (schemaName != null) {
            insertStatement.append(schemaName).append(".");
        }
        insertStatement.append(tableName).append(" (");
        int columnCount = 0;
        for (TableColumnMetaData column : tableColumns) {
            insertStatement.append(column.getColumn()).append(",");
            columnCount++;
        }
        if (columnCount < 1) {
            if (!keys.isEmpty()) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Unable to locate non-key columns for table '" + tableName + "' so an empty insert statement is generated");
                }
            } else {
                throw new InvalidDataAccessApiUsageException("Unable to locate columns for table '" + tableName + "' so an insert statement can't be generated");
            }
        } else {
            insertStatement.deleteCharAt(insertStatement.length() - 1);
        }
        insertStatement.append(") VALUES(");
        for (int i = 0; i < columnCount; i++) {
            if (i > 0) {
                insertStatement.append(", ");
            }
            insertStatement.append("?");
        }
        insertStatement.append(")");
        String sql = insertStatement.toString();
        if (logger.isDebugEnabled()) {
            logger.debug("SQL:" + insertStatement);
        }
        return sql;
    }


    /**
     * @param entity
     * @param schemaName
     * @param tableName
     * @param tableColumns
     * @param applyFieldStrategy
     * @param defaultFieldStrategy
     * @param <T>
     * @return
     */
    public <T> int insert(T entity, String schemaName, String tableName, TableColumnMetaData[] tableColumns, boolean applyFieldStrategy, FieldStrategy defaultFieldStrategy) {
        if (entity == null || tableColumns == null || tableColumns.length == 0) {
            return 0;
        }
        Set<TableColumnMetaData> keys = new LinkedHashSet<>(tableColumns.length);
        List<TableColumnMetaData> columnMetaDataList = new ArrayList<>(tableColumns.length);
        List<Object> param = new ArrayList<>(tableColumns.length);
        BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(entity);
        for (TableColumnMetaData column : tableColumns) {
            if (column.isGeneratedValue()) {
                keys.add(column);
            } else {
                if (applyFieldStrategy) {
                    Object propertyValue = getPropertyValue(column, wrapper);
                    if (isUseThatField(propertyValue, applyFieldStrategy, column.getInsertStrategy(), defaultFieldStrategy)) {
                        param.add(propertyValue);
                        columnMetaDataList.add(column);
                    }
                } else {
                    param.add(null);
                    columnMetaDataList.add(column);
                }
            }
        }
        if (keys.isEmpty() && columnMetaDataList.isEmpty()) {
            return 0;
        }
        String insertStatement = createInsertString(schemaName, tableName, columnMetaDataList, keys);
        //没有生成字段则直接插入数据
        if (keys.isEmpty()) {
            if (!columnMetaDataList.isEmpty()) {
                return jdbcOperations.update(insertStatement, param.toArray());
            } else {
                return 0;
            }
        } else {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            String[] keysArray = new String[keys.size()];
            keys.toArray(keysArray);
            PreparedStatementCreator creator = (con) -> {
                PreparedStatement preparedStatement = con.prepareStatement(insertStatement, keysArray);
                for (int i = 0; i < param.size(); i++) {
                    StatementCreatorUtils.setParameterValue(preparedStatement, i + 1, SqlTypeValue.TYPE_UNKNOWN, param.get(i));
                }
                return preparedStatement;
            };
            int c = jdbcOperations.update(creator, keyHolder);
            if (keysArray.length == 1) {
                wrapper.setPropertyValue(keysArray[0], keyHolder.getKey());
            } else {
                Map<String, Object> map = keyHolder.getKeys();
                int i = 0;
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    wrapper.setPropertyValue(keysArray[i++], entry.getValue());
                }
            }
            return c;
        }
    }

    public Object getPropertyValue(TableColumnMetaData column, BeanWrapper beanWrapper) {
        Object value = null;
        try {
            value = beanWrapper.getPropertyValue(column.getProperty());
        } catch (NotReadablePropertyException e) {
            try {
                value = beanWrapper.getPropertyValue(column.getColumn());
            } catch (NotReadablePropertyException ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
        return value;
    }

    /**
     * @param propertyValue
     * @param applyFieldStrategy
     * @param fieldStrategy
     * @param defaultFieldStrategy
     * @param <T>
     * @return
     */
    <T> boolean isUseThatField(T propertyValue, boolean applyFieldStrategy, FieldStrategy fieldStrategy, FieldStrategy defaultFieldStrategy) {
        if (applyFieldStrategy) {
            if (fieldStrategy != null) {
                return fieldStrategy.accept(propertyValue);
            } else {
                return defaultFieldStrategy != null && defaultFieldStrategy.accept(propertyValue);
            }
        } else {
            return true;
        }

    }

    public <T> int updateSelectiveByKey(T entity, TableMetaData tableMetaData) {
        return updateSelectiveByKey(entity, tableMetaData.getSchemaName(), tableMetaData.getTableName(), tableMetaData.getColumns());
    }

    /**
     * @param tableName
     * @param entity
     * @param tableColumns
     * @param <T>
     * @return
     */
    public <T> int updateSelectiveByKey(T entity, String tableName, TableColumnMetaData[] tableColumns) {
        return updateSelectiveByKey(entity, null, tableName, tableColumns);
    }


    /**
     * @param schemaName
     * @param tableName
     * @param entity
     * @param tableColumns
     * @param <T>
     * @return
     */
    public <T> int updateSelectiveByKey(T entity, String schemaName, String tableName, TableColumnMetaData[] tableColumns) {
        return updateSelectiveByKey(entity, schemaName, tableName, tableColumns, null);
    }

    public <T> int updateSelectiveByKey(T entity, TableMetaData tableMetaData, FieldStrategy defaultFieldStrategy) {
        return updateByKey(entity, tableMetaData.getSchemaName(), tableMetaData.getTableName(), tableMetaData.getColumns(), true, defaultFieldStrategy);
    }

    /**
     * @param schemaName
     * @param tableName
     * @param entity
     * @param tableColumns
     * @param defaultFieldStrategy
     * @param <T>
     * @return
     */
    public <T> int updateSelectiveByKey(T entity, String schemaName, String tableName, TableColumnMetaData[] tableColumns, FieldStrategy defaultFieldStrategy) {
        return updateByKey(entity, schemaName, tableName, tableColumns, true, defaultFieldStrategy);
    }

    /**
     * @param entity
     * @param tableMetaData
     * @param <T>
     * @return
     */
    public <T> int updateByKey(T entity, TableMetaData tableMetaData) {
        return updateByKey(entity, tableMetaData.getSchemaName(), tableMetaData.getTableName(), tableMetaData.getColumns());
    }

    /**
     * @param tableName
     * @param entity
     * @param tableColumns
     * @param <T>
     * @return
     */
    public <T> int updateByKey(T entity, String tableName, TableColumnMetaData[] tableColumns) {
        return updateByKey(entity, null, tableName, tableColumns);
    }

    /**
     * @param entities
     * @param schemaName
     * @param tableName
     * @param tableColumns
     * @param <T>
     * @return
     */
    public <T> List<Integer> batchUpdateByKey(Collection<T> entities, String schemaName, String tableName, TableColumnMetaData[] tableColumns) {
        List<TableColumnMetaData> columnMetaDataList = new ArrayList<>(tableColumns.length);
        Set<TableColumnMetaData> keys = new LinkedHashSet<>(tableColumns.length);
        processColumn(columnMetaDataList, keys);
        String sql = createUpdateString(schemaName, tableName, columnMetaDataList, keys);
        int[][] ints = jdbcOperations.batchUpdate(sql, entities, batchSize, ((ps, argument) -> {
            int i = 0;
            for (TableColumnMetaData one : columnMetaDataList) {
                BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(argument);
                StatementCreatorUtils.setParameterValue(ps, i + 1, SqlTypeValue.TYPE_UNKNOWN, getPropertyValue(one, wrapper));
            }
            for (TableColumnMetaData one : keys) {
                BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(argument);
                StatementCreatorUtils.setParameterValue(ps, i + 1, SqlTypeValue.TYPE_UNKNOWN, getPropertyValue(one, wrapper));
            }
        }));
        ArrayList<Integer> res = new ArrayList<>(entities.size());
        for (int[] anInt : ints) {
            for (int i : anInt) {
                res.add(i);
            }
        }
        return res;
    }

    /**
     * @param schemaName
     * @param tableName
     * @param entity
     * @param tableColumns
     * @param <T>
     * @return
     */
    public <T> int updateByKey(T entity, String schemaName, String tableName, TableColumnMetaData[] tableColumns) {
        return updateByKey(entity, schemaName, tableName, tableColumns, false, null);
    }

    /**
     * @param query
     * @return
     */
    public int update(UpdateQuery query) {
        String sql = query.toSQL();
        Object[] params = query.getParameters();
        if (params == null || params.length == 0) {
            return jdbcOperations.update(sql);
        } else {
            return jdbcOperations.update(sql, params);
        }

    }

    /**
     * @param query
     * @return
     */
    public int delete(DeleteQuery query) {
        String sql = query.toSQL();
        Object[] params = query.getParameters();
        if (params == null || params.length == 0) {
            return jdbcOperations.update(sql);
        } else {
            return jdbcOperations.update(sql, params);
        }

    }

    /**
     * @param schemaName
     * @param tableName
     * @param entity
     * @param tableColumns
     * @param <T>
     * @return
     */
    public <T> int updateByKey(T entity, String schemaName, String tableName, TableColumnMetaData[] tableColumns, boolean applyFieldStrategy, FieldStrategy defaultFieldStrategy) {
        if (entity == null || tableColumns == null || tableColumns.length == 0) {
            return 0;
        }
        List<TableColumnMetaData> columnMetaDataList = new ArrayList<>(tableColumns.length);
        Set<TableColumnMetaData> keys = new LinkedHashSet<>(tableColumns.length);
        List<Object> param = new ArrayList<>(tableColumns.length);
        int columnCount = 0;
        BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(entity);
        for (TableColumnMetaData column : tableColumns) {
            if (column.isGeneratedValue()) {
                keys.add(column);
            } else {
                Object propertyValue = getPropertyValue(column, wrapper);
                if (isUseThatField(propertyValue, applyFieldStrategy, column.getUpdateStrategy(), defaultFieldStrategy)) {
                    param.add(propertyValue);
                    columnMetaDataList.add(column);
                    columnCount++;
                }
            }
        }
        if (columnCount > 0) {
            if (keys.size() > 0) {
                for (TableColumnMetaData key : keys) {
                    param.add(getPropertyValue(key, wrapper));
                }
                String sql = createUpdateString(schemaName, tableName, columnMetaDataList, keys);
                return jdbcOperations.update(sql, param.toArray());
            }

        } else {
            throw new InvalidDataAccessApiUsageException("Unable to locate columns for table '" + tableName + "' so an update statement can't be generated");
        }
        return 0;
    }

    <T> RowMapper<T> getRowMapper(Class<T> mappedClass) {
        String cls = mappedClass.getName();
        synchronized (rowMapperCache) {
            RowMapper rowMapper = rowMapperCache.get(cls);
            if (rowMapper == null) {
                rowMapper = new BeanPropertyRowMapper<T>(mappedClass);
                rowMapperCache.put(cls, rowMapper);
            }
            return rowMapper;
        }
    }

    String createUpdateString(String schemaName, String tableName, Collection<TableColumnMetaData> tableColumns, Collection<TableColumnMetaData> keys) {
        StringBuilder updateStatement = new StringBuilder();
        updateStatement.append("update ");
        if (schemaName != null) {
            updateStatement.append(schemaName).append(".");
        }
        updateStatement.append(tableName);
        updateStatement.append(" set ");
        int i = 0;
        for (TableColumnMetaData column : tableColumns) {
            if (i > 0) {
                updateStatement.append(",");
            }
            updateStatement.append(column.getColumn()).append("=?");
        }
        i = 0;
        if (!CollectionUtils.isEmpty(keys)) {
            updateStatement.append(" where ");
            for (TableColumnMetaData key : keys) {
                if (i > 0) {
                    updateStatement.append(" and ");
                }
                updateStatement.append(key).append(key.getColumn()).append("=?");
            }
        } else {
            throw new InvalidDataAccessApiUsageException("Unable to locate columns for table '" + tableName + "' so an insert statement can't be generated");
        }
        String sql = updateStatement.toString();
        if (logger.isDebugEnabled()) {
            logger.debug("SQL:" + sql);
        }
        return sql;
    }
}
