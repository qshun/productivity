package com.seriousplay.productivity.web.rest;

import com.seriousplay.productivity.mybatis.MapperRefresher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mybatis")
public class MapperRefreshController {
    @Autowired
    private MapperRefresher refreshMapperCache;

    /**
     * @return
     */
    @RequestMapping("/refresh")
    public ResponseMsg refreshMapper() {
        ResponseMsg msg = new ResponseMsg();
        msg.success();
        refreshMapperCache.refreshMapper();
        return msg;
    }
}
