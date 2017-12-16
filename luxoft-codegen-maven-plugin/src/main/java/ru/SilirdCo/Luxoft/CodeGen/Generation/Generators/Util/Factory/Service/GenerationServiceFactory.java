package ru.SilirdCo.Luxoft.CodeGen.Generation.Generators.Util.Factory.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.SilirdCo.Luxoft.CodeGen.Generation.Entity.GenerationMapping;
import ru.SilirdCo.Luxoft.CodeGen.Generation.Util.GenerationUtils;

import java.io.IOException;
import java.util.List;

public class GenerationServiceFactory {
    private static final Logger logger = LoggerFactory.getLogger(GenerationServiceFactory.class);

    private StringBuilder imports = new StringBuilder();

    private StringBuilder mainPart = new StringBuilder();

    public GenerationServiceFactory() {

    }

    public boolean start(List<String> tableNames) {

        imports.delete(0, imports.length());

        mainPart.delete(0, mainPart.length());

        generateImports(tableNames);

        generateMainPart(tableNames);

        StringBuilder file = new StringBuilder();

        file.append(imports.toString())
                .append(mainPart.toString());
        try {
            String fileName = "ServiceFactoryGenerated.java";
            GenerationUtils.write(file.toString(), fileName);
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private void generateImports(List<String> tableNames) {
        imports.append("package ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Util.Factories.Services.Generated;\n" +
                "\n" +
                "import org.springframework.context.ApplicationContext;\n" +
                "import org.springframework.context.support.ClassPathXmlApplicationContext;\n" +
                "import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Entities.Generated.*;\n" +
                "import ru.SilirdCo.Luxoft.SocialNetwork.core.interfaces.Services.IService;\n" +
                "import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Services.impl.*;\n" +
                "\n");
    }

    private void generateMainPart(List<String> tableNames) {
        mainPart.append("public class ServiceFactoryGenerated {\n" +
                "    private ApplicationContext context = new ClassPathXmlApplicationContext(\"Factories/Services/Generated/ServiceFactory.xml\");\n" +
                "\n" +
                "    private static ServiceFactoryGenerated instance;\n" +
                "\n" +
                "    public static ServiceFactoryGenerated getInstance() {\n" +
                "        if (instance == null) {\n" +
                "            instance = new ServiceFactoryGenerated();\n" +
                "        }\n" +
                "        return instance;\n" +
                "    }\n" +
                "\n");

        for (String tableName : tableNames) {
            GenerationMapping mapping = ru.SilirdCo.Luxoft.CodeGen.Generation.Util.SpringFactory.getInstance().getMapping(tableName);

            String titleName = GenerationUtils.getTitleName(mapping.getTableName());
            String name = GenerationUtils.getName(mapping.getTableName());

            mainPart.append("    public IService<" + titleName + "> get" + titleName + "Service() {\n" +
                    "        return context.getBean(\"" + name + "Service\", " + titleName + "Service.class);\n" +
                    "    }\n" +
                    "\n");
        }

        mainPart.append("}");
    }
}