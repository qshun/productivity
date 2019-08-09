package com.seriousplay.productitity.jdbc;

import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class PositionParameterSql {
    private static final Pattern PARAMETER_PATTERN = Pattern.compile("([\\?|#])\\d+");
    private String originalSql;
    private StringBuffer parsedSql;
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

    public StringBuffer getParsedSql() {
        return parsedSql;
    }

    public PositionParameterSql setParsedSql(StringBuffer parsedSql) {
        this.parsedSql = parsedSql;
        return this;
    }

    public List<Object> getParameters() {
        return parameters;
    }

    public PositionParameterSql setParameters(List<Object> parameters) {
        this.parameters = parameters;
        return this;
    }

    void appendParameters(Collection<Object> parameters) {
        Iterator<Object> iterator = parameters.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            if (i > 0) {
                parsedSql.append(",");
            }
            Object value = iterator.next();
            appendParameter(value);
            i++;
        }
    }

    void appendParameters(Object[] parameters) {
        for (int i = 0; i < parameters.length; i++) {
            Object value = parameters[i];
            if (i > 0) {
                parsedSql.append(",");
            }
            appendParameter(value);
        }

    }

    void appendParameter(Object parameter) {
        if (parameter instanceof Collection) {
            Collection collection = (Collection) parameter;
            if (! CollectionUtils.isEmpty(collection)) {
                appendParameters(collection);
            }
        } else if (parameter instanceof Object[]) {
            Object[] array = (Object[]) parameter;
            if (array.length > 0) {
                appendParameters(array);
            }
        } else {
            parsedSql.append("?");
            parameters.add(parameter);
        }
    }

    @Override
    public String toString() {
        return "PositionParameterSql{" + "originalSql='" + originalSql + '\'' + ", parsedSql=" + parsedSql + ", parameters=" + parameters + '}';
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
        PositionParameterSql parameterSql = new PositionParameterSql().setOriginalSql(sql).setParsedSql(stringBuffer);
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
        PositionParameterSql parameterSql = new PositionParameterSql().setOriginalSql(sql).setParsedSql(stringBuffer);
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
