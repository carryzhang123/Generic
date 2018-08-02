package com.hang.ramcache.schema;

import com.hang.ramcache.exception.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import java.util.List;

/**
 * @author ZhangHang
 * @create 2018-02-05 17:04
 **/
public abstract class ParserHelper {
    private static final Logger logger = LoggerFactory.getLogger(ParserHelper.class);

    public static boolean hasChildElementsByTageName(Element parent, String tagName) {
        List<Element> elements = DomUtils.getChildElementsByTagName(parent, tagName);
        if (elements.size() > 0) {
            return true;
        }
        return false;
    }

    public static Element getUniqueChildElementByTageName(Element element, String tagName) {
        List<Element> elements = DomUtils.getChildElementsByTagName(element, tagName);
        if (elements.size() != 1) {
            FormattingTuple message = MessageFormatter.format("Tag数量不唯一", tagName, elements.size());
            logger.error(message.getMessage());
            throw new ConfigurationException(message.getMessage());
        }
        return elements.get(0);
    }
}
