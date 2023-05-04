package com.petproject.logger;

public class DebugHandler implements ILogHandler {
    public DebugHandler() {
    }

    public void addLogMessage(LogMessage msg) {
        if (C2FLogger.LogLevel.ERROR.compare(msg.getLogLevel()) <= 0) {
            System.err.println(msg);
            System.err.flush();
        } else {
            System.out.println(msg);
            System.out.flush();
        }
    }

    public static void main(String[] args) {
        ILogHandler[] handlers = new ILogHandler[]{new DebugHandler()};
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
            genException();
        } catch (Exception var4) {
            logger.log(C2FLogger.LogLevel.INFO, "usersession1", C2FLogger.LogType.GENERAL, var4, () -> "Test log message 1");
            logger.log(C2FLogger.LogLevel.DEBUG, "usersession2", C2FLogger.LogType.USE, var4, () -> "See me 2");
            logger.log(C2FLogger.LogLevel.DEBUG1, "usersession2", C2FLogger.LogType.USE, var4, () -> "See me 3");
            logger.log(C2FLogger.LogLevel.DEBUG2, "usersession1", C2FLogger.LogType.ENVIRONMENT, var4, () -> "Don't see me 4");
            logger.log(C2FLogger.LogLevel.WARNING, "usersession3", C2FLogger.LogType.SYSTEM, var4, () -> "Test log message 5");
            logger.log(C2FLogger.LogLevel.ERROR, "usersession3", C2FLogger.LogType.SYSTEM, var4, () -> "Test log message 6");
            logger.log(C2FLogger.LogLevel.FATAL, "usersession3", C2FLogger.LogType.SYSTEM, var4, () -> "Test log message 7");
        }

    }

    private static void genException() throws Exception {
        genException2();
    }

    private static void genException2() throws Exception {
        genException3();
    }

    private static void genException3() throws Exception {
        throw new Exception("Test me exception");
    }
}
