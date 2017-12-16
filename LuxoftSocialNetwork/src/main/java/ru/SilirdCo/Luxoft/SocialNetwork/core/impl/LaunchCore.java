package ru.SilirdCo.Luxoft.SocialNetwork.core.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Util.ExceptionHandler;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Util.Factories.DAO.DAOFactory;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Util.Factories.Services.ServiceFactory;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Util.PersistenceUtil;

public class LaunchCore {
    private static final Logger logger = LoggerFactory.getLogger(LaunchCore.class);

    public static boolean launch() {
        logger.info("\n\nЗапуск ядра\n\n");

        try {
            logger.info("\n\nИнициализация сессии..\n\n");
            if (!PersistenceUtil.initSession()) {
                return false;
            }

            logger.info("\n\nИнициализация фабрик..\n\n");
            DAOFactory.getInstance();
            ServiceFactory.getInstance();
        }
        catch (Exception ex) {
            ExceptionHandler.handle(logger, ex);

            return false;
        }

        return true;
    }
}
