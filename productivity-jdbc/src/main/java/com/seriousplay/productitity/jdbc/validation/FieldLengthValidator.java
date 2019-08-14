package com.seriousplay.productitity.jdbc.validation;

import java.math.BigDecimal;

/**
 *
 */
@FunctionalInterface
public interface FieldLengthValidator {
    /**
     * @param value
     * @param length
     * @return
     */
    boolean validate(Object value, int... length);

    /**
     * 验证short 类型数字
     *
     * @param value
     * @param length
     * @return
     */
    static boolean validateShortValue(Object value, int... length) {
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
    static boolean validateIntValue(Object value, int... length) {
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
    static boolean validateLongValue(Object value, int... length) {
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
    static boolean validateFloatValue(Object value, int... length) {
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
    static boolean validateDoubleValue(Object value, int... length) {
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
    static boolean validateDecimalValue(Object value, int... length) {
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
    static boolean validateStringValue(Object value, int... length) {
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
