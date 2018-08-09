package com.seriousplay.productivity.mybatis;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.session.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEvent;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author changqingshun
 */
public class MapperRefresher implements InitializingBean {

    /**
     *
     */
    static final Logger logger = LogManager.getLogger(MapperRefresher.class);
    /**
     *
     */
    static PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
    private Set<String> loadedResources;
    private Map<String, MapperRescource> mapperResources;
    /**
     * 配置
     */
    private Configuration configuration;
    /**
     * mapper 位置
     */
    private String[] mapperLocations;

    /**
     * @param mapperLocations
     * @param configuration
     */
    public MapperRefresher(String[] mapperLocations, Configuration configuration) {
        this();
        this.mapperLocations = mapperLocations;
        this.configuration = configuration;
    }

    public MapperRefresher() {
        super();
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public String[] getMapperLocations() {
        return mapperLocations;
    }

    public void setMapperLocations(String[] mapperLocations) {
        this.mapperLocations = mapperLocations;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.reloadMapperResources();
        Field loadedResourcesField = configuration.getClass().getDeclaredField("loadedResources");
        loadedResourcesField.setAccessible(true);
        this.loadedResources = (Set<String>) loadedResourcesField.get(configuration);
        this.removeConfig(configuration);
    }

    /**
     * 刷新mapper
     */
    public void refreshMapper() {
        try {
            this.reloadMapperResources();
            for (MapperRescource resource : this.mapperResources.values()) {
                String resourceName = resource.getName();
                Resource resource1 = pathMatchingResourcePatternResolver.getResource(resourceName);
                long vserion = resource1.contentLength() + resource1.lastModified();
                if (resource.isNewMapper() || vserion > resource.getVersion()) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("reload mapper " + resource1);
                    }
                    String loadedRes = resource1.toString();
                    this.loadedResources.remove(loadedRes);
                    XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(resource1.getInputStream(), configuration, loadedRes, configuration.getSqlFragments());
                    xmlMapperBuilder.parse();
                    resource.setNewMapper(false);
                }
                resource.setVersion(vserion);
            }
        } catch (Exception e) {
            logger.catching(e);
        }
    }

    /**
     *
     */
    void reloadMapperResources() throws IOException {
        if (this.mapperResources == null) {
            this.mapperResources = new HashMap<>();
        }
        if (this.getMapperLocations() != null) {
            for (String one : this.getMapperLocations()) {
                Resource[] resources = pathMatchingResourcePatternResolver.getResources(one);
                for (Resource resource : resources) {
                    String name = resource.getURL().toString();
                    if (!mapperResources.containsKey(name)) {
                        MapperRescource mapperRescource = new MapperRescource();
                        long lastVersion = resource.contentLength() + resource.lastModified();
                        mapperRescource.setName(name);
                        mapperRescource.setVersion(lastVersion);
                        mapperRescource.setNewMapper(true);
                        mapperResources.put(name, mapperRescource);
                    }
                }
            }
        }

    }

    /**
     * 清空Configuration中几个重要的缓存
     *
     * @param configuration
     * @throws Exception
     */
    private void removeConfig(Configuration configuration) throws Exception {
        Class<?> classConfig = configuration.getClass();
        clearMap(classConfig, configuration, "mappedStatements");
        clearMap(classConfig, configuration, "caches");
        clearMap(classConfig, configuration, "resultMaps");
        clearMap(classConfig, configuration, "parameterMaps");
        clearMap(classConfig, configuration, "keyGenerators");
        clearMap(classConfig, configuration, "sqlFragments");
        // clearSet(classConfig, configuration, "loadedResources");
    }

    @SuppressWarnings("rawtypes")
    private void clearMap(Class<?> classConfig, Configuration configuration, String fieldName) throws Exception {
        Field field = classConfig.getDeclaredField(fieldName);
        field.setAccessible(true);
        Map mapConfig = (Map) field.get(configuration);
        if (!(mapConfig instanceof StrictMap)) {
            Map newMap = new StrictMap(StringUtils.capitalize(fieldName) + "collection");
            for (Object key : mapConfig.keySet()) {
                try {
                    newMap.put(key, mapConfig.get(key));
                } catch (IllegalArgumentException ex) {
                    newMap.put(key, ex.getMessage());
                }
            }
            field.set(configuration, newMap);
        }
        //mapConfig.clear();
    }

    public void onApplicationEvent(ApplicationEvent event) {
        logger.debug(event);
    }


    public static class MapperRescource {
        private String name;
        private long version;
        private boolean newMapper;

        public boolean isNewMapper() {
            return newMapper;
        }

        public void setNewMapper(boolean newMapper) {
            this.newMapper = newMapper;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getVersion() {
            return version;
        }

        public void setVersion(long version) {
            this.version = version;
        }
    }

    /**
     * 重写 org.apache.ibatis.session.Configuration.StrictMap 类
     * 来自 MyBatis3.4.0版本，修改 put 方法，允许反复 put更新。
     */
    public static class StrictMap<V> extends HashMap<String, V> {

        private static final long serialVersionUID = -4950446264854982944L;
        private String name;

        public StrictMap(String name, int initialCapacity, float loadFactor) {
            super(initialCapacity, loadFactor);
            this.name = name;
        }

        public StrictMap(String name, int initialCapacity) {
            super(initialCapacity);
            this.name = name;
        }

        public StrictMap(String name) {
            super();
            this.name = name;
        }

        public StrictMap(String name, Map<String, ? extends V> m) {
            super(m);
            this.name = name;
        }

        @SuppressWarnings("unchecked")
        public V put(String key, V value) {
            // ThinkGem 如果现在状态为刷新，则刷新(先删除后添加)
            remove(key);
            // ThinkGem end
            if (containsKey(key)) {
                throw new IllegalArgumentException(name + " already contains value for " + key);
            }
            if (key.contains(".")) {
                final String shortKey = getShortName(key);
                if (super.get(shortKey) == null) {
                    super.put(shortKey, value);
                } else {
                    super.put(shortKey, (V) new Ambiguity(shortKey));
                }
            }
            return super.put(key, value);
        }

        public V get(Object key) {
            V value = super.get(key);
            if (value == null) {
                throw new IllegalArgumentException(name + " does not contain value for " + key);
            }
            if (value instanceof Ambiguity) {
                throw new IllegalArgumentException(((Ambiguity) value).getSubject() + " is ambiguous in " + name
                        + " (try using the full name including the namespace, or rename one of the entries)");
            }
            return value;
        }

        private String getShortName(String key) {
            final String[] keyparts = key.split("\\.");
            return keyparts[keyparts.length - 1];
        }

        protected static class Ambiguity {
            private String subject;

            public Ambiguity(String subject) {
                this.subject = subject;
            }

            public String getSubject() {
                return subject;
            }
        }
    }
}
