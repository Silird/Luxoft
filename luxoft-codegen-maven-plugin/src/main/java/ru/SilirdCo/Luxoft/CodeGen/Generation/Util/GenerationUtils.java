package ru.SilirdCo.Luxoft.CodeGen.Generation.Util;

import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.SilirdCo.Luxoft.CodeGen.Generation.Entity.GenerationColumn;
import ru.SilirdCo.Luxoft.CodeGen.Generation.Entity.GenerationMapping;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class GenerationUtils {
    private static final Logger logger = LoggerFactory.getLogger(GenerationUtils.class);

    public static String getTitleName(String name) {
        String result = getName(name);
        result = result.substring(0, 1).toUpperCase() + result.substring(1);
        return result;
    }

    public static String getName(String name) {
        String[] tmp = name.split("_");
        String result = "";

        for (int i = 0; i < tmp.length; i++) {
            if (i != 0) {
                result += tmp[i].substring(0, 1).toUpperCase() + tmp[i].substring(1);
            } else {
                result += tmp[i];
            }
        }

        return result;
    }

   /* public static String getUpperName(String name) {
        String[] tmp = name.split("_");
        String result = "";

        for(int i = 0; i < tmp.length; i++) {
            if (i != 0) {
                result += tmp[i].substring(0, 1).toUpperCase() + tmp[i].substring(1).toUpperCase();
            }
            else {
                result += tmp[i].toUpperCase();
            }
        }

        return result;
    }*/

    private static final String genAnnotation = "/*\n" +
            "==========================================================\n" +
            "Файл создан через генератор кода\n" +
            "Если надо что-то поменять, то надо отредактировать генератор кода иначе изменения могут не сохранится\n" +
            "и перегенерировать код\n" +
            "==========================================================\n" +
            "\n*/\n\n\n";

    private static final String genAnnotationXML = "<!--\n" +
            "==========================================================\n" +
            "Файл создан через генератор кода\n" +
            "Если надо что-то поменять, то надо отредактировать генератор кода иначе изменения могут не сохранится\n" +
            "и перегенерировать код\n" +
            "==========================================================\n" +
            "\n-->\n\n\n";

    private static final String genAnnotationSkeleton = "/*\n" +
            "==========================================================\n" +
            "Скелет файла создан через генератор кода\n" +
            "Файл изменять можно\n" +
            "==========================================================\n" +
            "\n*/\n\n\n";

    public static String baseDirectory = "genCode";

    /**
     * Запись в файл
     *
     * @param output   - строка для записи
     * @param fileName - имя файла
     * @throws IOException - стандартная ошибка при работе с прерываниями
     */
    public static void write(String output, String fileName) throws IOException {
        String separator = "\\";

        if (SystemUtils.IS_OS_LINUX) {
            separator = "/";
        }

        // Создание каталога
        String[] directories = output
                .split("\\n")[0]
                .split(";")[0]
                .split(" ")[1]
                .split("\\.");
        String path = baseDirectory + separator + getStartingDirectories(directories[3]);
        for (String directory : directories) {
            path += directory + separator;
        }

        File myPath = new File(path);

        if (!myPath.exists() && !myPath.mkdirs()) {
            logger.error("Ошибка при создании папки пути для файла:" + fileName + "\n" + path);
            return;
        }

        File file = new File(path + fileName);

        //проверяем, что если файл не существует то создаем его
        if (!file.exists()) {
            if (!file.createNewFile()) {
                logger.error("Ошибка при создании файла пути для файла:" + fileName + "\n");
                return;
            }
        }

        //PrintWriter обеспечит возможности записи в файл
        try (PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file.getAbsoluteFile()), StandardCharsets.UTF_8))) {
            //Записываем текст в файл
            out.print(genAnnotation + output);

        }
    }

    public static void writeXML(String output, String fileName, String filePath) throws IOException {
        // Создание каталога
        String separator = "\\";

        if (SystemUtils.IS_OS_LINUX) {
            separator = "/";
        }

        String path = baseDirectory + separator;

        String[] directories = filePath.split("\\.");

        for (String directory : directories) {
            path += directory + separator;
        }
        File myPath = new File(path);

        if (!myPath.exists() && !myPath.mkdirs()) {
            logger.error("Ошибка при создании папки пути для файла:" + fileName + "\n" + path);
            return;
        }

        File file = new File(path + fileName);

        //проверяем, что если файл не существует то создаем его
        if (!file.exists()) {
            if (!file.createNewFile()) {
                logger.error("Ошибка при создании файла пути для файла:" + fileName + "\n");
                return;
            }
        }

        //PrintWriter обеспечит возможности записи в файл
        try (PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file.getAbsoluteFile()), StandardCharsets.UTF_8))) {
            //Записываем текст в файл
            out.print(genAnnotationXML + output);
        }
    }

    /**
     * Запись в файл если он не существует
     *
     * @param output   - строка для записи
     * @param fileName - имя файла
     * @throws IOException - стандартная ошибка при работе с прерываниями
     */
    public static void writeNotExist(String output, String fileName) throws IOException {
        //String separator = "\\";

        /*
        if (SystemUtils.IS_OS_LINUX) {
            separator = "/";
        }
        */

        String separator = SystemUtils.FILE_SEPARATOR;

        // Создание каталога
        String[] directories = output
                .split("\\n")[0]
                .split(";")[0]
                .split(" ")[1]
                .split("\\.");
        String path = baseDirectory + separator + getStartingDirectories(directories[3]);
        for (String directory : directories) {
            path += directory + separator;
        }

        File myPath = new File(path);

        if (!myPath.exists() && !myPath.mkdirs()) {
            logger.error("Ошибка при создании папки пути для файла:" + fileName + "\n" + path);
            return;
        }

        File file = new File(path + fileName);

        //проверяем, что если файл не существует то создаем его
        if (!file.exists()) {
            if (!file.createNewFile()) {
                logger.error("Ошибка при создании файла:" + fileName + "\n");
                return;
            }

            //PrintWriter обеспечит возможности записи в файл
            try (PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file.getAbsoluteFile()), StandardCharsets.UTF_8))) {
                //Записываем текст в файл
                out.print(genAnnotationSkeleton + output);

            }
        }
    }

    public static String getStartingDirectories(String type) {
        String separator = "\\";

        if (SystemUtils.IS_OS_LINUX) {
            separator = "/";
        }

        switch (type) {
            case ("SocialNetwork"): {
                return "LuxoftSocialNetwork" + separator + "src" + separator + "main" + separator + "java" + separator;
            }
            default: {
                return "";
            }
        }

    }

    public static GenerationColumn getMainNameColumn(GenerationMapping mapping) {
        if (mapping.getMainName() != null) {
            return mapping.getMainName();
        }
        else {
            return getMainNameColumn(SpringFactory.getInstance().getMapping(mapping.getParentTable()));
        }
    }

    public static GenerationColumn getSortColumn(GenerationMapping mapping) {
        if (mapping.getSortColumn() != null) {
            return mapping.getSortColumn();
        }
        else {
            if (mapping.getParentTable() != null) {
                return getSortColumn(SpringFactory.getInstance().getMapping(mapping.getParentTable()));
            }
            else {
                return null;
            }
        }
    }
}
