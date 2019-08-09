package com.seriousplay.productitity.jdbc.functions.mysql;

import com.seriousplay.productitity.jdbc.SqlFunction;

public abstract class MysqlFuntions {
    /**
     *
     */
    public enum AggregateFuntion implements SqlFunction {

        /**
         * Return the average value of the argument
         */
        AVG("AVG"),
        /**
         * Return bitwise AND
         */
        BIT_AND("BIT_AND"),
        /**
         * Return bitwise OR
         */
        BIT_OR("BIT_OR"),
        /**
         * Return bitwise XOR
         */
        BIT_XOR("BIT_XOR"),
        /**
         * Return a count of the number of rows returned
         */
        COUNT("COUNT"),
        /**
         * Return a concatenated string
         */
        GROUP_CONCAT("GROUP_CONCAT"),
        /**
         * Return result set as a single JSON array
         */
        JSON_ARRAYAGG("JSON_ARRAYAGG"),
        /**
         * Return result set as a single JSON object
         */
        JSON_OBJECTAGG("JSON_OBJECTAGG"),
        /**
         * Return the maximum value
         */
        MAX("MAX"),
        /**
         * Return the minimum value
         */
        MIN("MIN"),
        /**
         * Return the population standard deviation
         */
        STD("STD"),
        /**
         * Return the population standard deviation
         */
        STDDEV("STDDEV"),
        /**
         * Return the population standard deviation
         */
        STDDEV_POP("STDDEV_POP"),
        /**
         * Return the sample standard deviation
         */
        STDDEV_SAMP("STDDEV_SAMP"),
        /**
         * Return the sum
         */
        SUM("SUM"),
        /**
         * Return the population standard variance
         */
        VAR_POP("VAR_POP"),
        /**
         * Return the sample variance
         */
        VAR_SAMP("VAR_SAMP"),
        /**
         * Return the population standard variance
         */
        VARIANCE("VARIANCE");
        private String function;

        AggregateFuntion(String function) {
            this.function = function;
        }

        @Override
        public String getFunction() {
            return function;
        }
    }

    /**
     *
     */
    public enum CastFuntion implements SqlFunction {

        /**
         * Cast a value as a certain type
         */
        CAST("CAST"),
        /**
         * Cast a value as a certain type
         */
        CONVERT("CONVERT");
        private String function;

        CastFuntion(String function) {
            this.function = function;
        }

        @Override
        public String getFunction() {
            return function;
        }
    }

    /***
     * https://dev.mysql.com/doc/refman/5.7/en/control-flow-functions.html#operator_caseControl Flow Functions
     */
    public enum ControlFlowFunction implements SqlFunction {
        /**
         * Case operator
         * CASE value WHEN [compare_value] THEN result [WHEN [compare_value] THEN result ...] [ELSE result] END
         * <p>
         * CASE WHEN [condition] THEN result [WHEN [condition] THEN result ...] [ELSE result] END
         */
        CASE("case"),
        /**
         * If/else construct
         * IF(expr1,expr2,expr3)
         * <p>
         * If expr1 is TRUE (expr1 <> 0 and expr1 <> NULL), IF() returns expr2. Otherwise, it returns expr3.
         */
        IF("if"),
        /**
         * Null if/else construct
         * IFNULL(expr1,expr2)
         * <p>
         * If expr1 is not NULL, IFNULL() returns expr1; otherwise it returns expr2.
         */
        IFNULL("ifnull"),
        /**
         * Return NULL if expr1 = expr2
         * NULLIF(expr1,expr2)
         * <p>
         * Returns NULL if expr1 = expr2 is true, otherwise returns expr1. This is the same as CASE WHEN expr1 = expr2 THEN NULL ELSE expr1 END.
         */
        NULLIF("nullif");

        private String function;

        ControlFlowFunction(String function) {
            this.function = function;
        }

        @Override
        public String getFunction() {
            return function;
        }
    }

    /**
     *
     */
    public enum DateAndTimeFunction implements SqlFunction {
        /**
         * Add time values (intervals) to a date value
         */
        ADDDATE("ADDDATE"),
        /**
         * Add time
         */
        ADDTIME("ADDTIME"),
        /**
         * Convert from one time zone to another
         */
        CONVERT_TZ("CONVERT_TZ"),
        /**
         * Return the current date
         */
        CURDATE("CURDATE"),
        /**
         * , CURRENT_DATE	Synonyms for CURDATE
         */
        CURRENT_DATE("CURRENT_DATE"),
        /**
         * , CURRENT_TIME	Synonyms for CURTIME
         */
        CURRENT_TIME("CURRENT_TIME"),
        /**
         * , CURRENT_TIMESTAMP	Synonyms for NOW
         */
        CURRENT_TIMESTAMP("CURRENT_TIMESTAMP"),
        /**
         * Return the current time
         */
        CURTIME("CURTIME"),
        /**
         * Extract the date part of a date or datetime expression
         */
        DATE("DATE"),
        /**
         * Add time values (intervals) to a date value
         */
        DATE_ADD("DATE_ADD"),
        /**
         * Format date as specified
         */
        DATE_FORMAT("DATE_FORMAT"),
        /**
         * Subtract a time value (interval) from a date
         */
        DATE_SUB("DATE_SUB"),
        /**
         * Subtract two dates
         */
        DATEDIFF("DATEDIFF"),
        /**
         * Synonym for DAYOFMONTH
         */
        DAY("DAY"),
        /**
         * Return the name of the weekday
         */
        DAYNAME("DAYNAME"),
        /**
         * Return the day of the month (0-31)
         */
        DAYOFMONTH("DAYOFMONTH"),
        /**
         * Return the weekday index of the argument
         */
        DAYOFWEEK("DAYOFWEEK"),
        /**
         * Return the day of the year (1-366)
         */
        DAYOFYEAR("DAYOFYEAR"),
        /**
         * Extract part of a date
         */
        EXTRACT("EXTRACT"),
        /**
         * Convert a day number to a date
         */
        FROM_DAYS("FROM_DAYS"),
        /**
         * Format Unix timestamp as a date
         */
        FROM_UNIXTIME("FROM_UNIXTIME"),
        /**
         * Return a date format string
         */
        GET_FORMAT("GET_FORMAT"),
        /**
         * Extract the hour
         */
        HOUR("HOUR"),
        /**
         * , LOCALTIME	Synonym for NOW
         */
        LOCALTIME("LOCALTIME"),
        /**
         * Synonym for NOW
         */
        LOCALTIMESTAMP("LOCALTIMESTAMP"),
        /**
         * Create a date from the year and day of year
         */
        MAKEDATE("MAKEDATE"),
        /**
         * Create time from hour, minute, second
         */
        MAKETIME("MAKETIME"),
        /**
         * Return the microseconds from argument
         */
        MICROSECOND("MICROSECOND"),
        /**
         * Return the minute from the argument
         */
        MINUTE("MINUTE"),
        /**
         * Return the month from the date passed
         */
        MONTH("MONTH"),
        /**
         * Return the name of the month
         */
        MONTHNAME("MONTHNAME"),
        /**
         * Return the current date and time
         */
        NOW("NOW"),
        /**
         * Add a period to a year-month
         */
        PERIOD_ADD("PERIOD_ADD"),
        /**
         * Return the number of months between periods
         */
        PERIOD_DIFF("PERIOD_DIFF"),
        /**
         * Return the quarter from a date argument
         */
        QUARTER("QUARTER"),
        /**
         * Converts seconds to 'hh:mm:ss' format
         */
        SEC_TO_TIME("SEC_TO_TIME"),
        /**
         * Return the second (0-59)
         */
        SECOND("SECOND"),
        /**
         * Convert a string to a date
         */
        STR_TO_DATE("STR_TO_DATE"),
        /**
         * Synonym for DATE_SUB
         */
        SUBDATE("SUBDATE"),
        /**
         * Subtract times
         */
        SUBTIME("SUBTIME"),
        /**
         * Return the time at which the function executes
         */
        SYSDATE("SYSDATE"),
        /**
         * Extract the time portion of the expression passed
         */
        TIME("TIME"),
        /**
         * Format as time
         */
        TIME_FORMAT("TIME_FORMAT"),
        /**
         * Return the argument converted to seconds
         */
        TIME_TO_SEC("TIME_TO_SEC"),
        /**
         * Subtract time
         */
        TIMEDIFF("TIMEDIFF"),
        /**
         * With a single argument, this function returns the date or datetime expression; with two arguments, the sum of the arguments
         */
        TIMESTAMP("TIMESTAMP"),
        /**
         * Add an interval to a datetime expression
         */
        TIMESTAMPADD("TIMESTAMPADD"),
        /**
         * Subtract an interval from a datetime expression
         */
        TIMESTAMPDIFF("TIMESTAMPDIFF"),
        /**
         * Return the date argument converted to days
         */
        TO_DAYS("TO_DAYS"),
        /**
         * Return the date or datetime argument converted to seconds since Year 0
         */
        TO_SECONDS("TO_SECONDS"),
        /**
         * Return a Unix timestamp
         */
        UNIX_TIMESTAMP("UNIX_TIMESTAMP"),
        /**
         * Return the current UTC date
         */
        UTC_DATE("UTC_DATE"),
        /**
         * Return the current UTC time
         */
        UTC_TIME("UTC_TIME"),
        /**
         * Return the current UTC date and time
         */
        UTC_TIMESTAMP("UTC_TIMESTAMP"),
        /**
         * Return the week number
         */
        WEEK("WEEK"),
        /**
         * Return the weekday index
         */
        WEEKDAY("WEEKDAY"),
        /**
         * Return the calendar week of the date (1-53)
         */
        WEEKOFYEAR("WEEKOFYEAR"),
        /**
         * Return the year
         */
        YEAR("YEAR"),
        /**
         * Return the year and week
         */
        YEARWEEK("YEARWEEK"),
        ;

        private String function;

        DateAndTimeFunction(String function) {
            this.function = function;
        }


        @Override
        public String getFunction() {
            return function;
        }
    }

    /**
     *
     */
    public enum StringFuntion implements SqlFunction {
        /**
         * Return numeric value of left-most character
         */
        ASCII("ASCII"),
        /**
         * Return a string containing binary representation of a number
         */
        BIN("BIN"),
        /**
         * Return length of argument in bits
         */
        BIT_LENGTH("BIT_LENGTH"),
        /**
         * Return the character for each integer passed
         */
        CHAR("CHAR"),
        /**
         * Return number of characters in argument
         */
        CHAR_LENGTH("CHAR_LENGTH"),
        /**
         * Synonym for CHAR_LENGTH
         */
        CHARACTER_LENGTH("CHARACTER_LENGTH"),
        /**
         * Return concatenated string
         */
        CONCAT("CONCAT"),
        /**
         * Return concatenate with separator
         */
        CONCAT_WS("CONCAT_WS"),
        /**
         * Return string at index number
         */
        ELT("ELT"),
        /**
         * Return a string such that for every bit set in the value bits, you get an on string and for every unset bit, you get an off string
         */
        EXPORT_SET("EXPORT_SET"),
        /**
         * Index (position) of first argument in subsequent arguments
         */
        FIELD("FIELD"),
        /**
         * Index (position) of first argument within second argument
         */
        FIND_IN_SET("FIND_IN_SET"),
        /**
         * Return a number formatted to specified number of decimal places
         */
        FORMAT("FORMAT"),
        /**
         * Decode base64 encoded string and return result
         */
        FROM_BASE64("FROM_BASE64"),
        /**
         * Hexadecimal representation of decimal or string value
         */
        HEX("HEX"),
        /**
         * Insert substring at specified position up to specified number of characters
         */
        INSERT("INSERT"),
        /**
         * Return the index of the first occurrence of substring
         */
        INSTR("INSTR"),
        /**
         * Synonym for LOWER
         */
        LCASE("LCASE"),
        /**
         * Return the leftmost number of characters as specified
         */
        LEFT("LEFT"),
        /**
         * Return the length of a string in bytes
         */
        LENGTH("LENGTH"),
        /**
         * Load the named file
         */
        LOAD_FILE("LOAD_FILE"),
        /**
         * Return the position of the first occurrence of substring
         */
        LOCATE("LOCATE"),
        /**
         * Return the argument in lowercase
         */
        LOWER("LOWER"),
        /**
         * Return the string argument, left-padded with the specified string
         */
        LPAD("LPAD"),
        /**
         * Remove leading spaces
         */
        LTRIM("LTRIM"),
        /**
         * Return a set of comma-separated strings that have the corresponding bit in bits set
         */
        MAKE_SET("MAKE_SET"),
        /**
         * Return a substring starting from the specified position
         */
        MID("MID"),
        /**
         * Return a string containing octal representation of a number
         */
        OCT("OCT"),
        /**
         * Synonym for LENGTH
         */
        OCTET_LENGTH("OCTET_LENGTH"),
        /**
         * Return character code for leftmost character of the argument
         */
        ORD("ORD"),
        /**
         * Synonym for LOCATE
         */
        POSITION("POSITION"),
        /**
         * Escape the argument for use in an SQL statement
         */
        QUOTE("QUOTE"),
        /**
         * Repeat a string the specified number of times
         */
        REPEAT("REPEAT"),
        /**
         * Replace occurrences of a specified string
         */
        REPLACE("REPLACE"),
        /**
         * Reverse the characters in a string
         */
        REVERSE("REVERSE"),
        /**
         * Return the specified rightmost number of characters
         */
        RIGHT("RIGHT"),
        /**
         * Append string the specified number of times
         */
        RPAD("RPAD"),
        /**
         * Remove trailing spaces
         */
        RTRIM("RTRIM"),
        /**
         * Return a soundex string
         */
        SOUNDEX("SOUNDEX"),
        /**
         * Return a string of the specified number of spaces
         */
        SPACE("SPACE"),
        /**
         * Compare two strings
         */
        STRCMP("STRCMP"),
        /**
         * Return the substring as specified
         */
        SUBSTR("SUBSTR"),
        /**
         * Return the substring as specified
         */
        SUBSTRING("SUBSTRING"),
        /**
         * Return a substring from a string before the specified number of occurrences of the delimiter
         */
        SUBSTRING_INDEX("SUBSTRING_INDEX"),
        /**
         * Return the argument converted to a base-64 string
         */
        TO_BASE64("TO_BASE64"),
        /**
         * Remove leading and trailing spaces
         */
        TRIM("TRIM"),
        /**
         * Synonym for UPPER
         */
        UCASE("UCASE"),
        /**
         * Return a string containing hex representation of a number
         */
        UNHEX("UNHEX"),
        /**
         * Convert to uppercase
         */
        UPPER("UPPER"),
        /**
         * Return the weight string for a string
         */
        WEIGHT_STRING("WEIGHT_STRING"),


        ;
        private String function;

        StringFuntion(String function) {
            this.function = function;
        }

        @Override
        public String getFunction() {
            return function;
        }
    }

    /**
     * https://dev.mysql.com/doc/refman/5.7/en/mathematical-functions.html
     */
    public enum NumericFunction implements SqlFunction {
        /**
         * Return the absolute value
         */
        ABS("abs"),
        /**
         * Return the arc cosine
         */
        ACOS("acos"),
        /**
         * Return the arc sine
         */
        ASIN("asin"),
        /**
         * Return the arc tangent
         */
        ATAN("atan"),
        /**
         * , ATAN
         */
        ATAN2("atan2"),
        /**
         * Return the smallest integer value not less than the argument
         */
        CEIL("ceil"),
        /**
         * Return the smallest integer value not less than the argument
         */
        CEILING("ceiling"),
        /**
         * Convert numbers between different number bases
         */
        CONV("conv"),
        /**
         * Return the cosine
         */
        COS("cos"),
        /**
         * Return the cotangent
         */
        COT("cot"),
        /**
         * Compute a cyclic redundancy check value
         */
        CRC32("crc32"),
        /**
         * Convert radians to degrees
         */
        DEGREES("degrees"),
        /**
         * Raise to the power of
         */
        EXP("exp"),
        /**
         * Return the largest integer value not greater than the argument
         */
        FLOOR("floor"),
        /**
         * Return the natural logarithm of the argument
         */
        LN("ln"),
        /**
         * Return the natural logarithm of the first argument
         */
        LOG("log"),
        /**
         * Return the base-10 logarithm of the argument
         */
        LOG10("log10"),
        /**
         * Return the base-2 logarithm of the argument
         */
        LOG2("log2"),
        /**
         * Return the remainder
         */
        MOD("mod"),
        /**
         * Return the value of pi
         */
        PI("pi"),
        /**
         * Return the argument raised to the specified power
         */
        POW("pow"),
        /**
         * Return the argument raised to the specified power
         */
        POWER("power"),
        /**
         * Return argument converted to radians
         */
        RADIANS("radians"),
        /**
         * Return a random floating-point value
         */
        RAND("rand"),
        /**
         * Round the argument
         */
        ROUND("round"),
        /**
         * Return the sign of the argument
         */
        SIGN("sign"),
        /**
         * Return the sine of the argument
         */
        SIN("sin"),
        /**
         * Return the square root of the argument
         */
        SQRT("sqrt"),
        /**
         * Return the tangent of the argument
         */
        TAN("tan"),
        /**
         * Truncate to specified number of decimal places
         */
        TRUNCATE("truncate");
        private String function;

        NumericFunction(String function) {
            this.function = function;
        }

        @Override
        public String getFunction() {
            return function;
        }
    }

    /**
     *
     */
    public enum MiscellaneousFuntion implements SqlFunction {
        /**
         * Suppress ONLY_FULL_GROUP_BY value rejection
         */
        ANY_VALUE("ANY_VALUE"),
        /**
         * Return the default value for a table column
         */
        DEFAULT("DEFAULT"),
        /**
         * Return the numeric value of an IP address
         */
        INET_ATON("INET_ATON"),
        /**
         * Return the IP address from a numeric value
         */
        INET_NTOA("INET_NTOA"),
        /**
         * Return the numeric value of an IPv6 address
         */
        INET6_ATON("INET6_ATON"),
        /**
         * Return the IPv6 address from a numeric value
         */
        INET6_NTOA("INET6_NTOA"),
        /**
         * Whether argument is an IPv4 address
         */
        IS_IPV4("IS_IPV4"),
        /**
         * Whether argument is an IPv4-compatible address
         */
        IS_IPV4_COMPAT("IS_IPV4_COMPAT"),
        /**
         * Whether argument is an IPv4-mapped address
         */
        IS_IPV4_MAPPED("IS_IPV4_MAPPED"),
        /**
         * Whether argument is an IPv6 address
         */
        IS_IPV6("IS_IPV6"),
        /**
         * Block until the slave has read and applied all updates up to the specified position
         */
        MASTER_POS_WAIT("MASTER_POS_WAIT"),
        /**
         * Cause the column to have the given name
         */
        NAME_CONST("NAME_CONST"),
        /**
         * Sleep for a number of seconds
         */
        SLEEP("SLEEP"),
        /**
         * Return a Universal Unique Identifier (UUID)
         */
        UUID("UUID"),
        /**
         * Return an integer-valued universal identifier
         */
        UUID_SHORT("UUID_SHORT"),
        /**
         * Define the values to be used during an INSERT
         */
        VALUES("VALUES");
        private String function;

        MiscellaneousFuntion(String function) {
            this.function = function;
        }

        @Override
        public String getFunction() {
            return function;
        }
    }

    /**
     *
     */
    public enum LockFuntion implements SqlFunction {
        /**
         * Get a named lock
         */
        GET_LOCK("GET_LOCK"),
        /**
         * Whether the named lock is free
         */
        IS_FREE_LOCK("IS_FREE_LOCK"),
        /**
         * Whether the named lock is in use; return connection identifier if true
         */
        IS_USED_LOCK("IS_USED_LOCK"),
        /**
         * Release all current named locks
         */
        RELEASE_ALL_LOCKS("RELEASE_ALL_LOCKS"),
        /**
         * Release the named lock
         */
        RELEASE_LOCK("RELEASE_LOCK"),
        /**
         * Cast a value as a certain type
         */
        CAST("CAST"),
        /**
         * Cast a value as a certain type
         */
        CONVERT("CONVERT");
        private String function;

        LockFuntion(String function) {
            this.function = function;
        }

        @Override
        public String getFunction() {
            return function;
        }
    }

    /**
     *
     */
    public enum JsonFuntion implements SqlFunction {

        /**
         * (deprecated 5.7.9)	Append data to JSON document
         */
        JSON_APPEND("JSON_APPEND"),
        /**
         * Create JSON array
         */
        JSON_ARRAY("JSON_ARRAY"),
        /**
         * Append data to JSON document
         */
        JSON_ARRAY_APPEND("JSON_ARRAY_APPEND"),
        /**
         * Insert into JSON array
         */
        JSON_ARRAY_INSERT("JSON_ARRAY_INSERT"),
        /**
         * Whether JSON document contains specific object at path
         */
        JSON_CONTAINS("JSON_CONTAINS"),
        /**
         * Whether JSON document contains any data at path
         */
        JSON_CONTAINS_PATH("JSON_CONTAINS_PATH"),
        /**
         * Maximum depth of JSON document
         */
        JSON_DEPTH("JSON_DEPTH"),
        /**
         * Return data from JSON document
         */
        JSON_EXTRACT("JSON_EXTRACT"),
        /**
         * Insert data into JSON document
         */
        JSON_INSERT("JSON_INSERT"),
        /**
         * Array of keys from JSON document
         */
        JSON_KEYS("JSON_KEYS"),
        /**
         * Number of elements in JSON document
         */
        JSON_LENGTH("JSON_LENGTH"),
        /**
         * (deprecated 5.7.22)	Merge JSON documents, preserving duplicate keys. Deprecated synonym for JSON_MERGE_PRESERVE
         */
        JSON_MERGE("JSON_MERGE"),
        /**
         * Merge JSON documents, replacing values of duplicate keys
         */
        JSON_MERGE_PATCH("JSON_MERGE_PATCH"),
        /**
         * Merge JSON documents, preserving duplicate keys
         */
        JSON_MERGE_PRESERVE("JSON_MERGE_PRESERVE"),
        /**
         * Create JSON object
         */
        JSON_OBJECT("JSON_OBJECT"),
        /**
         * Print a JSON document in human-readable format
         */
        JSON_PRETTY("JSON_PRETTY"),
        /**
         * Quote JSON document
         */
        JSON_QUOTE("JSON_QUOTE"),
        /**
         * Remove data from JSON document
         */
        JSON_REMOVE("JSON_REMOVE"),
        /**
         * Replace values in JSON document
         */
        JSON_REPLACE("JSON_REPLACE"),
        /**
         * Path to value within JSON document
         */
        JSON_SEARCH("JSON_SEARCH"),
        /**
         * Insert data into JSON document
         */
        JSON_SET("JSON_SET"),
        /**
         * Space used for storage of binary representation of a JSON document
         */
        JSON_STORAGE_SIZE("JSON_STORAGE_SIZE"),
        /**
         * Type of JSON value
         */
        JSON_TYPE("JSON_TYPE"),
        /**
         * Unquote JSON value
         */
        JSON_UNQUOTE("JSON_UNQUOTE"),
        /**
         * Whether JSON value is valid
         */
        JSON_VALID("JSON_VALID");
        private String function;

        JsonFuntion(String function) {
            this.function = function;
        }

        @Override
        public String getFunction() {
            return function;
        }
    }

    /**
     *
     */
    public enum InformationFuntion implements SqlFunction {
        /**
         * Repeatedly execute an expression
         */
        BENCHMARK("BENCHMARK"),
        /**
         * Return the character set of the argument
         */
        CHARSET("CHARSET"),
        /**
         * Return the collation coercibility value of the string argument
         */
        COERCIBILITY("COERCIBILITY"),
        /**
         * Return the collation of the string argument
         */
        COLLATION("COLLATION"),
        /**
         * Return the connection ID (thread ID) for the connection
         */
        CONNECTION_ID("CONNECTION_ID"),
        /**
         * , CURRENT_USER	The authenticated user name and host name
         */
        CURRENT_USER("CURRENT_USER"),
        /**
         * Return the default (current) database name
         */
        DATABASE("DATABASE"),
        /**
         * For a SELECT with a LIMIT clause, the number of rows that would be returned were there no LIMIT clause
         */
        FOUND_ROWS("FOUND_ROWS"),
        /**
         * Value of the AUTOINCREMENT column for the last INSERT
         */
        LAST_INSERT_ID("LAST_INSERT_ID"),
        /**
         * The number of rows updated
         */
        ROW_COUNT("ROW_COUNT"),
        /**
         * Synonym for DATABASE
         */
        SCHEMA("SCHEMA"),
        /**
         * Synonym for USER
         */
        SESSION_USER("SESSION_USER"),
        /**
         * Synonym for USER
         */
        SYSTEM_USER("SYSTEM_USER"),
        /**
         * The user name and host name provided by the client
         */
        USER("USER"),
        /**
         * Return a string that indicates the MySQL server version
         */
        VERSION("VERSION");

        private String function;

        InformationFuntion(String function) {
            this.function = function;
        }

        @Override
        public String getFunction() {
            return function;
        }
    }

    /**
     *
     */
    public enum EncryptionAndCompressionFuntion implements SqlFunction {
        /**
         * Decrypt using AES
         */
        AES_DECRYPT("AES_DECRYPT"),

        /**
         * Encrypt using AES
         */
        AES_ENCRYPT("AES_ENCRYPT"),

        /**
         * Decrypt ciphertext using private or public key
         */
        ASYMMETRIC_DECRYPT("ASYMMETRIC_DECRYPT"),

        /**
         * Derive symmetric key from asymmetric keys
         */
        ASYMMETRIC_DERIVE("ASYMMETRIC_DERIVE"),

        /**
         * Encrypt cleartext using private or public key
         */
        ASYMMETRIC_ENCRYPT("ASYMMETRIC_ENCRYPT"),

        /**
         * Generate signature from digest
         */
        ASYMMETRIC_SIGN("ASYMMETRIC_SIGN"),

        /**
         * Verify that signature matches digest
         */
        ASYMMETRIC_VERIFY("ASYMMETRIC_VERIFY"),

        /**
         * Return result as a binary string
         */
        COMPRESS("COMPRESS"),

        /**
         * Create private key
         */
        CREATE_ASYMMETRIC_PRIV_KEY("CREATE_ASYMMETRIC_PRIV_KEY"),

        /**
         * Create public key
         */
        CREATE_ASYMMETRIC_PUB_KEY("CREATE_ASYMMETRIC_PUB_KEY"),

        /**
         * Generate shared DH secret
         */
        CREATE_DH_PARAMETERS("CREATE_DH_PARAMETERS"),

        /**
         * Generate digest from string
         */
        CREATE_DIGEST("CREATE_DIGEST"),

        /**
         * (deprecated 5.7.2)	Decode a string encrypted using ENCODE
         */
        DECODE("DECODE"),

        /**
         * (deprecated 5.7.6)	Decrypt a string
         */
        DES_DECRYPT("DES_DECRYPT"),

        /**
         * (deprecated 5.7.6)	Encrypt a string
         */
        DES_ENCRYPT("DES_ENCRYPT"),

        /**
         * (deprecated 5.7.2)	Encode a string
         */
        ENCODE("ENCODE"),

        /**
         * (deprecated 5.7.6)	Encrypt a string
         */
        ENCRYPT("ENCRYPT"),

        /**
         * Calculate MD5 checksum
         */
        MD5("MD5"),

        /**
         * Return the value of the pre-4.1 implementation of PASSWORD
         */
        OLD_PASSWORD("OLD_PASSWORD"),

        /**
         * (deprecated 5.7.6)	Calculate and return a password string
         */
        PASSWORD("PASSWORD"),

        /**
         * Return a random byte vector
         */
        RANDOM_BYTES("RANDOM_BYTES"),

        /**
         * , SHA
         */
        SHA1("SHA1"),

        /**
         * Calculate an SHA-2 checksum
         */
        SHA2("SHA2"),

        /**
         * Uncompress a string compressed
         */
        UNCOMPRESS("UNCOMPRESS"),

        /**
         * Return the length of a string before compression
         */
        UNCOMPRESSED_LENGTH("UNCOMPRESSED_LENGTH"),

        /**
         * Determine strength of password
         */
        VALIDATE_PASSWORD_STRENGTH("VALIDATE_PASSWORD_STRENGTH");


        private String function;

        EncryptionAndCompressionFuntion(String function) {
            this.function = function;
        }

        @Override
        public String getFunction() {
            return function;
        }
    }
}
