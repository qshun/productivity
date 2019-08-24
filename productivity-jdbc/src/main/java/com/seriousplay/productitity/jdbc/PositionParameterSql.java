package com.seriousplay.productitity.jdbc;

import com.seriousplay.productitity.jdbc.query.AbstractCriterionBuilder;
import com.seriousplay.productitity.jdbc.query.functions.SqlFunction;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.StatementCreatorUtils;
import org.springframework.util.CollectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class PositionParameterSql extends AbstractCriterionBuilder<PositionParameterSql> implements PreparedStatementCreator {
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

    @Override
    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        PreparedStatement ps = con.prepareStatement(sql.toString());
        if (!CollectionUtils.isEmpty(parameters)) {
            Iterator<Object> it = this.parameters.iterator();
            for (int i = 1; it.hasNext(); i++) {
                Object value = it.next();
                int sqlType = StatementCreatorUtils.javaTypeToSqlParameterType(value.getClass());
                StatementCreatorUtils.setParameterValue(ps, i, sqlType, value);
            }
        }
        return ps;
    }
}
