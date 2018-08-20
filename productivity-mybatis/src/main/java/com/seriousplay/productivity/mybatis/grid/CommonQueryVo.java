package com.seriousplay.productivity.mybatis.grid;



import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

/**
 * app 数据分页
 */
public class CommonQueryVo {
    /**
     * 数据长度
     */
    private int length = 20;
    /**
     * sql mapper 命名空间
     */
    private String namespace;
    /**
     * mapper id
     */
    @NotBlank
    private String mapper;
    /**
     * 其他参数
     */
    private Map<String, Object> paramMap = new HashMap<>(20);

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getMapper() {
        return mapper;
    }

    public void setMapper(String mapper) {
        this.mapper = mapper;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }


}
