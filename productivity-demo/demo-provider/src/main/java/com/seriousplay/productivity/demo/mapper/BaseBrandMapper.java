package com.seriousplay.productivity.demo.mapper;

import com.seriousplay.productivity.demo.model.Brand;
import com.seriousplay.productivity.demo.model.BrandExample;
import com.seriousplay.productivity.mybatis.MybatisMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

@Mapper
public interface BaseBrandMapper extends MybatisMapper {
    int deleteByPrimaryKey(String id_);

    int insert(Brand record);

    int insertSelective(Brand record);

    List<Brand> selectByExampleWithRowbounds(BrandExample example, RowBounds rowBounds);

    List<Brand> selectByExample(BrandExample example);

    Brand selectByPrimaryKey(String id_);

    int updateByExampleSelective(@Param("record") Brand record, @Param("example") BrandExample example);

    int updateByExample(@Param("record") Brand record, @Param("example") BrandExample example);

    int updateByPrimaryKeySelective(Brand record);

    int updateByPrimaryKey(Brand record);
}