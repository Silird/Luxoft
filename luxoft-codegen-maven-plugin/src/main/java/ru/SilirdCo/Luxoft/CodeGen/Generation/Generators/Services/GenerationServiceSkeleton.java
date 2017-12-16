package ru.SilirdCo.Luxoft.CodeGen.Generation.Generators.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.SilirdCo.Luxoft.CodeGen.Generation.Entity.GenerationMapping;
import ru.SilirdCo.Luxoft.CodeGen.Generation.Util.GenerationUtils;

import java.io.IOException;

public class GenerationServiceSkeleton {
    private static final Logger logger = LoggerFactory.getLogger(GenerationServiceSkeleton.class);

    private StringBuilder imports = new StringBuilder();

    private StringBuilder header = new StringBuilder();

    private StringBuilder mainPart = new StringBuilder();

    private StringBuilder footer = new StringBuilder();

    public GenerationServiceSkeleton() {

    }

    public boolean start(GenerationMapping mapping) {

        imports.delete(0, imports.length());

        header.delete(0, header.length());

        mainPart.delete(0, mainPart.length());

        footer.delete(0, footer.length());

        generateImports(mapping);

        generateHeader(mapping);

        generateFooter(mapping);

        generateMainPart(mapping);

        StringBuilder file = new StringBuilder();

        file.append(imports.toString())
                .append(header.toString())
                .append(mainPart.toString())
                .append(footer.toString());

        try {
            String fileName = GenerationUtils.getTitleName(mapping.getTableName()) + "Service.java";
            GenerationUtils.writeNotExist(file.toString(), fileName);
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private void generateImports(GenerationMapping mapping) {
        String titleName = GenerationUtils.getTitleName(mapping.getTableName());

        imports.append("package ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Services.impl;\n" +
                "\n" +
                "import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Entities.Generated." + titleName + ";\n" +
                "import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Services.BaseService;\n" +
                "import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Util.Factories.DAO.DAOFactory;\n" +
                "\n");
    }

    private void generateHeader(GenerationMapping mapping) {
        String titleName = GenerationUtils.getTitleName(mapping.getTableName());

        header.append("/**\n" +
                " * Реализация доступа к базе данных для сущностей " + titleName + "\n" +
                " * @see " + titleName + "\n" +
                " */\n" +
                "public class " + titleName + "Service extends BaseService<" + titleName + "> {\n");
    }

    private void generateFooter(GenerationMapping mapping) {
        footer.append("\n" +
                "}");
    }

    private void generateMainPart(GenerationMapping mapping) {
        String titleName = GenerationUtils.getTitleName(mapping.getTableName());

        mainPart.append("\n" +
                "    public " + titleName + "Service() {\n" +
                "        super(DAOFactory.getInstance()\n" +
                "                .get" + titleName + "DAO());\n" +
                "    }\n");
    }
}
