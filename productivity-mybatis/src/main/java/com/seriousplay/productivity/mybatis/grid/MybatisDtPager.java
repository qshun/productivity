package com.seriousplay.productivity.mybatis.grid;

import com.seriousplay.productivity.mybatis.MapperRefresher;
import com.seriousplay.productivity.mybatis.MyBatisTemplate;
import com.seriousplay.productivity.mybatis.MybatisOperationUtils;
import com.seriousplay.productivity.mybatis.MybatisSelectOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author changqingshun
 */
@Component
public class MybatisDtPager implements IDTPager {
    static final Logger logger = LoggerFactory.getLogger(MapperRefresher.class);

    @Autowired
    private MyBatisTemplate myBatisTemplate;

    @Override
    public <T> DTPageHelper<T> query(String namespace, String mapper, Map<String, Object> paramMap) {

        DTPageHelper<T> ret = new DTPageHelper<>();
        try {
            MybatisSelectOperation selectOperation = MybatisOperationUtils.selectList(namespace, mapper)
                    .param(paramMap).param("_count_", true);
            Map<String, Object> count = myBatisTemplate.selectOne(selectOperation);
            long c = Long.parseLong(count.get("_count_").toString());
            ret.setRecordsFiltered(c);
            ret.setRecordsTotal(c);
            if (c > 0) {
                selectOperation.removeParam("_count_");
                int start = (int) paramMap.get("_start_");
                int length = (int) paramMap.get("_length_");
                length = length < 0 ? 100 : length;
                selectOperation.rowBounds(start, length);
                List<T> list = this.myBatisTemplate.selectList(selectOperation);
                ret.setData(list);
            } else {
                ret.setData(Collections.emptyList());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            ret.setData(Collections.emptyList());
            ret.setRecordsTotal(0);
            ret.setRecordsFiltered(0);
        }
        return ret;
    }

    @Override
    public <T> DTPageHelper<T> query(String namespace, String mapper, int start, int length) {
        Map<String, Object> paramMap = new HashMap<>(3);
        paramMap.put("_start_", start);
        paramMap.put("_length_", length);
        return this.query(namespace, mapper, paramMap);
    }
}
