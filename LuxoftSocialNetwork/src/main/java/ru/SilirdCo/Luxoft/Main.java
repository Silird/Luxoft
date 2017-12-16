package ru.SilirdCo.Luxoft;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.LaunchCore;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.CommandService;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Frames.MainJavaFX;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.LaunchView;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        if (!LaunchCore.launch()) {
            logger.error("Ошибка запуска ядра");
            System.exit(1);
        }
        if (!LaunchView.launch()) {
            logger.error("Ошибка запуска формы");
            System.exit(2);
        }
    }
}
