package com.seriousplay.productivity.mybatis.config;

import com.seriousplay.productivity.mybatis.MapperRefresher;
import com.seriousplay.productivity.mybatis.MybatisMapper;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.scripting.freemarker.FreeMarkerLanguageDriver;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(annotationClass = Mapper.class, markerInterface = MybatisMapper.class, basePackages = "com.zayy.sport.**.mapper")
public class MybatisConfiguration {
    @Autowired
    MybatisProperties mybatisProperties;

    @Bean
    MapperRefresher refreshMapperCache(MybatisProperties mybatisProperties, SqlSessionTemplate sqlSessionTemplate) {
        MapperRefresher refreshMapperCache = new MapperRefresher(mybatisProperties.getMapperLocations(), sqlSessionTemplate.getConfiguration());
        return refreshMapperCache;
    }

    @ConditionalOnClass(FreeMarkerLanguageDriver.class)
    @Bean
    ConfigurationCustomizer mybatisConfigurationCustomizer() {
        return (configuration) -> configuration.getTypeAliasRegistry().registerAlias("freemarker", FreeMarkerLanguageDriver.class);
    }
}
