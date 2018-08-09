package com.seriousplay.productivity.mybatis;

public class MyBatisUpdateOperation extends MybatisOperation {
    public MyBatisUpdateOperation() {
        super();
    }

    public MyBatisUpdateOperation(String mapper, String namespace) {
        super(namespace, mapper);
    }
}
