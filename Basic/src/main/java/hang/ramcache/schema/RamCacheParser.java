package hang.ramcache.schema;

import com.hang.ramcache.IEntity;
import com.hang.ramcache.anno.Cached;
import com.hang.ramcache.exception.ConfigurationException;
import com.hang.ramcache.persist.PersistType;
import com.hang.ramcache.persist.PersisterConfig;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.SystemPropertyUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author ZhangHang
 * @create 2018-02-05 17:10
 **/
public class RamCacheParser extends AbstractBeanDefinitionParser {
    private static final Logger logger = LoggerFactory.getLogger(RamCacheParser.class);

    protected static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";
    private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
    private MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(this.resourcePatternResolver);

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
        registerInjectProcessor(parserContext);
        if (Boolean.valueOf(element.getAttribute(AttributeNames.LOCK_ASPECT))) {
            registerLockAspect(parserContext);
        }
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(ServiceManagerFactory.class);
        builder.addPropertyReference(ElementNames.ACCESSOR, getAccessorBeanName(element));
        builder.addPropertyReference(ElementNames.QUERIER, getQuerierBeanName(element));
        parseConstants2Bean(builder, (Element) DomUtils.getChildElementsByTagName(element, ElementNames.CONSTANTS), parserContext);
        Map<String, PersisterConfig> persisterConfigs = new HashMap<>();
        Element persisiterElement = DomUtils.getChildElementByTagName(element, ElementNames.PERSIST);
        PersistType type = PersistType.valueOf(persisiterElement.getAttribute(AttributeNames.TYPE));
        String value = persisiterElement.getAttribute(AttributeNames.CONFIG);
        persisterConfigs.put(com.hang.ramcache.anno.Persister.DEFAULT, new PersisterConfig(type, value));
        for (Element e : DomUtils.getChildElementsByTagName(persisiterElement, ElementNames.PERSISTER)) {
            String name = e.getAttribute(AttributeNames.NAME);
            type = PersistType.valueOf(e.getAttribute(AttributeNames.TYPE));
            value = e.getAttribute(AttributeNames.CONFIG);
            persisterConfigs.put(name, new PersisterConfig(type, value));
        }
        builder.addPropertyValue(ServiceManagerFactory.PERSISTER_CONFIG_NAME, persisterConfigs);
        Set<Class<? extends IEntity>> classes = new HashSet<>();
        NodeList child = DomUtils.getChildElementByTagName(element, ElementNames.ENTITY).getChildNodes();
        for (int i = 0; i < child.getLength(); i++) {
            Node node = child.item(i);
            if (node.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            String name = node.getLocalName();
            if (name.equals(ElementNames.PACKAGE)) {
                String packgeNames = ((Element) node).getAttribute(AttributeNames.NAME);
                String[] names = getResources(packgeNames);
                for (String resource : names) {
                    Class<? extends IEntity> clz = null;
                    try {
                        clz = (Class<? extends IEntity>) Class.forName(resource);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    classes.add(clz);
                }
            }
            if (name.equals(ElementNames.ClASS)) {
                String className = ((Element) node).getAttribute(AttributeNames.NAME);
                Class<? extends IEntity> clz = null;
                try {
                    clz = (Class<? extends IEntity>) Class.forName(className);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                classes.add(clz);
            }
        }
        builder.addPropertyValue(ServiceManagerFactory.ENTITY_CLASSES_NAME, classes);
        return builder.getBeanDefinition();
    }

    private void parseConstants2Bean(BeanDefinitionBuilder builder, Element element, ParserContext parserContext) {
        String ref = element.getAttribute(AttributeNames.REF);
        if (StringUtils.isNotBlank(ref)) {
            builder.addPropertyReference(ElementNames.CONSTANTS, ref);
            return;
        }

        ManagedMap<String, Integer> constants = new ManagedMap<>();
        for (Element e : DomUtils.getChildElementsByTagName(element, ElementNames.CONSTANT)) {
            String name = e.getAttribute(AttributeNames.NAME);
            Integer value = Integer.parseInt(e.getAttribute(AttributeNames.SIZE));
            constants.put(name, value);
        }
        builder.addPropertyValue(ElementNames.CONSTANTS, constants);
    }

    private void registerLockAspect(ParserContext parserContext) {
        BeanDefinitionRegistry registry = parserContext.getRegistry();
        String name = StringUtils.uncapitalize(InjectProcessor.class.getSimpleName());
        BeanDefinitionBuilder factory = BeanDefinitionBuilder.rootBeanDefinition(InjectProcessor.class);
        registry.registerBeanDefinition(name, factory.getBeanDefinition());
    }

    private void registerInjectProcessor(ParserContext parserContext) {
        BeanDefinitionRegistry registry = parserContext.getRegistry();
        String name = StringUtils.uncapitalize(InjectProcessor.class.getSimpleName());
        BeanDefinitionBuilder factory = BeanDefinitionBuilder.rootBeanDefinition(InjectProcessor.class);
        registry.registerBeanDefinition(name, factory.getBeanDefinition());
    }

    private String getQuerierBeanName(Element element) {
        element = ParserHelper.getUniqueChildElementByTageName(element, ElementNames.QUERIER);
        if (element.hasAttribute(AttributeNames.REF)) {
            return element.getAttribute(AttributeNames.REF);
        }
        throw new ConfigurationException("缺失");
    }

    private String getAccessorBeanName(Element element) {
        element = ParserHelper.getUniqueChildElementByTageName(element, ElementNames.ACCESSOR);
        if (element.hasAttribute(AttributeNames.REF)) {
            return element.getAttribute(AttributeNames.REF);
        }
        throw new ConfigurationException("存储配置声明缺失");
    }

    private String[] getResources(String packageName) {
        try {
            String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + resolveBasePackage(packageName) + "/" + DEFAULT_RESOURCE_PATTERN;
            Resource[] resources = this.resourcePatternResolver.getResources(packageSearchPath);
            Set<String> result = new HashSet<>();
            String name = Cached.class.getName();
            for (Resource resource : resources) {
                if (!resource.isReadable()) {
                    continue;
                }
                MetadataReader metadataReader = this.metadataReaderFactory.getMetadataReader(resource);
                AnnotationMetadata annoMeta = metadataReader.getAnnotationMetadata();
                if (!annoMeta.hasAnnotation(name)) {
                    continue;
                }
                ClassMetadata clzMeta = metadataReader.getClassMetadata();
                result.add(clzMeta.getClassName());
            }
            return result.toArray(new String[0]);
        } catch (IOException e) {
            String message = "无法读取";
            throw new ConfigurationException(message, e);
        }
    }


    protected String resolveBasePackage(String basePackage) {
        return ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(basePackage));
    }
}
