package ru.SilirdCo.Luxoft.CodeGen.Generation.Generators.Attribute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.SilirdCo.Luxoft.CodeGen.Generation.Entity.GenerationColumn;
import ru.SilirdCo.Luxoft.CodeGen.Generation.Entity.GenerationLinkColumn;
import ru.SilirdCo.Luxoft.CodeGen.Generation.Entity.GenerationMapping;
import ru.SilirdCo.Luxoft.CodeGen.Generation.Util.*;

import java.io.IOException;

public class GenerationAttribute {
    private static final Logger logger = LoggerFactory.getLogger(GenerationAttribute.class);

    private GenerationImports generationImports = new GenerationImports();

    private StringBuilder header = new StringBuilder();

    private StringBuilder imports = new StringBuilder();

    private StringBuilder footer = new StringBuilder();

    private StringBuilder oneAttribute = new StringBuilder();

    private StringBuilder twoAttribute = new StringBuilder();

    private StringBuilder listAttribute = new StringBuilder();

    public GenerationAttribute() {

    }

    public boolean start(GenerationMapping mapping) {
        generationImports.clear();

        imports.delete(0, imports.length());

        header.delete(0, header.length());

        oneAttribute.delete(0, oneAttribute.length());

        footer.delete(0, footer.length());

        twoAttribute.delete(0, twoAttribute.length());

        listAttribute.delete(0, listAttribute.length());

        generateImports(mapping);

        generateHeader(mapping);

        generateFooter(mapping);

        generateOneAttribute(mapping);

        generateGetAttribute(mapping);

        generateGetAttributeList(mapping);

        if (!generationImports.isEmpty()) {
            String im = generationImports.getImports();
            imports.append(im);
            imports.append("\n");
        }

        StringBuilder file = new StringBuilder();

        file.append(imports.toString())
                .append(header.toString())
                .append(oneAttribute.toString())
                .append(twoAttribute.toString())
                .append(listAttribute.toString())
                .append(footer.toString());
        try {
            String fileName = GenerationUtils.getTitleName(mapping.getTableName()) + "Attribute.java";
            GenerationUtils.write(file.toString(), fileName);
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private void generateImports(GenerationMapping mapping) {
        imports.append("package ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Attributes.Generated;\n");

        imports.append("\n");

        imports.append("import org.slf4j.Logger;\n" +
                "import org.slf4j.LoggerFactory;\n" +
                "import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Attributes.*;\n" +
                "\n" +
                "import java.util.ArrayList;\n" +
                "import java.text.ParseException;\n" +
                "import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Util.Structure;\n" +
                "import java.util.List;\n");

        imports.append("\n");
    }

    private void generateHeader(GenerationMapping mapping) {
        String titleName = GenerationUtils.getTitleName(mapping.getTableName());

        header.append("    public class " + titleName + "Attribute {\n\n" +
                "    private static final Logger logger = LoggerFactory.getLogger(" + titleName + "Attribute.class);\n");
        header.append("    public static final int ID = 0;\n");

        Integer i = 0;
        if (mapping.getColumns() != null) {
            for (GenerationColumn column : mapping.getColumns()) {
                String uppedCase = column.getName().toUpperCase();

                i++;
                header.append("    public static final int " + uppedCase + " = " + i + ";\n");
            }
        }

        if (mapping.getLinkColumns() != null) {
            for (GenerationLinkColumn column : mapping.getLinkColumns()) {
                String uppedCase = column.getName().toUpperCase();

                i++;
                header.append("    public static final int " + uppedCase + " = " + i + ";\n");
            }
        }

        generateParentHeader(mapping, header, i);

        header.append("\n\n");
    }

    private void generateOneAttribute(GenerationMapping mapping) {
        oneAttribute.append("    public static ElementAttribute getAttribute(int type, int field, String value)  {\n" +
                "        switch (field) {\n" +
                "            case ID: {\n" +
                "                try {\n" +
                "                    Integer castValue = Integer.parseInt(value);\n" +
                "                    return new ElementAttributeOne<>(type, \"id\", castValue);\n" +
                "                }\n" +
                "                catch (NumberFormatException ex) {\n" +
                "                    logger.warn(\"Значение \\\"\" + value + \"\\\" имеет неверный тип данных \" +\n" +
                "                            \"для запроса поиска по \\\"\" + field + \"\\\"\");\n" +
                "                    return new ElementAttributeOne<>(AttributeType.NOTHING.getID(), null, null);\n" +
                "                }\n" +
                "            }\n");

        if (mapping.getColumns() != null) {
            for (GenerationColumn column : mapping.getColumns()) {
                String type = column.getType();
                String upperCase = column.getName().toUpperCase();
                String columnName = GenerationUtils.getName(column.getName());

                switch (type) {
                    case ("String"):
                        oneAttribute.append("            case " + upperCase + ": {\n" +
                                "                return new ElementAttributeOne<>(type, \"" + columnName + "\", value);\n" +
                                "            }\n");
                        break;
                    case ("Date"): {
                        generationImports.addType(new Import(type, ImportType.NONE));

                        oneAttribute.append("            case " + upperCase + ": {\n" +
                                "                try {\n" +
                                "                    Date castValue = Structure.formatTimeMillisecondTZ.parse(value);\n" +
                                "                    return new ElementAttributeOne<>(type, \"" + columnName + "\", castValue);\n" +
                                "                }\n" +
                                "                catch (ParseException ex){\n" +
                                "                    logger.warn(\"Значение \\\"\" + value + \"\\\" имеет parse данных \" +\n" +
                                "                            \"для запроса поиска по \\\"\" + field + \"\\\"\");\n" +
                                "                    return new ElementAttributeOne<>(type, null, null);\n" +
                                "                }\n" +
                                "            }\n");
                        break;
                    }
                    case ("Integer"): {
                        oneAttribute.append("            case " + upperCase + ": {\n" +
                                "                try {\n" +
                                "                    Integer castValue = Integer.parseInt(value);\n" +
                                "                    return new ElementAttributeOne<>(type, \"" + columnName + "\", castValue);\n" +
                                "                }\n" +
                                "                catch (NumberFormatException ex) {\n" +
                                "                    logger.warn(\"Значение \\\"\" + value + \"\\\" имеет неверный тип данных \" +\n" +
                                "                            \"для запроса поиска по \\\"\" + field + \"\\\"\");\n" +
                                "                    return new ElementAttributeOne<>(AttributeType.NOTHING.getID(), null, null);\n" +
                                "                }\n" +
                                "            }\n");
                        break;
                    }
                    case ("Long"): {
                    }
                    case ("Float"): {
                    }
                    case ("Boolean"): {
                        oneAttribute.append("            case " + upperCase + ": {\n" +
                                "                try {\n" +
                                "                    " + type + " castValue = " + type + ".parse" + type + "(value);\n" +
                                "                    return new ElementAttributeOne<>(type, \"" + columnName + "\", castValue);\n" +
                                "                }\n" +
                                "                catch (NumberFormatException ex) {\n" +
                                "                    logger.warn(\"Значение \\\"\" + value + \"\\\" имеет неверный тип данных \" +\n" +
                                "                            \"для запроса поиска по \\\"\" + field + \"\\\"\");\n" +
                                "                    return new ElementAttributeOne<>(AttributeType.NOTHING.getID(), null, null);\n" +
                                "                }\n" +
                                "            }\n");
                        break;
                    }
                }
            }
        }
        if (mapping.getLinkColumns() != null) {
            for (GenerationLinkColumn column : mapping.getLinkColumns()) {
                String type = column.getType();
                String upperCase = column.getName().toUpperCase();
                String columnName = GenerationUtils.getName(column.getName());

                generationImports.addType(new Import(type, ImportType.DATA));

                oneAttribute.append("            case " + upperCase + ": {\n" +
                        "                try {\n" +
                        "                    " + type + " target" + type + ";\n" +
                        "                    if ((value == null) || (value.equals(\"null\"))) {\n" +
                        "                        target" + type + " = null;\n" +
                        "                    }\n" +
                        "                    else {\n" +
                        "                        Integer castValue = Integer.parseInt(value);\n" +
                        "                        target" + type + " = new " + type + "();\n" +
                        "                        target" + type + ".setId(castValue);\n" +
                        "                    }\n" +
                        "\n" +
                        "                    return new ElementAttributeOne<>(type, \"" + columnName + "\", target" + type + ");\n" +
                        "                }\n" +
                        "                catch (NumberFormatException ex) {\n" +
                        "                    logger.warn(\"Значение \\\"\" + value + \"\\\" имеет неверный тип данных \" +\n" +
                        "                            \"для запроса поиска по \\\"\" + field + \"\\\"\");\n" +
                        "                    return new ElementAttributeOne<>(AttributeType.NOTHING.getID(), null, null);\n" +
                        "                }\n" +
                        "            }\n");
            }
        }

        generateParentOneAttribute(mapping, oneAttribute);

        oneAttribute.append("            default: {\n" +
                        "                logger.warn(\"Запроса поиска по \\\"\" + field + \"\\\" не найден\");\n" +
                        "                return new ElementAttributeOne<>(AttributeType.NOTHING.getID(), null, null);\n" +
                        "            }\n" +
                        "       }\n" +
                        "   }\n");
    }

    private void generateGetAttribute(GenerationMapping mapping) {
        twoAttribute.append("" +
                "    public static ElementAttribute getAttribute(int type, int field, String value1, String value2) {\n" +
                "        switch (field) {\n" +
                "            case ID: {\n" +
                "                try {\n" +
                "                    Integer castValue1 = Integer.parseInt(value1);\n" +
                "                    Integer castValue2 = Integer.parseInt(value2);\n" +
                "                    return new ElementAttributeTwo<>(type, \"id\", castValue1, castValue2);\n" +
                "                }\n" +
                "                catch (NumberFormatException ex) {\n" +
                "                    logger.warn(\"Значение \\\"\" + value1+ \"или значение\" + value2 + \"\\\" имеет неверный тип данных \" +\n" +
                "                            \"для запроса поиска по \\\"\" + field + \"\\\"\");\n" +
                "                    return new ElementAttributeTwo<>(AttributeType.NOTHING.getID(), null, null, null);\n" +
                "                }\n" +
                "            }\n");

        if (mapping.getColumns() != null) {
            for (GenerationColumn column : mapping.getColumns()) {
                String type = column.getType();
                String upperCase = column.getName().toUpperCase();
                String columnName = GenerationUtils.getName(column.getName());

                switch (type) {
                    case ("String"):
                        twoAttribute.append("            case " + upperCase + ": {\n" +
                                "                return new ElementAttributeTwo<>(type, \"" + columnName + "\", value1, value2);\n" +
                                "            }\n");
                        break;
                    case ("Date"): {
                        generationImports.addType(new Import(type, ImportType.NONE));

                        twoAttribute.append("            case " + upperCase + ": {\n" +
                                "                try {\n" +
                                "                    Date castValue1 = Structure.formatTimeMillisecondTZ.parse(value1);\n" +
                                "                    Date castValue2 = Structure.formatTimeMillisecondTZ.parse(value2);\n" +
                                "                    return new ElementAttributeTwo<>(type, \"" + columnName + "\", castValue1, castValue2);\n" +
                                "                }\n" +
                                "                catch (ParseException ex) {\n" +
                                "                    logger.warn(\"Значение \\\"\" + value1 + \" или значение \" + value2 + \"\\\" имеет parse данных \" +\n" +
                                "                            \"для запроса поиска по \\\"\" + field + \"\\\"\");\n" +
                                "                    return new ElementAttributeTwo<>(type, null, null, null);\n" +
                                "                }\n" +
                                "            }\n");
                        break;
                    }
                    case ("Integer"): {
                        twoAttribute.append("            case " + upperCase + ": {\n" +
                                "                try {\n" +
                                "                    Integer castValue1 = Integer.parseInt(value1);\n" +
                                "                    Integer castValue2 = Integer.parseInt(value2);\n" +
                                "                    return new ElementAttributeTwo<>(type, \"" + columnName + "\", castValue1, castValue2);\n" +
                                "                }\n" +
                                "                catch (NumberFormatException ex) {\n" +
                                "                    logger.warn(\"Значение \\\"\" + value1+ \"или значение\" + value2 + \"\\\" имеет неверный тип данных \" +\n" +
                                "                            \"для запроса поиска по \\\"\" + field + \"\\\"\");\n" +
                                "                    return new ElementAttributeTwo<>(AttributeType.NOTHING.getID(), null, null, null);\n" +
                                "                }\n" +
                                "            }\n");
                        break;
                    }
                    case ("Long"): {
                    }
                    case ("Float"): {
                    }
                    case ("Boolean"): {
                        twoAttribute.append("            case " + upperCase + ": {\n" +
                                "                try {\n" +
                                "                    " + type + " castValue1 = " + type + ".parse" + type + "(value1);\n" +
                                "                    " + type + " castValue2 = " + type + ".parse" + type + "(value2);\n" +
                                "                    return new ElementAttributeTwo<>(type, \"" + columnName + "\", castValue1, castValue2);\n" +
                                "                }\n" +
                                "                catch (NumberFormatException ex) {\n" +
                                "                    logger.warn(\"Значение \\\"\" + value1+ \"или значение\" + value2 + \"\\\" имеет неверный тип данных \" +\n" +
                                "                            \"для запроса поиска по \\\"\" + field + \"\\\"\");\n" +
                                "                    return new ElementAttributeTwo<>(AttributeType.NOTHING.getID(), null, null, null);\n" +
                                "                }\n" +
                                "            }\n");
                        break;
                    }
                }
            }
        }

        generateParentTwoAttribute(mapping, twoAttribute);

        twoAttribute.append("            default: {\n" +
                "                logger.warn(\"Запроса поиска по \\\"\" + field + \"\\\" не найден\");\n" +
                "                return new ElementAttributeOne<>(AttributeType.NOTHING.getID(), null, null);\n" +
                "            }\n" +
                "       }\n" +
                "   }\n");
    }

    private void generateGetAttributeList(GenerationMapping mapping) {
        listAttribute.append("    public static ElementAttribute getAttribute(int type, int field, List<String> values) {\n" +
                "        switch (field) {\n" +
                "            case ID: {\n" +
                "                try {\n" +
                "                    List<Integer> listAttribute = new ArrayList<>();\n" +
                "                    for (String value : values) {\n" +
                "                        Integer castValue = Integer.parseInt(value);\n" +
                "                        listAttribute.add(castValue);\n" +
                "                    }\n" +
                "                    return new ElementAttributeList<>(type, \"id\", listAttribute);\n" +
                "                }\n" +
                "                catch (NumberFormatException ex) {\n" +
                "                    logger.warn(\"Значение \\\"\" + values + \"\\\" имеет неверный тип данных \" +\n" +
                "                            \"для запроса поиска по \\\"\" + field + \"\\\"\");\n" +
                "                    return new ElementAttributeList<>(AttributeType.NOTHING.getID(), null, null);\n" +
                "                }\n" +
                "            }\n");

        if (mapping.getColumns() != null) {
            for (GenerationColumn column : mapping.getColumns()) {
                String type = column.getType();
                String upperCase = column.getName().toUpperCase();
                String columnName = GenerationUtils.getName(column.getName());

                switch (type) {
                    case ("String"):
                        listAttribute.append("            case " + upperCase + ": {\n" +
                                "                return new ElementAttributeList<>(type, \"" + columnName + "\", values);\n" +
                                "            }\n");
                        break;
                    case ("Date"): {
                        generationImports.addType(new Import(type, ImportType.NONE));

                        listAttribute.append("            case " + upperCase + ": {\n" +
                                "                try {\n" +
                                "                    List<Date> listAttribute = new ArrayList<>();\n" +
                                "                    for (String value : values) {\n" +
                                "                        Date castValue = Structure.formatTimeMillisecondTZ.parse(value);\n" +
                                "                        listAttribute.add(castValue);\n" +
                                "                    }\n" +
                                "                    return new ElementAttributeList<>(type, \"" + columnName + "\", listAttribute);\n" +
                                "                }\n" +
                                "                catch (ParseException ex) {\n" +
                                "                    logger.warn(\"Значение \\\"\" + values + \"\\\" имеет неверный тип данных \" +\n" +
                                "                            \"для запроса поиска по \\\"\" + field + \"\\\"\");\n" +
                                "                    return new ElementAttributeList<>(AttributeType.NOTHING.getID(), null, null);\n" +
                                "                }\n" +
                                "            }\n");
                        break;
                    }
                    case ("Integer"): {
                        listAttribute.append("            case " + upperCase + ": {\n" +
                                "                try {\n" +
                                "                    List<Integer> listAttribute = new ArrayList<>();\n" +
                                "                    for (String value : values) {\n" +
                                "                        Integer castValue = Integer.parseInt(value);\n" +
                                "                        listAttribute.add(castValue);\n" +
                                "                    }\n" +
                                "                    return new ElementAttributeList<>(type, \"" + columnName + "\", listAttribute);\n" +
                                "                }\n" +
                                "                catch (NumberFormatException ex) {\n" +
                                "                    logger.warn(\"Значение \\\"\" + values + \"\\\" имеет неверный тип данных \" +\n" +
                                "                            \"для запроса поиска по \\\"\" + field + \"\\\"\");\n" +
                                "                    return new ElementAttributeList<>(AttributeType.NOTHING.getID(), null, null);\n" +
                                "                }\n" +
                                "            }\n");
                        break;
                    }
                    case ("Long"): {
                    }
                    case ("Float"): {
                    }
                    case ("Boolean"): {
                        listAttribute.append("            case " + upperCase + ": {\n" +
                                "                try {\n" +
                                "                    List<" + type + "> listAttribute = new ArrayList<>();\n" +
                                "                    for (String value : values) {\n" +
                                "                        " + type + " castValue = " + type + ".parse" + type + "(value);\n" +
                                "                        listAttribute.add(castValue);\n" +
                                "                    }\n" +
                                "                    return new ElementAttributeList<>(type, \"" + columnName + "\", listAttribute);\n" +
                                "                }\n" +
                                "                catch (NumberFormatException ex) {\n" +
                                "                    logger.warn(\"Значение \\\"\" + values + \"\\\" имеет неверный тип данных \" +\n" +
                                "                            \"для запроса поиска по \\\"\" + field + \"\\\"\");\n" +
                                "                    return new ElementAttributeList<>(AttributeType.NOTHING.getID(), null, null);\n" +
                                "                }\n" +
                                "            }\n");
                        break;
                    }
                }
            }
        }

        if (mapping.getLinkColumns() != null) {
            for (GenerationLinkColumn column : mapping.getLinkColumns()) {
                String type = column.getType();
                String upperCase = column.getName().toUpperCase();
                String columnName = GenerationUtils.getName(column.getName());

                generationImports.addType(new Import(type, ImportType.DATA));

                listAttribute.append("            case " + upperCase + ": {\n" +
                        "                try {\n" +
                        "                    " + type + " target" + type + ";\n" +
                        "                    List<" + type + "> listAttribute =new ArrayList<>();\n" +
                        "                    if (values == null) {\n" +
                        "                        logger.warn(\"Значение имеет нулевую ссылку\" +\n" +
                        "                                \"для запроса поиска по \\\"\" + field + \"\\\"\");\n" +
                        "                        return new ElementAttributeList<>(AttributeType.NOTHING.getID(), null, null);\n" +
                        "                    }\n" +
                        "                    else {\n" +
                        "                        for(String value : values) {\n" +
                        "                            if (value.equals(\"null\")) {\n" +
                        "                                target" + type + " = null;\n" +
                        "                            }\n" +
                        "                            else {\n" +
                        "                                Integer castValue = Integer.parseInt(value);\n" +
                        "                                target" + type + " = new " + type + "();\n" +
                        "                                target" + type + ".setId(castValue);\n" +
                        "                            }\n" +
                        "                            listAttribute.add(target" + type + ");\n" +
                        "                        }\n" +
                        "                    }\n" +
                        "                    return new ElementAttributeList<>(type,\"" + columnName + "\", listAttribute);\n" +
                        "                }\n" +
                        "                catch (NumberFormatException ex) {\n" +
                        "                    logger.warn(\"Значение \\\"\" + values + \"\\\" имеет неверный тип данных \" +\n" +
                        "                            \"для запроса поиска по \\\"\" + field + \"\\\"\");\n" +
                        "                    return new ElementAttributeList<>(AttributeType.NOTHING.getID(), null, null);\n" +
                        "                }\n" +
                        "            }");
            }
        }

        generateParentListAttribute(mapping, listAttribute);

        listAttribute.append("            default: {\n" +
                "                logger.warn(\"Запроса поиска по \\\"\" + field + \"\\\" не найден\");\n" +
                "                return new ElementAttributeList<>(AttributeType.NOTHING.getID(), null, null);\n" +
                "            }");

    }

    private void generateFooter(GenerationMapping mapping) {
        footer.append("        }\n" +
                "    }\n" +
                "}");
    }

    private void generateParentOneAttribute(GenerationMapping mapping, StringBuilder builder) {
        if (mapping.getParentTable() != null) {
            GenerationMapping parentMapping = SpringFactory.getInstance().getMapping(mapping.getParentTable());

            if (parentMapping != null) {
                for (GenerationColumn column : parentMapping.getColumns()) {
                    String type = column.getType();
                    String upperCase = column.getName().toUpperCase();
                    String columnName = GenerationUtils.getName(column.getName());

                    switch (type) {
                        case ("String"):
                            builder.append("            case " + upperCase + ": {\n" +
                                    "                return new ElementAttributeOne<>(type, \"" + columnName + "\", value);\n" +
                                    "            }\n");
                            break;
                        case ("Date"): {
                            generationImports.addType(new Import(type, ImportType.NONE));

                            builder.append("            case " + upperCase + ": {\n" +
                                    "                try {\n" +
                                    "                    Date castValue = Structure.formatTimeMillisecondTZ.parse(value);\n" +
                                    "                    return new ElementAttributeOne<>(type, \"" + columnName + "\", castValue);\n" +
                                    "                }\n" +
                                    "                catch (ParseException ex){\n" +
                                    "                    logger.warn(\"Значение \\\"\" + value + \"\\\" имеет parse данных \" +\n" +
                                    "                            \"для запроса поиска по \\\"\" + field + \"\\\"\");\n" +
                                    "                    return new ElementAttributeOne<>(type, null, null);\n" +
                                    "                }\n" +
                                    "            }\n");
                            break;
                        }
                        case ("Integer"): {
                            builder.append("            case " + upperCase + ": {\n" +
                                    "                try {\n" +
                                    "                    Integer castValue = Integer.parseInt(value);\n" +
                                    "                    return new ElementAttributeOne<>(type, \"" + columnName + "\", castValue);\n" +
                                    "                }\n" +
                                    "                catch (NumberFormatException ex) {\n" +
                                    "                    logger.warn(\"Значение \\\"\" + value + \"\\\" имеет неверный тип данных \" +\n" +
                                    "                            \"для запроса поиска по \\\"\" + field + \"\\\"\");\n" +
                                    "                    return new ElementAttributeOne<>(AttributeType.NOTHING.getID(), null, null);\n" +
                                    "                }\n" +
                                    "            }\n");
                            break;
                        }
                        case ("Long"): {
                        }
                        case ("Float"): {
                        }
                        case ("Boolean"): {
                            builder.append("            case " + upperCase + ": {\n" +
                                    "                try {\n" +
                                    "                    " + type + " castValue = " + type + ".parse" + type + "(value);\n" +
                                    "                    return new ElementAttributeOne<>(type, \"" + columnName + "\", castValue);\n" +
                                    "                }\n" +
                                    "                catch (NumberFormatException ex) {\n" +
                                    "                    logger.warn(\"Значение \\\"\" + value + \"\\\" имеет неверный тип данных \" +\n" +
                                    "                            \"для запроса поиска по \\\"\" + field + \"\\\"\");\n" +
                                    "                    return new ElementAttributeOne<>(AttributeType.NOTHING.getID(), null, null);\n" +
                                    "                }\n" +
                                    "            }\n");
                            break;
                        }
                    }
                }
                if (parentMapping.getLinkColumns() != null) {
                    for (GenerationLinkColumn column : parentMapping.getLinkColumns()) {
                        String type = column.getType();
                        String upperCase = column.getName().toUpperCase();
                        String columnName = GenerationUtils.getName(column.getName());

                        generationImports.addType(new Import(type, ImportType.DATA));

                        builder.append("            case " + upperCase + ": {\n" +
                                "                try {\n" +
                                "                    " + type + " target" + type + ";\n" +
                                "                    if ((value == null) || (value.equals(\"null\"))) {\n" +
                                "                        target" + type + " = null;\n" +
                                "                    }\n" +
                                "                    else {\n" +
                                "                        Integer castValue = Integer.parseInt(value);\n" +
                                "                        target" + type + " = new " + type + "();\n" +
                                "                        target" + type + ".setId(castValue);\n" +
                                "                    }\n" +
                                "\n" +
                                "                    return new ElementAttributeOne<>(type, \"" + columnName + "\", target" + type + ");\n" +
                                "                }\n" +
                                "                catch (NumberFormatException ex) {\n" +
                                "                    logger.warn(\"Значение \\\"\" + value + \"\\\" имеет неверный тип данных \" +\n" +
                                "                            \"для запроса поиска по \\\"\" + field + \"\\\"\");\n" +
                                "                    return new ElementAttributeOne<>(AttributeType.NOTHING.getID(), null, null);\n" +
                                "                }\n" +
                                "            }\n");
                    }
                }

                generateParentOneAttribute(parentMapping, builder);
            }
        }
    }

    private void generateParentTwoAttribute(GenerationMapping mapping, StringBuilder builder) {
        if (mapping.getParentTable() != null) {
            GenerationMapping parentMapping = SpringFactory.getInstance().getMapping(mapping.getParentTable());

            if (parentMapping != null) {
                for (GenerationColumn column : parentMapping.getColumns()) {
                    String type = column.getType();
                    String upperCase = column.getName().toUpperCase();
                    String columnName = GenerationUtils.getName(column.getName());

                    switch (type) {
                        case ("String"):
                            builder.append("            case " + upperCase + ": {\n" +
                                    "                return new ElementAttributeTwo<>(type, \"" + columnName + "\", value1, value2);\n" +
                                    "            }\n");
                            break;
                        case ("Date"): {
                            generationImports.addType(new Import(type, ImportType.NONE));

                            builder.append("            case " + upperCase + ": {\n" +
                                    "                try {\n" +
                                    "                    Date castValue1 = Structure.formatTimeMillisecondTZ.parse(value1);\n" +
                                    "                    Date castValue2 = Structure.formatTimeMillisecondTZ.parse(value2);\n" +
                                    "                    return new ElementAttributeTwo<>(type, \"" + columnName + "\", castValue1, castValue2);\n" +
                                    "                }\n" +
                                    "                catch (ParseException ex) {\n" +
                                    "                    logger.warn(\"Значение \\\"\" + value1 + \" или значение \" + value2 + \"\\\" имеет parse данных \" +\n" +
                                    "                            \"для запроса поиска по \\\"\" + field + \"\\\"\");\n" +
                                    "                    return new ElementAttributeTwo<>(type, null, null, null);\n" +
                                    "                }\n" +
                                    "            }\n");
                            break;
                        }
                        case ("Integer"): {
                            builder.append("            case " + upperCase + ": {\n" +
                                    "                try {\n" +
                                    "                    Integer castValue1 = Integer.parseInt(value1);\n" +
                                    "                    Integer castValue2 = Integer.parseInt(value2);\n" +
                                    "                    return new ElementAttributeTwo<>(type, \"" + columnName + "\", castValue1, castValue2);\n" +
                                    "                }\n" +
                                    "                catch (NumberFormatException ex) {\n" +
                                    "                    logger.warn(\"Значение \\\"\" + value1+ \"или значение\" + value2 + \"\\\" имеет неверный тип данных \" +\n" +
                                    "                            \"для запроса поиска по \\\"\" + field + \"\\\"\");\n" +
                                    "                    return new ElementAttributeTwo<>(AttributeType.NOTHING.getID(), null, null, null);\n" +
                                    "                }\n" +
                                    "            }\n");
                            break;
                        }
                        case ("Long"): {}
                        case ("Float"): {}
                        case ("Boolean"): {
                            builder.append("            case " + upperCase + ": {\n" +
                                    "                try {\n" +
                                    "                    " + type + " castValue1 = " + type + ".parse" + type + "(value1);\n" +
                                    "                    " + type + " castValue2 = " + type + ".parse" + type + "(value2);\n" +
                                    "                    return new ElementAttributeTwo<>(type, \"" + columnName + "\", castValue1, castValue2);\n" +
                                    "                }\n" +
                                    "                catch (NumberFormatException ex) {\n" +
                                    "                    logger.warn(\"Значение \\\"\" + value1+ \"или значение\" + value2 + \"\\\" имеет неверный тип данных \" +\n" +
                                    "                            \"для запроса поиска по \\\"\" + field + \"\\\"\");\n" +
                                    "                    return new ElementAttributeTwo<>(AttributeType.NOTHING.getID(), null, null, null);\n" +
                                    "                }\n" +
                                    "            }\n");
                            break;
                        }
                    }
                }

                generateParentTwoAttribute(parentMapping, builder);
            }
        }
    }

    private void generateParentListAttribute(GenerationMapping mapping, StringBuilder builder) {
        if (mapping.getParentTable() != null) {
            GenerationMapping parentMapping = SpringFactory.getInstance().getMapping(mapping.getParentTable());

            if (parentMapping != null) {
                for (GenerationColumn column : parentMapping.getColumns()) {
                    String type = column.getType();
                    String upperCase = column.getName().toUpperCase();
                    String columnName = GenerationUtils.getName(column.getName());

                    switch (type) {
                        case ("String"):
                            builder.append("            case " + upperCase + ": {\n" +
                                    "                return new ElementAttributeList<>(type, \"" + columnName + "\", values);\n" +
                                    "            }\n");
                            break;
                        case ("Date"): {
                            generationImports.addType(new Import(type, ImportType.NONE));

                            builder.append("            case " + upperCase + ": {\n" +
                                    "                try {\n" +
                                    "                    List<Date> listAttribute = new ArrayList<>();\n" +
                                    "                    for (String value : values) {\n" +
                                    "                        Date castValue = Structure.formatTimeMillisecondTZ.parse(value);\n" +
                                    "                        listAttribute.add(castValue);\n" +
                                    "                    }\n" +
                                    "                    return new ElementAttributeList<>(type, \"" + columnName + "\", listAttribute);\n" +
                                    "                }\n" +
                                    "                catch (ParseException ex) {\n" +
                                    "                    logger.warn(\"Значение \\\"\" + values + \"\\\" имеет неверный тип данных \" +\n" +
                                    "                            \"для запроса поиска по \\\"\" + field + \"\\\"\");\n" +
                                    "                    return new ElementAttributeList<>(AttributeType.NOTHING.getID(), null, null);\n" +
                                    "                }\n" +
                                    "            }\n");
                            break;
                        }
                        case ("Integer"): {
                            builder.append("            case " + upperCase + ": {\n" +
                                    "                try {\n" +
                                    "                    List<Integer> listAttribute = new ArrayList<>();\n" +
                                    "                    for (String value : values) {\n" +
                                    "                        Integer castValue = Integer.parseInt(value);\n" +
                                    "                        listAttribute.add(castValue);\n" +
                                    "                    }\n" +
                                    "                    return new ElementAttributeList<>(type, \"" + columnName + "\", listAttribute);\n" +
                                    "                }\n" +
                                    "                catch (NumberFormatException ex) {\n" +
                                    "                    logger.warn(\"Значение \\\"\" + values + \"\\\" имеет неверный тип данных \" +\n" +
                                    "                            \"для запроса поиска по \\\"\" + field + \"\\\"\");\n" +
                                    "                    return new ElementAttributeList<>(AttributeType.NOTHING.getID(), null, null);\n" +
                                    "                }\n" +
                                    "            }\n");
                            break;
                        }
                        case ("Long"): {}
                        case ("Float"): {}
                        case ("Boolean"): {
                            builder.append("            case " + upperCase + ": {\n" +
                                    "                try {\n" +
                                    "                    List<" + type + "> listAttribute = new ArrayList<>();\n" +
                                    "                    for (String value : values) {\n" +
                                    "                        " + type + " castValue = " + type + ".parse" + type + "(value);\n" +
                                    "                        listAttribute.add(castValue);\n" +
                                    "                    }\n" +
                                    "                    return new ElementAttributeList<>(type, \"" + columnName + "\", listAttribute);\n" +
                                    "                }\n" +
                                    "                catch (NumberFormatException ex) {\n" +
                                    "                    logger.warn(\"Значение \\\"\" + values + \"\\\" имеет неверный тип данных \" +\n" +
                                    "                            \"для запроса поиска по \\\"\" + field + \"\\\"\");\n" +
                                    "                    return new ElementAttributeList<>(AttributeType.NOTHING.getID(), null, null);\n" +
                                    "                }\n" +
                                    "            }\n");
                            break;
                        }
                    }
                }

                if (parentMapping.getLinkColumns() != null) {
                    for (GenerationLinkColumn column : parentMapping.getLinkColumns()) {
                        String type = column.getType();
                        String upperCase = column.getName().toUpperCase();
                        String columnName = GenerationUtils.getName(column.getName());

                        generationImports.addType(new Import(type, ImportType.DATA));

                        builder.append("            case " + upperCase + ": {\n" +
                                "                try {\n" +
                                "                    " + type + " target" + type + ";\n" +
                                "                    List<" + type + "> listAttribute =new ArrayList<>();\n" +
                                "                    if (values == null) {\n" +
                                "                        logger.warn(\"Значение имеет нулевую ссылку\" +\n" +
                                "                                \"для запроса поиска по \\\"\" + field + \"\\\"\");\n" +
                                "                        return new ElementAttributeList<>(AttributeType.NOTHING.getID(), null, null);\n" +
                                "                    }\n" +
                                "                    else {\n" +
                                "                        for(String value : values) {\n" +
                                "                            if (value.equals(\"null\")) {\n" +
                                "                                target" + type + " = null;\n" +
                                "                            }\n" +
                                "                            else {\n" +
                                "                                Integer castValue = Integer.parseInt(value);\n" +
                                "                                target" + type + " = new " + type + "();\n" +
                                "                                target" + type + ".setId(castValue);\n" +
                                "                            }\n" +
                                "                            listAttribute.add(target" + type + ");\n" +
                                "                        }\n" +
                                "                    }\n" +
                                "                    return new ElementAttributeList<>(type,\"" + columnName + "\", listAttribute);\n" +
                                "                }\n" +
                                "                catch (NumberFormatException ex) {\n" +
                                "                    logger.warn(\"Значение \\\"\" + values + \"\\\" имеет неверный тип данных \" +\n" +
                                "                            \"для запроса поиска по \\\"\" + field + \"\\\"\");\n" +
                                "                    return new ElementAttributeList<>(AttributeType.NOTHING.getID(), null, null);\n" +
                                "                }\n" +
                                "            }");
                    }
                }

                generateParentListAttribute(parentMapping, builder);
            }
        }
    }

    private Integer generateParentHeader(GenerationMapping mapping, StringBuilder builder, Integer i) {
        if (mapping.getParentTable() != null) {
            GenerationMapping parentMapping = SpringFactory.getInstance().getMapping(mapping.getParentTable());

            if (parentMapping != null) {
                for (GenerationColumn column : parentMapping.getColumns()) {
                    String uppedCase = column.getName().toUpperCase();

                    i++;
                    builder.append("    public static final int " + uppedCase + " = " + i + ";\n");
                }

                if (parentMapping.getLinkColumns() != null) {
                    for (GenerationLinkColumn column : parentMapping.getLinkColumns()) {
                        String uppedCase = column.getName().toUpperCase();

                        i++;
                        builder.append("    public static final int " + uppedCase + " = " + i + ";\n");
                    }
                }

                return generateParentHeader(parentMapping, builder, i);
            }
        }

        return i;
    }
}
