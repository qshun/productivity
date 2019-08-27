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

    public static CriterionBuilder getInstance() {
        return new CriterionBuilder();
    }
}

