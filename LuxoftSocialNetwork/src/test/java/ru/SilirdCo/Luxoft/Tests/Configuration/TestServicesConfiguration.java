package ru.SilirdCo.Luxoft.Tests.Configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Util.Factories.DAO.DAOFactory;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Util.Factories.Services.ServiceFactory;

public class TestServicesConfiguration {
    private final static Logger logger = LoggerFactory.getLogger(TestServicesConfiguration.class);

    @BeforeTest
    public void beforeSuite() {
        logger.info(" [@] Начало тестирования сервисов");
        ServiceFactory.getInstance();
        DAOFactory.getInstance();
    }

    @AfterTest()
    public void afterSuite() {
        logger.info(" [@] Окончание тестирования сервисов");
    }
}
