package com.seriousplay.productivity.demo.service;

import com.seriousplay.productivity.demo.model.Brand;

import java.util.List;

public interface BrandService {
    /**
     * 保存
     * @param brand
     */
    void save(Brand brand);

    /**
     *
     * @return
     */
    List<Brand> getBrandList();
}
