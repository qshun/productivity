package com.seriousplay.productivity.web.rest;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CommonRestControllerAdvice {
    @ExceptionHandler(Exception.class)
    public ResponseMsg handlerException(Exception e, HttpServletResponse response) throws IOException {
        ResponseMsg msg = new ResponseMsg();
        msg.error(e);
        return msg;
    }

    /**
     * 验证失败
     *
     * @param e 异常
     * @param errors 错误
     * @return
     */
    @ExceptionHandler(BindException.class)
    public ResponseMsg onBindException(BindException e, Errors errors) {
        ResponseMsg msg = new ResponseMsg();
        Map<String, Object> data = new HashMap<>(errors.getErrorCount());
        errors.getFieldErrors().forEach(error -> {
            data.put(error.getField(), error.getDefaultMessage());
        });
        msg.setData(data);
        msg.fail("400", "参数错误！");
        return msg;
    }
}
