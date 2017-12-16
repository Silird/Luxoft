package ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Util;

import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Util.Configuration.DataBaseConfiguration;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Util.Configuration.DataBaseConfigurationFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class PersistenceUtil {
    private static final Logger logger = LoggerFactory.getLogger(PersistenceUtil.class);

    private static EntityManagerFactory session = null;

    private static EntityManager manager = null;

    public static EntityManager getSession() {
        if (manager == null) {
            manager = session.createEntityManager();
        }

        return manager;
    }

    public static boolean initSession() {
        if (isInitialized()) {
            logger.warn("Сессия уже инициализирована");
            return false;
        }
        return initSessionWithParameters("jpa");
    }

    public static boolean initSession(String name) {
        if (isInitialized()) {
            logger.warn("Сессия уже инициализирована");
            return false;
        }
        try {
            session = Persistence.createEntityManagerFactory(name);
            return true;
        }
        catch (Exception ex) {
            ExceptionHandler.handle(logger, ex);
            return false;
        }
    }

    public static boolean initSessionWithParameters(String name) {
        if (isInitialized()) {
            logger.warn("Сессия уже инициализирована");
            return false;
        }
        try {
            boolean isNewDB = false;

            DataBaseConfiguration configuration = DataBaseConfigurationFactory.getInstance().getDataBaseConfiguration();

            Connection connection = null;
            try {
                Class.forName(configuration.getDriver());
                connection = DriverManager.getConnection(configuration.getUrl(),
                        configuration.getUser(), configuration.getPassword());
                connection.close();
            }
            catch (PSQLException ex) {
                logger.info("\n\n\nБазы данных не существует, создание...");
                logger.info("Создание...");

                if ((connection != null) && !connection.isClosed()) {
                    connection.close();
                }

                try {
                    String[] tmp = configuration.getUrl().split("/");
                    if (tmp.length != 4) {
                        logger.error("Неверно задан url для базы данных в конфигурации");
                        return false;
                    }

                    String dataBaseUrl = tmp[0] + "//" + tmp[2];
                    String dataBaseName = tmp[3];
                    connection = DriverManager.getConnection(dataBaseUrl,
                            configuration.getUser(), configuration.getPassword());
                    Statement statement = connection.createStatement();

                    String query = "CREATE DATABASE \"" + dataBaseName + "\"";
                    statement.executeUpdate(query);

                    statement.close();
                    connection.close();


                    logger.info("База данных успешно создана\n\n\n");
                    isNewDB = true;
                }
                catch (Exception ex1) {
                    logger.error("Ошибка при создании базы данных");
                    ExceptionHandler.handle(logger, ex1);
                    return false;
                }
            }


            Map<String, Object> properties = new HashMap<>();
            properties.put("javax.persistence.jdbc.url", configuration.getUrl());
            properties.put("javax.persistence.jdbc.user", configuration.getUser());
            properties.put("javax.persistence.jdbc.password", configuration.getPassword());
            properties.put("javax.persistence.jdbc.driver", configuration.getDriver());
            properties.put("hibernate.dialect", configuration.getDialect());
            session = Persistence.createEntityManagerFactory(name, properties);

            /*
            if (isNewDB) {
                DataBaseFill.generate();
            }
            */

            return true;
        }
        catch (Exception ex) {
            ExceptionHandler.handle(logger, ex);
            DataBaseConfigurationFactory.generateContext();
            return false;
        }
    }

    public static boolean initSession(String name, String url) {
        try {
            Map<String, Object> properties = new HashMap<>();
            properties.put("javax.persistence.jdbc.url", url);
            session = Persistence.createEntityManagerFactory(name, properties);
            return true;
        }
        catch (Exception ex) {
            ExceptionHandler.handle(logger, ex);
            return false;
        }
    }

    public static Map<String, Object> getSessionProperties(String name) {
        session = Persistence.createEntityManagerFactory(name);

        Map<String, Object> properties = session.getProperties();
        session.close();
        session = null;

        return properties;
    }

    public static PersistenceException ExceptionHandler(Exception ex) {
        ExceptionHandler.handle(logger, ex);
        if (ex instanceof PersistenceException) {
            return  (PersistenceException) ex;
        }
        return null;
    }

    public static void closeSession() {
        if (session != null) {
            if (session.isOpen()) {
                logger.info("Сессия закрывается...");
                session.close();
                session = null;
                manager = null;
                logger.info("Сессия закрыта");
            }
        }
    }

    public static boolean isInitialized() {
        return ((session != null) && (session.isOpen()));
    }
}
