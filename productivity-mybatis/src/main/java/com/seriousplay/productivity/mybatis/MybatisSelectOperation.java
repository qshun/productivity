package com.seriousplay.productivity.mybatis;

import org.apache.ibatis.session.RowBounds;

public class MybatisSelectOperation extends MybatisOperation {
    private String mapKey;
    private Integer offset;
    private Integer limit;

    public MybatisSelectOperation(String mapper, String namespace) {
        super(namespace, mapper);
    }

    public MybatisSelectOperation() {
        super();
    }

    public String getMapKey() {
        return mapKey;
    }

    public void setMapKey(String mapKey) {
        this.mapKey = mapKey;
    }

    public MybatisSelectOperation rowBounds(Integer offset, Integer limit) {
        if (offset != null && offset >= 0) {
            this.offset = offset;
        }
        if (limit != null && limit > 0) {
            this.limit = limit;
        }
        return this;
    }

    public MybatisSelectOperation mapKey(String mapKey) {
        this.mapKey = mapKey;
        return this;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public RowBounds getRowBounds() {
        if (offset != null && limit != null) {
            return new RowBounds(offset, limit);
        } else if (offset != null) {
            return new RowBounds(offset, RowBounds.NO_ROW_LIMIT);
        } else if (limit != null) {
            return new RowBounds(0, limit);
        }
        return null;
    }

    @Override
    public MybatisOperation reset() {
        super.reset();
        this.mapKey = null;
        this.offset = null;
        this.limit = null;
        return this;
    }
}
