package com.seriousplay.productivity.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author changqingshun
 */
@Component
public class JsonUtils {
    static ObjectMapper objectMapper = new ObjectMapper();

    /**
     *
     * @return
     */
    public static ObjectMapper objectMapper() {
        return objectMapper;
    }

    @Autowired
    public void init(ObjectMapper objectMapper) {
        JsonUtils.objectMapper = objectMapper;
    }
}
