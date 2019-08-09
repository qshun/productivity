package com.seriousplay.productitity.jdbc;

import org.springframework.validation.Errors;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 数据类型及长度验证
 */
public abstract class TableColumnLengthValidationUtils {

    static final Map<String, FieldLengthValidator> VALIDATOR_MAP;
    /***
     *
     */
    public static String ERROR_CODE_DATA_TOO_LONG = "data_too_long";
    /**
     *
     */
    public static String ERROR_CODE_DATA_REQUIRED = "data_required";
    public static String ERROR_MESSAGE = "%s格式有误";

    static {
        VALIDATOR_MAP = new HashMap<>();
        VALIDATOR_MAP.put("SMALLINT", TableColumnLengthValidationUtils::validateShortValue);
        VALIDATOR_MAP.put("INT", TableColumnLengthValidationUtils::validateIntValue);
        VALIDATOR_MAP.put("INTEGER", TableColumnLengthValidationUtils::validateIntValue);
        VALIDATOR_MAP.put("BIGINT", TableColumnLengthValidationUtils::validateLongValue);
        VALIDATOR_MAP.put("FLOAT", TableColumnLengthValidationUtils::validateFloatValue);
        VALIDATOR_MAP.put("DOUBLE", TableColumnLengthValidationUtils::validateDoubleValue);
        VALIDATOR_MAP.put("DECIMAL", TableColumnLengthValidationUtils::validateDecimalValue);
        VALIDATOR_MAP.put("CHAR", TableColumnLengthValidationUtils::validateStringValue);
        VALIDATOR_MAP.put("VARCHAR", TableColumnLengthValidationUtils::validateStringValue);
        VALIDATOR_MAP.put("NVARCHAR", TableColumnLengthValidationUtils::validateStringValue);
        VALIDATOR_MAP.put("NCHAR", TableColumnLengthValidationUtils::validateStringValue);
    }

    /**
     * @param column
     * @param value
     * @return
     */
    public static void validate(Object value, TableColumnMetaData column, Errors errors) {
        if (column == null || column == null || errors == null) {
            return;
        }
        String dataType = column.getColumnType();
        int lengthstart = dataType.indexOf("(");
        int lengthend = dataType.indexOf(")");
        //字段非空校验
        if (!column.isNullable() && !column.isGeneratedValue()) {
            if (value == null) {
                errors.rejectValue(column.getProperty(), ERROR_CODE_DATA_REQUIRED, String.format("%s是必填项！", column.getComment()));
            }
        }
        //验证字段合法性
        int[] dataLength = null;
        String type = null;
        if (lengthstart > 0 && lengthend > 1) {
            type = dataType.substring(0, lengthstart).toUpperCase(Locale.US);
            String length[] = dataType.substring(lengthstart + 1, lengthend).split(",");
            dataLength = new int[length.length];
            for (int i = 0; i < length.length; i++) {
                dataLength[i] = Integer.parseInt(length[i]);
            }
        } else {
            type = dataType;
        }
        FieldLengthValidator validator = VALIDATOR_MAP.get(type);
        boolean rejected = validator != null && !validator.validate(value, dataLength);
        if (rejected) {
            errors.rejectValue(column.getProperty(), ERROR_CODE_DATA_TOO_LONG, String.format(ERROR_MESSAGE, column.getComment()));
        }

    }

    /**
     * 验证short 类型数字
     *
     * @param value
     * @param length
     * @return
     */
    public static boolean validateShortValue(Object value, int... length) {
        if (value == null) {
            return true;
        }
        if (value instanceof Short) {
            return true;
        }
        if (value instanceof CharSequence) {
            try {
                Short val = Short.parseShort(value.toString());
                return val != null;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    /**
     * 验证int数字类型
     *
     * @param value  数值
     * @param length 长度
     * @return
     */
    public static boolean validateIntValue(Object value, int... length) {
        if (value == null) {
            return true;
        }
        if (value instanceof Integer) {
            return true;
        }
        if (value instanceof CharSequence) {
            try {
                Integer val = Integer.parseInt(value.toString());
                return val != null;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    /**
     * 验证long数字类型
     *
     * @param value  数值
     * @param length 长度
     * @return
     */
    public static boolean validateLongValue(Object value, int... length) {
        if (value == null) {
            return true;
        }
        if (value instanceof Long) {
            return true;
        }
        if (value instanceof CharSequence) {
            try {
                Long val = Long.parseLong(value.toString());
                return val != null;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    /**
     * 验证float数字类型
     *
     * @param value  数值
     * @param length 长度
     * @return
     */
    public static boolean validateFloatValue(Object value, int... length) {
        if (value == null) {
            return true;
        }
        if (value instanceof Float) {
            return true;
        }
        if (value instanceof CharSequence) {
            try {
                Float val = Float.parseFloat(value.toString());
                return val != null;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    /**
     * 验证double 数据类型
     *
     * @param value  数值
     * @param length 长度
     * @return
     */
    public static boolean validateDoubleValue(Object value, int... length) {
        if (value == null) {
            return true;
        }
        if (value instanceof Double) {
            return true;
        }
        if (value instanceof CharSequence) {
            try {
                Double val = Double.parseDouble(value.toString());
                return val != null;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    /**
     * 验证decimal字段值
     *
     * @param value  验证值
     * @param length 长度[20,6]数据总长度为20，且保留6位小数
     * @return
     */
    public static boolean validateDecimalValue(Object value, int... length) {
        if (value == null) {
            return true;
        }
        if (length == null || length.length != 2) {
            return true;
        }
        BigDecimal bigNum = null;
        if (value instanceof Number) {
            if (value instanceof BigDecimal) {
                bigNum = (BigDecimal) value;
            } else {
                bigNum = new BigDecimal(value.toString()).stripTrailingZeros();
            }
        } else if (value instanceof CharSequence) {
            bigNum = new BigDecimal(value.toString());
        }
        if (bigNum == null) {
            return false;
        }
        int maxIntegerLength = length[0] - length[1];
        int maxFractionLength = length[1];
        int integerPartLength = bigNum.precision() - bigNum.scale();
        int fractionPartLength = bigNum.scale() < 0 ? 0 : bigNum.scale();

        return (maxIntegerLength >= integerPartLength && maxFractionLength >= fractionPartLength);
    }

    /**
     * 验证字符串长度
     *
     * @param value  需验证的字符串
     * @param length 字符串长度
     * @return
     */
    public static boolean validateStringValue(Object value, int... length) {
        if (value == null) {
            return true;
        }
        if (value instanceof CharSequence && length.length > 0) {
            CharSequence charSequence = (CharSequence) value;
            return charSequence.length() <= length[0];
        }
        return false;
    }

}
