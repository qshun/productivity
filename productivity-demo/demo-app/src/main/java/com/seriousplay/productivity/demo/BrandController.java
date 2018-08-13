package com.seriousplay.productivity.demo;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.seriousplay.productivity.demo.model.Brand;
import com.seriousplay.productivity.demo.service.BrandService;
import com.seriousplay.productivity.web.rest.ResponseMsg;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class BrandController {
    @SofaReference(interfaceType = BrandService.class, binding = @SofaReferenceBinding(bindingType = "bolt"))
    private BrandService brandService;

    @RequestMapping("/brand/create")
    public ResponseMsg save(Brand brand) {
        ResponseMsg msg = new ResponseMsg();
        this.brandService.save(brand);
        msg.success();
        return msg;
    }
}
