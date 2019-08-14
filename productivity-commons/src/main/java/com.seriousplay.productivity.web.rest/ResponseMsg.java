package com.seriousplay.productivity.web.rest;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;

/**
 * 接口请求返回数据
 *
 * @param
 */
public class ResponseMsg {
    private static Logger logger = LogManager.getLogger(ResponseMsg.class);
    /**
     * 请求状态
     */
    private boolean success;
    /**
     * 编码
     */
    private String code;
    /**
     * 接口返回数据
     */
    private Object data;
    /**
     * 消息
     */
    private String msg;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ResponseMsg get() {
        return new ResponseMsg();
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 操作失败
     *
     * @param code
     * @param msg
     */
    public void fail(String code, String msg) {
        this.success = false;
        this.code = code;
        this.msg = msg;
    }

    /**
     * 操作失败
     *
     * @param msg
     */
    public void fail(String msg) {
        this.fail("20000", msg);
    }

    /**
     *
     */
    public void fail() {
        fail("20000", "操作失败！");
    }

    /**
     * 系统异常
     *
     * @param exception
     */
    public void error(Throwable exception) {
        logger.catching(Level.ERROR,exception);
        this.success = false;
        this.code = "500";
        this.msg = exception.getMessage();
    }

    /**
     * 请求成功
     *
     * @param data
     */
    public void success(Object data) {
        this.data = data;
        this.success = true;
        this.code = "200";

    }

    /**
     * 操作成功
     */
    public void success() {
        this.success(Collections.emptyMap());
    }
}
