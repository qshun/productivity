package com.seriousplay.productivity.demo;


import com.alibaba.dubbo.config.annotation.Reference;
import com.seriousplay.productivity.demo.model.Brand;
import com.seriousplay.productivity.demo.service.BrandService;
import com.seriousplay.productivity.web.rest.ResponseMsg;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class BrandController {

    @Reference(interfaceClass = BrandService.class,check = false)
    private BrandService brandService;

    @RequestMapping("/brand/create")
    public ResponseMsg save(Brand brand) {
        ResponseMsg msg = new ResponseMsg();
        this.brandService.save(brand);
        msg.success();
        return msg;
    }
}
