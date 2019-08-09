package com.seriousplay.productitity.generator;

import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;

/**
 * Created by JacksonGenerator on 6/18/19.
 */

public class MysqlDbTable extends MySqlQuery {

    @Override
    public String[] fieldCustom() {
        return new String[]{"null", "default"};
    }
}