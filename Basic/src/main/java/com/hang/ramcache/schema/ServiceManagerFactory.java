package com.hang.ramcache.schema;

import com.hang.ramcache.IEntity;
import com.hang.ramcache.ServiceManager;
import com.hang.ramcache.orm.Accessor;
import com.hang.ramcache.orm.Querier;
import com.hang.ramcache.persist.PersisterConfig;
import org.springframework.beans.factory.FactoryBean;

import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.Set;

/**
 * @author ZhangHang
 * @create 2018-02-07 21:24
 **/
public class ServiceManagerFactory implements FactoryBean<ServiceManager> {
    public static final String ENTITY_CLASSES_NAME = "entityClasses";
    public static final String PERSISTER_CONFIG_NAME = "persisterConfig";

    private Accessor accessor;
    private Querier querier;
    private Set<Class<IEntity>> entityClasses;
    private Map<String, PersisterConfig> persisterConfig;
    private Map<String, Integer> constants;

    private ServiceManager cacheServiceManager;


    @Override
    public ServiceManager getObject() throws Exception {
        cacheServiceManager = new ServiceManager(entityClasses, accessor, querier, constants, persisterConfig);
        return cacheServiceManager;
    }

    @PreDestroy
    public void shutdown() {
        if (cacheServiceManager == null) {
            return;
        }
        cacheServiceManager.shutdown();
    }

    @Override
    public Class<?> getObjectType() {
        return ServiceManager.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public void setAccessor(Accessor accessor) {
        this.accessor = accessor;
    }

    public void setQuerier(Querier querier) {
        this.querier = querier;
    }

    public void setEntityClasses(Set<Class<IEntity>> entityClasses) {
        this.entityClasses = entityClasses;
    }

    public void setPersisterConfig(Map<String, PersisterConfig> persisterConfig) {
        this.persisterConfig = persisterConfig;
    }

    public void setConstants(Map<String, Integer> constants) {
        this.constants = constants;
    }
}
