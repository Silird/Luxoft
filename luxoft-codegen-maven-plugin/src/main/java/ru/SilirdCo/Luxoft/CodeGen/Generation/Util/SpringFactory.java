package ru.SilirdCo.Luxoft.CodeGen.Generation.Util;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.SilirdCo.Luxoft.CodeGen.Generation.Entity.GenerationConfiguration;
import ru.SilirdCo.Luxoft.CodeGen.Generation.Entity.GenerationMapping;

import java.util.HashMap;
import java.util.Map;

public class SpringFactory {
    private ApplicationContext configurationContext = new ClassPathXmlApplicationContext("Factory/Generation/Configuration.xml");

    private Map<String, ApplicationContext> tablesContext = new HashMap<>();

    private static SpringFactory instance;

    public static SpringFactory getInstance() {
        if (instance == null) {
            instance = new SpringFactory();
        }
        return instance;
    }

    public GenerationConfiguration getConfiguration() {
        return configurationContext.getBean("configuration", GenerationConfiguration.class);
    }

    public GenerationMapping getMapping(String tableName) {
        ApplicationContext tableContext;
        if (tablesContext.containsKey(tableName)) {
            tableContext = tablesContext.get(tableName);
        }
        else {
            try {
                tableContext = new ClassPathXmlApplicationContext("Factory/Entity/" + tableName + ".xml");
                tablesContext.put(tableName, tableContext);
            }
            catch (BeanDefinitionStoreException ex) {
                ex.printStackTrace();
                return null;
            }
        }

        return tableContext.getBean("table", GenerationMapping.class);
    }
}
