package ru.SilirdCo.Luxoft.SocialNetwork.view.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Util.ExceptionHandler;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.CommandService;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Frames.MainJavaFX;

public class LaunchView {
    private static final Logger logger = LoggerFactory.getLogger(LaunchView.class);

    public static boolean launch() {
        logger.info("\n\nЗапуск визуальной формы\n\n");

        try {
            logger.info("\n\nИнициализация сервиса команд..\n\n");
            new CommandService();

            logger.info("\n\nЗапуск формы..\n\n");
            MainJavaFX.show(new String[0]);
        }
        catch (Exception ex) {
            ExceptionHandler.handle(logger, ex);

            return false;
        }

        return true;
    }
}
