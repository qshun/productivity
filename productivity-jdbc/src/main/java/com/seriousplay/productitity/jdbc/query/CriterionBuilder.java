package com.seriousplay.productitity.jdbc.query;

/**
 *
 */
public class CriterionBuilder extends AbstractCriterionBuilder<CriterionBuilder> {

    /**
     *
     */
    public CriterionBuilder() {
        super();
    }

    public CriterionBuilder getSelf() {
        return this;
    }

    public CriterionBuilder func(FunctionCriterionBulider fun) {
        this.sql.append(fun.getSql());
        this.parameters.addAll(fun.parameters);
        return getSelf();
    }
}
