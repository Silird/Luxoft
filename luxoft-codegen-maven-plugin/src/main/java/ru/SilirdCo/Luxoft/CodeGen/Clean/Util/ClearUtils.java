package ru.SilirdCo.Luxoft.CodeGen.Clean.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class ClearUtils {
    private final static Logger logger = LoggerFactory.getLogger(ClearUtils.class);

    public static boolean clearGenerated(String path) {
        logger.info("Начало удаления сгенерированного кода в папке: " + path);

        return recursiveDeletionGenerated(path);
    }

    private static boolean recursiveDeletionGenerated(String path) {
        File folder = new File(path);
        if (!folder.exists()) {
            logger.error("Пути \""+ path + "\" не существует");
            return false;
        }

        if (folder.isFile()) {
            logger.error("Путь \""+ path + "\" не является папкой");
            return false;
        }

        boolean success = true;
        String[] children = folder.list();
        if (children != null) {
            for (String childName : children) {
                if (childName.equals("Generated")) {
                    String childPath = path + File.separator + childName;
                    File deletedFolder = new File(childPath);
                    if (recursiveDeletion(deletedFolder)) {
                        logger.info("Удалено: " + childPath);
                    }
                    else {
                        logger.error("Ошибка удаления: " + childPath);
                        success = false;
                    }
                }
                else {
                    String childPath = path + File.separator + childName;
                    File deletedFile = new File(childPath);
                    if (deletedFile.isFile()) {
                        if (childName.contains("Generated")) {
                            if (recursiveDeletion(deletedFile)) {
                                logger.info("Удалено: " + childPath);
                            }
                            else {
                                logger.error("Ошибка удаления: " + childPath);
                                success = false;
                            }
                        }
                    }
                    else {
                        success = success && recursiveDeletionGenerated(childPath);
                    }
                }
            }
        }

        return success;
    }

    private static boolean recursiveDeletion(File deletedFile) {
        if (deletedFile.exists()) {
            if (deletedFile.isFile()) {
                return deletedFile.delete();
            }
            else {
                File[] children = deletedFile.listFiles();
                while ((children != null) && (children.length != 0)) {
                    for (File child : children) {
                        recursiveDeletion(child);
                    }
                    children = deletedFile.listFiles();
                }
                return deletedFile.delete();
            }
        }
        else {
            return false;
        }
    }
}
