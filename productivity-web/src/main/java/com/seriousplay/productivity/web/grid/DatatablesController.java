package com.seriousplay.productivity.web.grid;


import com.seriousplay.productivity.mybatis.grid.DTPageHelper;
import com.seriousplay.productivity.mybatis.grid.DTQueryVO;
import com.seriousplay.productivity.mybatis.grid.IDTPager;
import com.seriousplay.productivity.web.rest.ResponseMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class DatatablesController {
    @Autowired
    private IDTPager dtPager;

    @PostMapping(value = "/api/dt/{namespace}/{mapper}")
    public ResponseMsg list(@PathVariable String namespace, @PathVariable String mapper, @RequestBody DTQueryVO query) {
        ResponseMsg msg = new ResponseMsg();
        Map<String, Object> param = query.getParamMap();
        DTPageHelper<?> list = this.dtPager.query(namespace, mapper, param);
        list.setDraw(query.getDraw());
        msg.success(list);
        return msg;
    }

    @PostMapping("/api/table/{namespace}/{mapper}")
    public <T> DTPageHelper<T> list(@PathVariable String namespace, @PathVariable String mapper, @RequestParam int start, @RequestParam int length, HttpServletRequest req) {
        Map<String, Object> param = new HashMap<>();
        param.put("_start_", start);
        param.put("_length_", length);
        req.getParameterMap().forEach((key, val) -> param.put(key, val[0]));
        DTPageHelper<T> list = this.dtPager.query(namespace, mapper, param);
        return list;
    }
}
