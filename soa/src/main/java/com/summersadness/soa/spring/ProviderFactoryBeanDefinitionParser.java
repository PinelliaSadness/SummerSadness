package com.summersadness.soa.spring;

import com.summersadness.soa.provider.ProviderFactoryBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

/**
 *
 * @author PinelliaSadness@Gmail.com
 * @version 1.0.0
 * @date 2019/3/28 14:50
 */
public class ProviderFactoryBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    //logger
    private static final Logger logger = LoggerFactory.getLogger(ProviderFactoryBeanDefinitionParser.class);


    @Override
    protected Class getBeanClass(Element element) {
        return ProviderFactoryBean.class;
    }

    @Override
    protected void doParse(Element element, BeanDefinitionBuilder bean) {

        try {
            String serviceItf = element.getAttribute("inter");
            String timeOut = element.getAttribute("timeout");
            String serverPort = element.getAttribute("serverPort");
            String ref = element.getAttribute("ref");
            String weight = element.getAttribute("weight");
            String workerThreads = element.getAttribute("workerThreads");
            String appKey = element.getAttribute("appKey");
            String groupName = element.getAttribute("groupName");

            bean.addPropertyValue("serverPort", Integer.parseInt(serverPort));
            bean.addPropertyValue("timeout", Integer.parseInt(timeOut));
            bean.addPropertyValue("serviceItf", Class.forName(serviceItf));
            bean.addPropertyReference("serviceObject", ref);
            bean.addPropertyValue("appKey", appKey);

            if (NumberUtils.isNumber(weight)) {
                bean.addPropertyValue("weight", Integer.parseInt(weight));
            }
            if (NumberUtils.isNumber(workerThreads)) {
                bean.addPropertyValue("workerThreads", Integer.parseInt(workerThreads));
            }
            if (StringUtils.isNotBlank(groupName)) {
                bean.addPropertyValue("groupName", groupName);
            }
        } catch (Exception e) {
            logger.error("ProviderFactoryBeanDefinitionParser error.", e);
            throw new RuntimeException(e);
        }

    }


}
