package ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Util.Configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Util.ExceptionHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class DataBaseConfigurationFactory {
    private final static Logger logger = LoggerFactory.getLogger(DataBaseConfigurationFactory.class);
    private static final String separator = File.separator;
    private ApplicationContext context = new FileSystemXmlApplicationContext(/*System.getProperty("user.dir") + */"/Configuration/DataBaseConfiguration.xml");

    private static DataBaseConfigurationFactory instance;

    public static DataBaseConfigurationFactory getInstance() {
        if (instance == null) {
            instance = new DataBaseConfigurationFactory();
        }
        return instance;
    }

    public DataBaseConfiguration getDataBaseConfiguration() {
        return context.getBean("dataBaseConfiguration", DataBaseConfiguration.class);
    }

    public static void generateContext() {
        try {
            File file = new File(System.getProperty("user.dir") + separator + "Configuration" + separator + "DataBaseConfiguration.xml");
            if (!file.exists()) {
                File parent = file.getParentFile();
                if (parent.mkdirs()) {
                    if (!file.createNewFile()) {
                        logger.warn("Файл конфигурации не был создан");
                    }
                }

                try (PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file.getAbsoluteFile()), StandardCharsets.UTF_8))) {
                    //Записываем текст в файл
                    out.print("<beans xmlns=\"http://www.springframework.org/schema/beans\"\n" +
                            "       xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                            "       xsi:schemaLocation=\"http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd\">\n" +
                            "\n" +
                            "    <bean id=\"dataBaseConfiguration\" class=\"ru.iqtech.IQShop.Server.core.impl.Util.Configuration.DataBaseConfiguration\" scope=\"prototype\">\n" +
                            "        <property name=\"url\" value=\"jdbc:postgresql://host:port/table\"/>\n" +
                            "        <property name=\"user\" value=\"\"/>\n" +
                            "        <property name=\"password\" value=\"\"/>\n" +
                            "        <property name=\"driver\" value=\"org.postgresql.Driver\"/>\n" +
                            "        <property name=\"dialect\" value=\"org.hibernate.dialect.PostgreSQL95Dialect\"/>\n" +
                            "    </bean>\n" +
                            "</beans>");
                }

                logger.info("Файл конфигурации базы данных успешно создан!");
            }
        }
        catch (IOException ex) {
            ExceptionHandler.handle(logger, ex);
        }
    }
}
