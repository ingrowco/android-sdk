package co.ingrow.android;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

public class InGrowLogging {

    private static final Logger LOGGER;
    private static final StreamHandler HANDLER;

    static {
        LOGGER = Logger.getLogger(InGrowLogging.class.getName());
        HANDLER = new StreamHandler(System.out, new SimpleFormatter());
        LOGGER.addHandler(HANDLER);
        disableLogging();
    }

    public static void log(String msg) {
        if (isLoggingEnabled()) {
            LOGGER.log(Level.FINER, msg);
            HANDLER.flush();
        }
    }

    public static void log(String msg, Throwable throwable) {
        if (isLoggingEnabled()) {
            LOGGER.log(Level.FINER, msg, throwable);
            HANDLER.flush();
        }
    }

    public static void enableLogging() {
        setLogLevel(Level.FINER);
    }

    public static void disableLogging() {
        setLogLevel(Level.OFF);
    }

    public static boolean isLoggingEnabled() {
        return LOGGER.getLevel() == Level.FINER;
    }

    private static void setLogLevel(Level newLevel) {
        LOGGER.setLevel(newLevel);
        for (Handler handler : LOGGER.getHandlers()) {
            try {
                handler.setLevel(newLevel);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }
}

