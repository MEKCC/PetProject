package com.petproject.logger;

import java.util.LinkedList;

public class StdOutHandler implements ILogHandler, Runnable {
    private LinkedList<LogMessage> m_queue = new LinkedList<>();
    private Thread m_thread = new Thread(this, this.getClass().getSimpleName());
    private static final int LOW_PRIORITY = 3;
    private static final long MAX_WAIT = 1000L;

    public StdOutHandler() {
        this.m_thread.setDaemon(true);
        this.m_thread.setPriority(3);
        this.m_thread.start();
    }

    public void addLogMessage(LogMessage msg) {
        synchronized (this.m_queue) {
            this.m_queue.add(msg);
            this.m_queue.notify();
        }
    }

    public void run() {
        while (true) {
            LogMessage msg = null;
            synchronized (this.m_queue) {
                if (this.m_queue.isEmpty()) {
                    try {
                        this.m_queue.wait(1000L);
                    } catch (InterruptedException var5) {
                    }
                } else {
                    msg = this.m_queue.remove();
                }
            }

            if (null != msg) {
                if (C2FLogger.LogLevel.ERROR.compare(msg.getLogLevel()) <= 0) {
                    System.err.println(msg);
                    System.err.flush();
                } else {
                    System.out.println(msg);
                    System.out.flush();
                }
            }
        }
    }

    public static void main(String[] args) {
        ILogHandler[] handlers = new ILogHandler[]{new StdOutHandler()};
        C2FLogger.initAppConfig("Logger Test", C2FLogger.LogLevel.DEBUG, handlers);
        C2FLogger logger = new C2FLogger();
        logger.log(C2FLogger.LogLevel.INFO, "usersession1", C2FLogger.LogType.GENERAL, () -> "Test log message 1");
        logger.log(C2FLogger.LogLevel.DEBUG, "usersession2", C2FLogger.LogType.USE, () -> "See me 2");
        logger.log(C2FLogger.LogLevel.DEBUG1, "usersession2", C2FLogger.LogType.USE, () -> "See me 3");
        logger.log(C2FLogger.LogLevel.DEBUG2, "usersession1", C2FLogger.LogType.ENVIRONMENT, () -> "Don't see me 4");
        logger.log(C2FLogger.LogLevel.WARNING, "usersession3", C2FLogger.LogType.SYSTEM, () -> "Test log message 5");
        logger.log(C2FLogger.LogLevel.ERROR, "usersession3", C2FLogger.LogType.SYSTEM, () -> "Test log message 6");
        logger.log(C2FLogger.LogLevel.FATAL, "usersession3", C2FLogger.LogType.SYSTEM, () -> "Test log message 7");

        try {
            Thread.sleep(2000L);
        } catch (InterruptedException var4) {
        }

    }
}