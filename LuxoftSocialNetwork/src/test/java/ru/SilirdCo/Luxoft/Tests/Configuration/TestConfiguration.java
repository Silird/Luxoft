package ru.SilirdCo.Luxoft.Tests.Configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Util.PersistenceUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.UUID;

public class TestConfiguration {
    private final static Logger logger = LoggerFactory.getLogger(TestConfiguration.class);

    private Connection connection;
    private Statement statement;
    private String database;

    @BeforeSuite()
    public void beforeSuite() throws SQLException {
        logger.info(" [~] Запуск сессии");

        Map<String, Object> properties =  PersistenceUtil.getSessionProperties("test");
        String url = (String) properties.get("javax.persistence.jdbc.url");
        String login = (String) properties.get("hibernate.connection.username");
        String password = (String) properties.get("javax.persistence.jdbc.password");

        connection = DriverManager.getConnection(url, login, password);
        statement = connection.createStatement();

        database = String.valueOf(UUID.randomUUID());
        String query = "CREATE DATABASE \"" + database + "\"";
        statement.executeUpdate(query);

        url += "/" + database;
        PersistenceUtil.initSession("test", url);
    }

    @AfterSuite()
    public void afterSuite() throws SQLException {
        logger.info(" [~] Закрытие сессии");
        PersistenceUtil.closeSession();

        String query = "DROP DATABASE \"" + database + "\"";
        statement.executeUpdate(query);
        statement.close();
        connection.close();
    }
}
