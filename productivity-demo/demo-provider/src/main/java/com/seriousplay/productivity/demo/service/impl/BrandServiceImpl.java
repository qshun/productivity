package com.seriousplay.productivity.demo.service.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.seriousplay.productivity.demo.mapper.BaseBrandMapper;
import com.seriousplay.productivity.demo.model.Brand;
import com.seriousplay.productivity.demo.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@SofaService(interfaceType = BrandService.class,bindings = {@SofaServiceBinding,@SofaServiceBinding(bindingType = "bolt"),@SofaServiceBinding(bindingType = "dubbo")})
public class BrandServiceImpl implements BrandService {
    static {
        System.out.println("----------------------------------------------------------------");
    }
    @Autowired
    BaseBrandMapper brandMapper;
    @Override
    public void save(Brand brand) {
    this.brandMapper.insert(brand);
    }
}
