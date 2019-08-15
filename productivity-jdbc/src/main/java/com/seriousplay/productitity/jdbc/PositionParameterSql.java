package com.seriousplay.productitity.jdbc;

import com.seriousplay.productitity.jdbc.query.AbstractSqlBuilder;
import com.seriousplay.productitity.jdbc.query.functions.SqlFunction;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class PositionParameterSql extends AbstractSqlBuilder<PositionParameterSql> {
    private static final Pattern PARAMETER_PATTERN = Pattern.compile("([\\?|#])\\d+");
    private String originalSql;
    private List<Object> parameters;

    PositionParameterSql() {
        super();
        parameters = new LinkedList<>();
    }

    public String getOriginalSql() {
        return originalSql;
    }

    public PositionParameterSql setOriginalSql(String originalSql) {
        this.originalSql = originalSql;
        return this;
    }


    public List<Object> getParameters() {
        return parameters;
    }


    @Override
    public PositionParameterSql getSelf() {
        return this;
    }

    @Override
    public String toString() {
        return "PositionParameterSql{" + "originalSql='" + originalSql + '\'' + ", parsedSql=" + sql + ", parameters=" + parameters + '}';
    }

    /***
     *
     * @param sql
     * @param arg
     * @return
     */
    public static PositionParameterSql parseSql(String sql, Object... arg) {
        Matcher matcher = PARAMETER_PATTERN.matcher(sql);
        StringBuffer stringBuffer = new StringBuffer(sql.length());
        PositionParameterSql parameterSql = new PositionParameterSql().setOriginalSql(sql).setSql(stringBuffer);
        while (matcher.find()) {
            String gruop = matcher.group();
            String valueType = gruop.substring(0, 1);
            int position = Integer.parseInt(matcher.group().substring(1));
            if (position <= 1) {
                //抛异常
            }
            if ("?".equals(valueType)) {
                if (arg.length > position - 1) {
                    Object val = arg[position - 1];
                    matcher.appendReplacement(stringBuffer, "");
                    parameterSql.appendParameter(val);
                }
            } else if ("#".equals(valueType)) {
                if (arg.length > position - 1) {
                    Object val = arg[position - 1];
                    if (val instanceof SqlFunction) {
                        SqlFunction sqlFunction = (SqlFunction) val;
                        matcher.appendReplacement(stringBuffer, sqlFunction.getFunction());
                    } else {
                        matcher.appendReplacement(stringBuffer, val.toString());
                    }
                }
            }
        }
        matcher.appendTail(stringBuffer);
        return parameterSql;
    }

    /**
     * @param sql
     * @param arg
     * @return
     */
    public static PositionParameterSql parseSql(String sql, List<Object> arg) {
        Matcher matcher = PARAMETER_PATTERN.matcher(sql);
        StringBuffer stringBuffer = new StringBuffer(sql.length());
        PositionParameterSql parameterSql = new PositionParameterSql().setOriginalSql(sql).setSql(stringBuffer);
        while (matcher.find()) {
            String gruop = matcher.group();
            String valueType = gruop.substring(0, 1);
            int position = Integer.parseInt(matcher.group().substring(1));
            if (position <= 1) {
                //抛异常
            }
            if ("?".equals(valueType)) {
                if (arg.size() > position - 1) {
                    Object val = arg.get(position - 1);
                    matcher.appendReplacement(stringBuffer, "");
                    parameterSql.appendParameter(val);
                }
            } else if ("#".equals(valueType)) {
                if (arg.size() > position - 1) {
                    Object val = arg.get(position - 1);
                    if (val instanceof SqlFunction) {
                        SqlFunction sqlFunction = (SqlFunction) val;
                        matcher.appendReplacement(stringBuffer, sqlFunction.getFunction());
                    } else {
                        matcher.appendReplacement(stringBuffer, val.toString());
                    }

                }
            }
        }
        matcher.appendTail(stringBuffer);
        return parameterSql;
    }
}
