package com.seriousplay.productivity.demo.service.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.seriousplay.productivity.demo.mapper.BaseBrandMapper;
import com.seriousplay.productivity.demo.model.Brand;
import com.seriousplay.productivity.demo.service.BrandService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@SofaService(interfaceType = BrandService.class,bindings = {@SofaServiceBinding,@SofaServiceBinding(bindingType = "bolt"),@SofaServiceBinding(bindingType = "dubbo")})
public class BrandServiceImpl implements BrandService {

    @Autowired
    BaseBrandMapper brandMapper;
    @Override
    public void save(Brand brand) {
        brand.setId_(ObjectId.get().toHexString());
        brand.setCreate_time_(new Date());
        brand.setStatus_("1");
        this.brandMapper.insert(brand);
    }
}
