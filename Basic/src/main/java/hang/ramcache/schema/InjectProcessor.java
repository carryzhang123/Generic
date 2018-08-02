package hang.ramcache.schema;

import com.hang.ramcache.IEntity;
import com.hang.ramcache.ServiceManager;
import com.hang.ramcache.anno.Inject;
import com.hang.ramcache.exception.ConfigurationException;
import com.hang.ramcache.service.EntityCacheService;
import com.hang.ramcache.service.RegionCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.core.Ordered;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author ZhangHang
 * @create 2018-01-30 14:13
 **/
public class InjectProcessor extends InstantiationAwareBeanPostProcessorAdapter implements Ordered {
    private static final Logger logger = LoggerFactory.getLogger(InjectProcessor.class);
    @Autowired
    private ServiceManager manager;

    public boolean postProcessAfterInstantiation(final Object bean, final String beanName) throws BeansException {
        ReflectionUtils.doWithFields(bean.getClass(), new FieldCallback() {
            @Override
            public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                Inject anno = field.getAnnotation(Inject.class);
                if (anno == null) {
                    return;
                }
                if (field.getType().equals(EntityCacheService.class)) {
                    injectEntityCacheService(bean, beanName, field);
                } else if (field.getType().equals(RegionCacheService.class)) {
                    injectEntityCacheService(bean, beanName, field);
                } else {
                    FormattingTuple message = MessageFormatter.format("Bean的注入属性类型声明错误", beanName, field.getName());
                    logger.error(message.getMessage());
                    throw new ConfigurationException(message.getMessage());
                }

            }
        });
        return super.postProcessAfterInstantiation(bean, beanName);
    }

    private void injectEntityCacheService(Object bean, String beanName, Field field) {
        Class<? extends IEntity> clz = null;
        EntityCacheService service = null;
        try {
            Type type = field.getGenericType();
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            clz = (Class<? extends IEntity>) types[1];
            service = manager.getEntityService(clz);
        } catch (Exception e) {
            FormattingTuple message = MessageFormatter.format("Bean的注入属性类型声明错误", beanName, field.getName());
            logger.error(message.getMessage());
            throw new ConfigurationException(message.getMessage());
        }
        if (service == null) {
            FormattingTuple message = MessageFormatter.format("实体不存在", clz.getName());
            logger.debug(message.getMessage());
            throw new ConfigurationException(message.getMessage());
        }
        inject(bean, field, service);
    }

    private void injectRegionCacheService(Object bean, String beanName, Field field) {
        Class<? extends IEntity> clz = null;
        RegionCacheService service = null;
        try {
            Type type = field.getGenericType();
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            clz = (Class<? extends IEntity>) types[1];
            service = manager.getRegionService(clz);
        } catch (Exception e) {
            FormattingTuple message = MessageFormatter.format("Bean的注入属性类型声明错误", beanName, field.getName());
            logger.error(message.getMessage());
            throw new ConfigurationException(message.getMessage());
        }
        if (service == null) {
            FormattingTuple message = MessageFormatter.format("实体不存在", clz.getName());
            logger.debug(message.getMessage());
            throw new ConfigurationException(message.getMessage());
        }
        inject(bean, field, service);
    }

    private void inject(Object bean, Field field, Object value) {
        ReflectionUtils.makeAccessible(field);
        try {
            field.set(bean, value);
        } catch (Exception e) {
            FormattingTuple message = MessageFormatter.format("属性注入失败", field);
            logger.debug(message.getMessage());
            throw new ConfigurationException(message.getMessage());
        }
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }

}
