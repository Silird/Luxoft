package ru.SilirdCo.Luxoft.CodeGen.Generation.Generators.Util.Factory.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.SilirdCo.Luxoft.CodeGen.Generation.Entity.GenerationMapping;
import ru.SilirdCo.Luxoft.CodeGen.Generation.Util.GenerationUtils;

import java.io.IOException;
import java.util.List;

public class GenerationServiceFactoryXML {
    private static final Logger logger = LoggerFactory.getLogger(GenerationServiceFactoryXML.class);

    private StringBuilder mainPart = new StringBuilder();

    public GenerationServiceFactoryXML() {

    }

    public boolean start(List<String> tableNames) {

        mainPart.delete(0, mainPart.length());

        generateMainPart(tableNames);

        StringBuilder file = new StringBuilder();

        file.append(mainPart.toString());

        String filePath = "LuxoftSocialNetwork.src.main.resources.Factories.Services.Generated.";
        try {
            String fileName = "ServiceFactory.xml";
            GenerationUtils.writeXML(file.toString(), fileName, filePath);
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private void generateMainPart(List<String> tableNames) {
        mainPart.append("<beans xmlns=\"http://www.springframework.org/schema/beans\"\n" +
                "       xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "       xsi:schemaLocation=\"http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd\">\n" +
                "\n");

        for (String tableName : tableNames) {
            GenerationMapping mapping = ru.SilirdCo.Luxoft.CodeGen.Generation.Util.SpringFactory.getInstance().getMapping(tableName);

            String name = GenerationUtils.getName(mapping.getTableName());
            String titleName = GenerationUtils.getTitleName(mapping.getTableName());

            mainPart.append("    <bean id=\"" + name + "Service\" " +
                    "class=\"ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Services.impl." + titleName + "Service\"/>\n");
        }

        mainPart.append("\n" +
                "</beans>");
    }
}