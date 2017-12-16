package ru.SilirdCo.Luxoft.CodeGen.Generation.Generators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.SilirdCo.Luxoft.CodeGen.Generation.Entity.GenerationConfiguration;
import ru.SilirdCo.Luxoft.CodeGen.Generation.Entity.GenerationMapping;
import ru.SilirdCo.Luxoft.CodeGen.Generation.Generators.Infrastructure.Attribute.GenerationAttribute;
import ru.SilirdCo.Luxoft.CodeGen.Generation.Generators.Server.DAO.GenerationDAO;
import ru.SilirdCo.Luxoft.CodeGen.Generation.Generators.Server.Entity.GenerationEntity;
import ru.SilirdCo.Luxoft.CodeGen.Generation.Generators.Server.Util.Factory.DAO.GenerationDAOFactory;
import ru.SilirdCo.Luxoft.CodeGen.Generation.Generators.Server.Util.Factory.DAO.GenerationDAOFactoryXML;
import ru.SilirdCo.Luxoft.CodeGen.Generation.Generators.Server.Util.Factory.Service.GenerationServiceFactory;
import ru.SilirdCo.Luxoft.CodeGen.Generation.Generators.Server.Util.Factory.Service.GenerationServiceFactoryXML;
import ru.SilirdCo.Luxoft.CodeGen.Generation.Util.SpringFactory;

public class Generation {
    private static final Logger logger = LoggerFactory.getLogger(Generation.class);

    private GenerationConfiguration configuration;

    public Generation(GenerationConfiguration configuration) {
        this.configuration = configuration;
    }

    public boolean start() {
        logger.info("Starting generation...\n\n");

        GenerationEntity entity = new GenerationEntity();
        GenerationAttribute attribute = new GenerationAttribute();
        GenerationDAO dao = new GenerationDAO();


        GenerationDAOFactoryXML daoFactoryXML = new GenerationDAOFactoryXML();
        GenerationServiceFactoryXML serviceFactoryXML = new GenerationServiceFactoryXML();

        if (!daoFactoryXML.start(configuration.getTables())) {
            logger.error("Ошибка генерации маппинга для фабрики ДАО");
            return false;
        }
        if (!serviceFactoryXML.start(configuration.getTables())) {
            logger.error("Ошибка генерации маппинга для фабрики сервисов");
            return false;
        }

        GenerationDAOFactory daoFactory = new GenerationDAOFactory();
        GenerationServiceFactory serviceFactory = new GenerationServiceFactory();

        if (!daoFactory.start(configuration.getTables())) {
            logger.error("Ошибка генерации фабрики ДАО");
            return false;
        }
        if (!serviceFactory.start(configuration.getTables())) {
            logger.error("Ошибка генерации фабрики сервисов");
            return false;
        }

        for (String tableName : configuration.getTables()) {
            GenerationMapping mapping = SpringFactory.getInstance().getMapping(tableName);

            if (!entity.start(mapping)) {
                logger.error("Ошибка генерации сущности: " + tableName);
                return false;
            }

            if (!attribute.start(mapping)) {
                logger.error("Ошибка генерации аттрибутов для таблицы: " + tableName);
                return false;
            }

            if (!dao.start(mapping)) {
                logger.error("Ошибка генерации дао для таблицы: " + tableName);
                return false;
            }
        }


        logger.info("Finishing generation...\n\n");
        return true;
    }

    public void setConfiguration(GenerationConfiguration configuration) {
        this.configuration = configuration;
    }
}
