package com.seriousplay.productivity.demo.service.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.seriousplay.productivity.demo.model.Brand;
import com.seriousplay.productivity.demo.service.BrandService;
@SofaService(interfaceType = BrandService.class,bindings = {@SofaServiceBinding,@SofaServiceBinding(bindingType = "bolt"),@SofaServiceBinding(bindingType = "dubbo")})
public class BrandServiceImpl implements BrandService {
    @Override
    public void save(Brand brand) {

    }
}
