package com.seriousplay.productitity.jdbc.query;

import com.seriousplay.productitity.jdbc.query.functions.SqlFunction;

/**
 *
 */
public class FunctionCriterionBulider extends AbstractCriterionBuilder<FunctionCriterionBulider> {
    private int functionCount;

    public FunctionCriterionBulider() {
        super();
        functionCount = 0;
    }

    public static FunctionCriterionBulider start(SqlFunction function) {
        return new FunctionCriterionBulider()
                .func(function);
    }


    /**
     * @param function
     * @return
     */
    protected FunctionCriterionBulider func(SqlFunction function) {
        this.operator(function.getFunction()).leftParentheses();
        return getSelf();
    }

    /**
     * @param
     * @return
     */
    public FunctionCriterionBulider func(FunctionCriterionBulider sqlFunctionBuilder) {
        if (functionCount++ > 0) {
            this.comma();
        }
        this.sql.append(sqlFunctionBuilder.rightParentheses().getSql());
        this.parameters.addAll(sqlFunctionBuilder.parameters);
        return getSelf();
    }

    @Override
    public Criterion build() {
        this.rightParentheses();
        return super.build();
    }

    @Override
    public FunctionCriterionBulider getSelf() {
        return this;
    }
}
