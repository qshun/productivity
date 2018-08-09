package com.seriousplay.productivity.web.helper;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求工具类
 *
 * @author changqingshun
 */
public class Helper4Request {
    /**
     * 获取参数map
     *
     * @param request 请求
     * @return
     */
    public static Map<String, Object> getParamMap(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> data = new HashMap<>(parameterMap.size());
        parameterMap.forEach((key, val) -> data.put(key, val.length > 1 ? Arrays.asList(val) : val[0]));
        return data;
    }
}
