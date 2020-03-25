package network.palace.dashboard.log;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class ForwardLogHandler extends ConsoleHandler {
    private Map<String, Logger> cachedLoggers = new ConcurrentHashMap<>();

    private Logger getLogger(String name) {
        Logger logger = cachedLoggers.get(name);
        if (logger == null) {
            logger = LogManager.getLogger(name);
            cachedLoggers.put(name, logger);
        }
        return logger;
    }

    @Override
    public void publish(LogRecord record) {
        Logger logger = getLogger(String.valueOf(record.getLoggerName()));
        Throwable exception = record.getThrown();
        Level level = record.getLevel();
        String message = getFormatter().formatMessage(record);

        if (level.equals(Level.SEVERE)) {
            logger.error(message, exception);
        } else if (level.equals(Level.WARNING)) {
            logger.warn(message, exception);
        } else if (level.equals(Level.INFO)) {
            logger.info(message, exception);
        } else if (level.equals(Level.CONFIG)) {
            logger.debug(message, exception);
        } else {
            logger.trace(message, exception);
        }
    }

    @Override
    public synchronized void flush() {
    }

    @Override
    public void close() {
    }
}
