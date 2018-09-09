package com.seriousplay.productivity.demo.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.seriousplay.productivity.demo.mapper.BaseBrandMapper;
import com.seriousplay.productivity.demo.model.Brand;
import com.seriousplay.productivity.demo.service.BrandService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
@Service
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
