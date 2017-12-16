package ru.SilirdCo.Luxoft.CodeGen.Generation.Generators.DAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.SilirdCo.Luxoft.CodeGen.Generation.Entity.GenerationMapping;
import ru.SilirdCo.Luxoft.CodeGen.Generation.Util.GenerationUtils;

import java.io.IOException;

public class GenerationDAO {
    private static final Logger logger = LoggerFactory.getLogger(GenerationDAO.class);

    private StringBuilder imports = new StringBuilder();

    private StringBuilder header = new StringBuilder();

    private StringBuilder mainPart = new StringBuilder();

    private StringBuilder footer = new StringBuilder();

    public GenerationDAO() {

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
            String fileName = GenerationUtils.getTitleName(mapping.getTableName()) + "DAO.java";
            GenerationUtils.write(file.toString(), fileName);
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private void generateImports(GenerationMapping mapping) {
        String titleName = GenerationUtils.getTitleName(mapping.getTableName());

        imports.append("package ru.SilirdCo.Luxoft.SocialNetwork.core.impl.DAO.DB.Generated;\n" +
                "\n" +
                "import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Entities.Generated." + titleName + ";\n" +
                "import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.DAO.DB.BaseDAO;\n" +
                "\n");
    }

    private void generateHeader(GenerationMapping mapping) {
        String titleName = GenerationUtils.getTitleName(mapping.getTableName());

        header.append("/**\n" +
                " * Реализация доступа к базе данных для сущностей " + titleName + "\n" +
                " * @see " + titleName + "\n" +
                " */\n" +
                "public class " + titleName + "DAO extends BaseDAO<" + titleName + "> {\n");
    }

    private void generateFooter(GenerationMapping mapping) {
        footer.append("\n" +
                "}");
    }

    private void generateMainPart(GenerationMapping mapping) {
        String titleName = GenerationUtils.getTitleName(mapping.getTableName());
        String mainName;
        if (GenerationUtils.getSortColumn(mapping) == null) {
            mainName = GenerationUtils.getName(GenerationUtils.getMainNameColumn(mapping).getName());
        }
        else {
            mainName = GenerationUtils.getName(GenerationUtils.getSortColumn(mapping).getName());
        }

        mainPart.append("\n" +
                "    public " + titleName + "DAO() {\n" +
                "        super(" + titleName + ".class, " + titleName + "::new);\n" +
                "        orderAttribute = \"" + mainName + "\";\n" +
                "    }\n");
    }
}
