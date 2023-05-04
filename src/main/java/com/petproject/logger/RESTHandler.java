package com.petproject.logger;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;

public class RESTHandler implements ILogHandler, Runnable {
    private String m_restURL;
    private LinkedList<LogMessage> m_queue = new LinkedList<>();
    private Thread m_thread;
    private static final int LOW_PRIORITY = 3;
    private static final long MAX_WAIT = 1000L;

    public RESTHandler(String restURL) {
        this.m_restURL = restURL;
        this.m_thread = new Thread(this, this.getClass().getSimpleName());
        this.m_thread.setDaemon(true);
        this.m_thread.setPriority(3);
        this.m_thread.start();
    }

    public void addLogMessage(LogMessage msg) {
        synchronized(this.m_queue) {
            this.m_queue.add(msg);
            this.m_queue.notify();
        }
    }

    public void run() {
        LinkedList<LogMessage> logs = new LinkedList();

        while(true) {
            do {
                synchronized(this.m_queue) {
                    if (this.m_queue.isEmpty()) {
                        try {
                            this.m_queue.wait(1000L);
                        } catch (InterruptedException var9) {
                        }
                    } else {
                        logs.addAll(this.m_queue);
                        this.m_queue.clear();
                    }
                }
            } while(logs.isEmpty());

            StringBuilder sb = new StringBuilder();
            if (1 == logs.size()) {
                logs.get(0).toJsonString(sb);
            } else {
                sb.append("[ ");

                for(int i = 0; i < logs.size(); ++i) {
                    if (0 != i) {
                        sb.append(",");
                    }

                    logs.get(i).toJsonString(sb);
                }

                sb.append(" ]");
            }

            try {
                URL url = new URL(this.m_restURL);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setDoOutput(true);
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json");
                OutputStream os = con.getOutputStream();
                os.write(sb.toString().getBytes());
                os.close();
                int response = con.getResponseCode();
                if (200 > response || 300 <= response) {
                    System.out.println("ERROR: Unexpected response from remote logging endpoint: " + response + "  Lost log messages: ");
                    Iterator var7 = logs.iterator();

                    while(var7.hasNext()) {
                        LogMessage log = (LogMessage)var7.next();
                        System.out.println("    " + log.toString());
                    }
                }

                con.disconnect();
            } catch (Exception var11) {
                System.out.println("ERROR: Exception sending log message to remote server:");
                var11.printStackTrace();
            }

            logs.clear();
        }
    }

    public static void main(String[] args) {
        ILogHandler[] handlers = new ILogHandler[]{new DebugHandler(), new RESTHandler("http://localhost:8080//logservice/v1/log")};
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
            Thread.sleep(5000L);
        } catch (InterruptedException var4) {
        }

    }
}