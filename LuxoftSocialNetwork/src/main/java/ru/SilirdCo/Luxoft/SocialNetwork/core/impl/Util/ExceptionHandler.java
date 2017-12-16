package ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Util;

import org.slf4j.Logger;

public class ExceptionHandler {
    public static void handle(Logger logger, Throwable throwable) {
        String trace = throwable + "\n";
        for (StackTraceElement stackTraceElement : throwable.getStackTrace()) {
            trace += "    at " + stackTraceElement.toString() + "\n";
        }
        logger.error(trace + addCauseTrace(throwable.getCause()));
    }

    private static String addCauseTrace(Throwable throwable) {
        if (throwable != null) {
            String trace = "Caused by: " + throwable + "\n";
            for (StackTraceElement stackTraceElement : throwable.getStackTrace()) {
                trace += "    at " + stackTraceElement.toString() + "\n";
            }
            return trace + addCauseTrace(throwable.getCause());
        }
        else {
            return "";
        }
    }
}
