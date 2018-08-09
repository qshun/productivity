package com.seriousplay.productivity.mybatis;

/**
 *
 */
public class MybatisAppSelectOperation extends MybatisSelectOperation {
    public MybatisAppSelectOperation(String namespace, String mapper) {
        super(namespace, mapper);
        this.rowBounds(null, 20);
    }

    public MybatisAppSelectOperation() {
        super();
    }

    @Override
    public MybatisSelectOperation rowBounds(Integer offset, Integer limit) {
        return super.rowBounds(null, limit);
    }
}
