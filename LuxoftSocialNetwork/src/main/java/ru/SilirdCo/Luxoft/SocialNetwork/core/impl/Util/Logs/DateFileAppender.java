package ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Util.Logs;

import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.recovery.ResilientFileOutputStream;
import ch.qos.logback.core.util.FileUtil;
import org.apache.commons.lang3.SystemUtils;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Util.Structure;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class DateFileAppender<T> extends FileAppender<T> {
    @Override
    public void openFile(String file_name) throws IOException {
        lock.lock();
        try {
            String[] tmp = file_name.split("\\.");
            String path = "";
            for (int i = 0; i < (tmp.length - 1); i++) {
                if (i != 0) {
                    path += ".";
                }
                path += tmp[i];
            }
            path += "_" + Structure.formatDateTime.format(new Date()) + "." + tmp[tmp.length - 1];

            if (SystemUtils.IS_OS_LINUX) {
                path = path.replaceAll("\\\\", "/");
            }

            File file = new File(path);
            boolean result = FileUtil.createMissingParentDirectories(file);
            if (!result) {
                addError("Failed to create parent directories for [" + file.getAbsolutePath() + "]");
            }

            ResilientFileOutputStream resilientFos = new ResilientFileOutputStream(file, append);
            resilientFos.setContext(context);
            setOutputStream(resilientFos);
        }
        finally {
            lock.unlock();
        }
    }
}
