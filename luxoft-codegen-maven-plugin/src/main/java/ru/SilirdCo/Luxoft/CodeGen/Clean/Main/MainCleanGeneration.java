package ru.SilirdCo.Luxoft.CodeGen.Clean.Main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.SilirdCo.Luxoft.CodeGen.Clean.Util.ClearUtils;

public class MainCleanGeneration {
    private final static Logger logger = LoggerFactory.getLogger(MainCleanGeneration.class);

    public static void main(String[] args) {
        if (ClearUtils.clearGenerated(System.getProperty("user.dir"))) {
            logger.info("Успех");
        }
        else {
            logger.error("Неудача");
        }
    }
}
