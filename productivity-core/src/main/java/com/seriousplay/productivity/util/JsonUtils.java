package com.seriousplay.productivity.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author changqingshun
 */
@Component
public class JsonUtils {
    public static ObjectMapper objectMapper;

    @Autowired
    public void init(ObjectMapper objectMapper) {
        JsonUtils.objectMapper = objectMapper;
    }
}
