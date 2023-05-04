//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.petproject.logger;

import java.io.PrintWriter;
import java.io.StringWriter;

public class C2FLogger {

    private static String sm_appName = "-unassigned-";
    private static LogLevel sm_minLogLevel;
    private static ILogHandler[] sm_logHandlers;
    private static String sm_thisClassName;

    public C2FLogger() {
    }

    public void log(LogLevel logLevel, String sessionId, LogType logType, LambdaLog genBody) {
        this.log(logLevel, sessionId, logType, null, genBody);
    }

    public void log(LogLevel logLevel, String sessionId, LogType logType, Throwable ex, LambdaLog genBody) {
        if (sm_minLogLevel.compare(logLevel) <= 0 && null != sm_logHandlers && 0 != sm_logHandlers.length) {
            long now = System.currentTimeMillis();
            StackTraceElement[] stack = (new Throwable()).getStackTrace();

            int levelsUp = 1;

            if (levelsUp == stack.length) {
                --levelsUp;
            }

            StringBuilder sb = new StringBuilder();
            sb.append(genBody.lambdaLog());
            if (null != ex) {
                if (0 != sb.length()) {
                    sb.append(":  ");
                }

                StringWriter sw = new StringWriter();
                PrintWriter exStackTrace = new PrintWriter(sw);
                ex.printStackTrace(new PrintWriter(exStackTrace));
                sb.append(sw);
            }

            LogMessage msg = new LogMessage(sm_appName, stack[levelsUp].getFileName(), stack[levelsUp].getLineNumber(), stack[levelsUp].getClassName(), stack[levelsUp].getMethodName(), sessionId, now, logLevel, logType, sb.toString());
            ILogHandler[] var19 = sm_logHandlers;

            for (ILogHandler handler : var19) {
                try {
                    handler.addLogMessage(msg);
                } catch (Exception var17) {
                    System.out.println("ERROR: LogHandler " + handler.getClass().getName() + " threw exception on message:" + msg);
                    var17.printStackTrace();
                }
            }

        }
    }

    public void debug(String sessionId, LogType logType, LambdaLog genBody) {
        this.log(C2FLogger.LogLevel.DEBUG, sessionId, logType, genBody);
    }

    public void debug(String sessionId, LogType logType, Throwable ex, LambdaLog genBody) {
        this.log(C2FLogger.LogLevel.DEBUG, sessionId, logType, ex, genBody);
    }

    public void info(String sessionId, LogType logType, LambdaLog genBody) {
        this.log(C2FLogger.LogLevel.INFO, sessionId, logType, genBody);
    }

    public void info(String sessionId, LogType logType, Throwable ex, LambdaLog genBody) {
        this.log(C2FLogger.LogLevel.INFO, sessionId, logType, ex, genBody);
    }

    public void warn(String sessionId, LogType logType, LambdaLog genBody) {
        this.log(C2FLogger.LogLevel.WARNING, sessionId, logType, genBody);
    }

    public void warn(String sessionId, LogType logType, Throwable ex, LambdaLog genBody) {
        this.log(C2FLogger.LogLevel.WARNING, sessionId, logType, ex, genBody);
    }

    public void error(String sessionId, LogType logType, LambdaLog genBody) {
        this.log(C2FLogger.LogLevel.ERROR, sessionId, logType, genBody);
    }

    public void error(String sessionId, LogType logType, Throwable ex, LambdaLog genBody) {
        this.log(C2FLogger.LogLevel.ERROR, sessionId, logType, ex, genBody);
    }

    public void fatal(String sessionId, LogType logType, LambdaLog genBody) {
        this.log(C2FLogger.LogLevel.FATAL, sessionId, logType, genBody);
    }

    public void fatal(String sessionId, LogType logType, Throwable ex, LambdaLog genBody) {
        this.log(C2FLogger.LogLevel.FATAL, sessionId, logType, ex, genBody);
    }

    public static void initAppConfig(String appName, LogLevel minLogLevel, ILogHandler[] handlers) {
        sm_appName = appName;
        sm_minLogLevel = minLogLevel;
        sm_logHandlers = handlers;
    }

    static {
        sm_minLogLevel = C2FLogger.LogLevel.DEBUG9;
        sm_logHandlers = null;
        sm_thisClassName = C2FLogger.class.getName();
    }

    public interface LambdaLog {
        String lambdaLog();
    }

    public enum LogLevel {
        DEBUG9(0),
        DEBUG8(1),
        DEBUG7(2),
        DEBUG6(3),
        DEBUG5(4),
        DEBUG4(5),
        DEBUG3(6),
        DEBUG2(7),
        DEBUG1(8),
        DEBUG(8),
        INFO(9),
        WARNING(10),
        ERROR(11),
        FATAL(12);

        private int m_level;

        LogLevel(int level) {
            this.m_level = level;
        }

        public int compare(LogLevel other) {
            return this.m_level - other.m_level;
        }
    }

    public enum LogType {
        GENERAL,
        SYSTEM,
        SUBSYSTEM,
        USE,
        ENVIRONMENT;

        LogType() {
        }
    }
}