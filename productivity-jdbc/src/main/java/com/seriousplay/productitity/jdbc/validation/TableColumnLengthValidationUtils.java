package com.seriousplay.productitity.jdbc.validation;

import com.seriousplay.productitity.jdbc.metadata.TableColumnMetaData;
import org.springframework.validation.Errors;

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
        VALIDATOR_MAP.put("SMALLINT", FieldLengthValidator::validateShortValue);
        VALIDATOR_MAP.put("INT", FieldLengthValidator::validateIntValue);
        VALIDATOR_MAP.put("INTEGER", FieldLengthValidator::validateIntValue);
        VALIDATOR_MAP.put("BIGINT", FieldLengthValidator::validateLongValue);
        VALIDATOR_MAP.put("FLOAT", FieldLengthValidator::validateFloatValue);
        VALIDATOR_MAP.put("DOUBLE", FieldLengthValidator::validateDoubleValue);
        VALIDATOR_MAP.put("DECIMAL", FieldLengthValidator::validateDecimalValue);
        VALIDATOR_MAP.put("CHAR", FieldLengthValidator::validateStringValue);
        VALIDATOR_MAP.put("VARCHAR", FieldLengthValidator::validateStringValue);
        VALIDATOR_MAP.put("NVARCHAR", FieldLengthValidator::validateStringValue);
        VALIDATOR_MAP.put("NCHAR", FieldLengthValidator::validateStringValue);
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


}
