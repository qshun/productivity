package com.seriousplay.productivity.demo.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.seriousplay.productivity.demo.mapper.BaseBrandMapper;
import com.seriousplay.productivity.demo.model.Brand;
import com.seriousplay.productivity.demo.model.BrandExample;
import com.seriousplay.productivity.demo.service.BrandService;
import org.bson.types.ObjectId;
import org.jboss.resteasy.annotations.Form;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;

@Service(protocol = {"rest", "dubbo"})
@Path("/demo")
public class BrandServiceImpl implements BrandService {

    @Autowired
    BaseBrandMapper brandMapper;


    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Override
    public void save(@Form Brand brand) {
        System.out.println(brand.getName_());
        brand.setId_(ObjectId.get().toHexString());
        brand.setCreate_time_(new Date());
        brand.setStatus_("1");
        this.brandMapper.insert(brand);
    }

    /**
     *
     * @return
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public List<Brand> getBrandList() {
        return this.brandMapper.selectByExample(new BrandExample());
    }
}
