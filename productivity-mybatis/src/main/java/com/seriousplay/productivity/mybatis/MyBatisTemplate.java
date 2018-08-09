package com.seriousplay.productivity.mybatis;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class MyBatisTemplate {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 更新操作
     *
     * @param updateOperation
     * @return
     */
    public int update(MyBatisUpdateOperation updateOperation) {
        return this.sqlSessionTemplate.update(updateOperation.getSqlMapper(), updateOperation.getParamMap());
    }

    /**
     * 查询列表操作
     *
     * @param selectOperation
     * @param <T>
     * @return
     */
    public <T> List<T> selectList(MybatisSelectOperation selectOperation) {
        RowBounds rowBounds = selectOperation.getRowBounds();
        if (rowBounds != null) {
            return this.sqlSessionTemplate.selectList(selectOperation.getSqlMapper(), selectOperation.getParamMap(), rowBounds);
        } else {
            return this.sqlSessionTemplate.selectList(selectOperation.getSqlMapper(), selectOperation.getParamMap());

        }

    }

    /**
     * 查询列表map
     *
     * @param selectOperation
     * @param <T>
     * @return
     */
    public <T> Map<String, T> selectMap(MybatisSelectOperation selectOperation) {
        if (StringUtils.isNotBlank(selectOperation.getMapKey())) {
            RowBounds rowBounds = selectOperation.getRowBounds();
            if (selectOperation.getRowBounds() != null) {
                return this.sqlSessionTemplate.selectMap(selectOperation.getSqlMapper(), selectOperation.getParamMap(), selectOperation.getMapKey(), rowBounds);
            } else {
                return this.sqlSessionTemplate.selectMap(selectOperation.getSqlMapper(), selectOperation.getParamMap(), selectOperation.getMapKey());
            }

        }
        return null;
    }

    /**
     * 查询单条记录
     *
     * @param selectOperation
     * @param <T>
     * @return
     */
    public <T> T selectOne(MybatisSelectOperation selectOperation) {

        return this.sqlSessionTemplate.selectOne(selectOperation.getSqlMapper(), selectOperation.getParamMap());
    }
}
