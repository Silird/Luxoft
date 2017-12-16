package ru.SilirdCo.Luxoft.CodeGen.Generation.Generators.Server.Util.Factory.DAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.SilirdCo.Luxoft.CodeGen.Generation.Entity.GenerationMapping;
import ru.SilirdCo.Luxoft.CodeGen.Generation.Util.GenerationUtils;

import java.io.IOException;
import java.util.List;

public class GenerationDAOFactory {
    private static final Logger logger = LoggerFactory.getLogger(GenerationDAOFactory.class);

    private StringBuilder imports = new StringBuilder();

    private StringBuilder mainPart = new StringBuilder();

    public GenerationDAOFactory() {

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
            String fileName = "DAOFactoryGenerated.java";
            GenerationUtils.write(file.toString(), fileName);
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private void generateImports(List<String> tableNames) {
        imports.append("package ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Util.Factories.DAO.Generated;\n" +
                "\n" +
                "import org.springframework.context.ApplicationContext;\n" +
                "import org.springframework.context.support.ClassPathXmlApplicationContext;\n" +
                "import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.DAO.DB.Generated.*;\n" +
                "import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Entities.Generated.*;\n" +
                "import ru.SilirdCo.Luxoft.SocialNetwork.core.interfaces.DAO.IDAO;\n" +
                "\n");
    }

    private void generateMainPart(List<String> tableNames) {
        mainPart.append("public class DAOFactoryGenerated {\n" +
                "    private ApplicationContext context = new ClassPathXmlApplicationContext(\"Factories/DAO/Generated/DAOFactory.xml\");\n" +
                "\n" +
                "    private static DAOFactoryGenerated instance;\n" +
                "\n" +
                "    public static DAOFactoryGenerated getInstance() {\n" +
                "        if (instance == null) {\n" +
                "            instance = new DAOFactoryGenerated();\n" +
                "        }\n" +
                "        return instance;\n" +
                "    }\n" +
                "\n");

        for (String tableName : tableNames) {
            GenerationMapping mapping = ru.SilirdCo.Luxoft.CodeGen.Generation.Util.SpringFactory.getInstance().getMapping(tableName);

            String titleName = GenerationUtils.getTitleName(mapping.getTableName());
            String name = GenerationUtils.getName(mapping.getTableName());

            mainPart.append("    public IDAO<" + titleName + "> get" + titleName + "DAO() {\n" +
                    "        return context.getBean(\"" + name + "DAO\", " + titleName + "DAO.class);\n" +
                    "    }\n" +
                    "\n");
        }

        mainPart.append("}");
    }
}