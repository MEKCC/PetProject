package com.petproject.logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogMessage {
    private String m_appName;
    private String m_fileName;
    private int m_lineNumber;
    private String m_className;
    private String m_methodName;
    private C2FLogger.LogLevel m_logLevel;
    private String m_sessionId;
    private C2FLogger.LogType m_logType;
    private String m_message;
    private long m_timestamp;
    private static final Pattern ESCAPE_CHARS = Pattern.compile("[\\\n\r\t\"]");
    private static final Map<Character, String> REPLACEMENTS = new HashMap();

    public LogMessage(String appName, String fileName, int lineNumber, String className, String methodName, String sessionId, long timestamp, C2FLogger.LogLevel logLevel, C2FLogger.LogType logType, String message) {
        this.m_appName = appName;
        this.m_fileName = fileName;
        this.m_lineNumber = lineNumber;
        String[] splitname = className.split("\\.");
        this.m_className = splitname[splitname.length - 1];
        this.m_methodName = methodName;
        this.m_logLevel = logLevel;
        this.m_sessionId = sessionId;
        this.m_logType = logType;
        this.m_message = escapeMessage(message);
        this.m_timestamp = timestamp;
    }

    private LogMessage() {
    }

    public String getAppName() {
        return this.m_appName;
    }

    public String getFileName() {
        return this.m_fileName;
    }

    public int getLineNumber() {
        return this.m_lineNumber;
    }

    public String getClassName() {
        return this.m_className;
    }

    public String getMethodName() {
        return this.m_methodName;
    }

    public C2FLogger.LogLevel getLogLevel() {
        return this.m_logLevel;
    }

    public String getSessionId() {
        return this.m_sessionId;
    }

    public C2FLogger.LogType getLogType() {
        return this.m_logType;
    }

    public String getMessage() {
        return this.m_message;
    }

    public long getTimestamp() {
        return this.m_timestamp;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        OffsetDateTime d = OffsetDateTime.ofInstant(Instant.ofEpochMilli(this.m_timestamp), ZoneId.of("UTC"));
        sb.append("[");
        sb.append(d.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        sb.append("] ");
        sb.append(this.m_appName);
        sb.append(" ");
        if (null != this.m_className) {
            sb.append(this.m_className);
            sb.append(".");
        }

        sb.append(this.m_methodName);
        sb.append("(");
        sb.append(this.m_fileName);
        sb.append(":");
        sb.append(this.m_lineNumber);
        sb.append(") ");
        sb.append(this.m_logLevel.name());
        sb.append(" {");
        if (null != this.m_sessionId) {
            sb.append(this.m_sessionId);
        }

        sb.append("} ");
        sb.append(this.m_logType.name());
        sb.append(" - ");
        sb.append(this.m_message);
        return sb.toString();
    }

    private static String escapeMessage(String s) {
        if (null == s) {
            return s;
        } else {
            StringBuffer sb = new StringBuffer(s);
            int offset = 0;
            Matcher matcher = ESCAPE_CHARS.matcher(s);
            int position = 0;

            boolean foundSomething;
            int charAt;
            for(foundSomething = false; matcher.find(position); position = charAt + 1) {
                foundSomething = true;
                charAt = matcher.start();
                String replacement = REPLACEMENTS.get(s.charAt(charAt));
                sb.replace(charAt + offset, charAt + offset + 1, replacement);
                offset += replacement.length() - 1;
            }

            return foundSomething ? sb.toString() : s;
        }
    }

    public void toEscapedJsonString(JsonStringBuilder sb) {
        sb.startObject();
        OffsetDateTime d = OffsetDateTime.ofInstant(Instant.ofEpochMilli(this.m_timestamp), ZoneId.of("UTC"));
        sb.addProperty("timestamp", d.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        sb.addProperty("functionalComponentName", this.m_appName);
        String classPrefix = "";
        if (null != this.m_className) {
            classPrefix = this.m_className + ".";
        }

        sb.addProperty("methodName", classPrefix + this.m_methodName);
        sb.addProperty("fileName", this.m_fileName);
        sb.addProperty("lineNumber", this.m_lineNumber);
        sb.addProperty("logLevel", this.m_logLevel.name());
        if (null != this.m_sessionId) {
            sb.addProperty("sessionId", this.m_sessionId);
        }

        sb.addProperty("logType", this.m_logType.name());
        sb.addProperty("message", this.m_message);
        sb.endObject();
    }

    public void toJsonString(StringBuilder sb) {
        sb.append("{ ");
        OffsetDateTime d = OffsetDateTime.ofInstant(Instant.ofEpochMilli(this.m_timestamp), ZoneId.of("UTC"));
        sb.append("\"timestamp\": \"");
        sb.append(d.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        sb.append("\",");
        sb.append("\"functionalComponentName\": \"");
        sb.append(this.m_appName);
        sb.append("\",");
        String classPrefix = "";
        if (null != this.m_className) {
            classPrefix = this.m_className + ".";
        }

        sb.append("\"methodName\" : \"");
        sb.append(classPrefix + this.m_methodName);
        sb.append("\",");
        sb.append("\"fileName\": \"");
        sb.append(this.m_fileName);
        sb.append("\",");
        sb.append("\"lineNumber\": \"");
        sb.append(this.m_lineNumber);
        sb.append("\",");
        sb.append("\"logLevel\" : \"");
        sb.append(this.m_logLevel.name());
        sb.append("\",");
        if (null != this.m_sessionId) {
            sb.append("\"sessionId\": \"");
            sb.append(this.m_sessionId);
            sb.append("\",");
        }

        sb.append("\"logType\": \"");
        sb.append(this.m_logType.name());
        sb.append("\",");
        sb.append("\"message\": \"");
        sb.append(escapeMessage(this.m_message));
        sb.append("\"");
        sb.append(" }");
    }

    public static LogMessage fromJsonObjectString(String jsonStr) {
        LogMessage msg = null;

        try {
            JsonElement baseEl = (new JsonParser()).parse(jsonStr);
            msg = internalParseLogMessage(baseEl);
        } catch (JsonSyntaxException var3) {
            System.out.println("ERROR: Unable to parse LogMessage: " + jsonStr + "\nError is: " + var3.getMessage());
        }

        return msg;
    }

    private static LogMessage internalParseLogMessage(JsonElement baseEl) {
        if (baseEl.isJsonObject()) {
            JsonObject obj = baseEl.getAsJsonObject();
            LogMessage lm = new LogMessage();
            String classMethod;
            if (obj.has("timestamp")) {
                classMethod = obj.get("timestamp").getAsString();
                Instant inst = Instant.from(DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse(classMethod));
                lm.m_timestamp = inst.toEpochMilli();
            } else {
                Instant i = Instant.ofEpochMilli(System.currentTimeMillis());
                lm.m_timestamp = i.toEpochMilli();
            }

            lm.m_appName = obj.has("functionalComponentName") ? obj.get("functionalComponentName").getAsString() : "";
            lm.m_fileName = obj.has("fileName") ? obj.get("fileName").getAsString() : "";
            lm.m_lineNumber = obj.has("lineNumber") ? obj.get("lineNumber").getAsInt() : 0;
            classMethod = obj.has("methodName") ? obj.get("methodName").getAsString() : "";
            if (classMethod.contains(".")) {
                String[] cmSplit = classMethod.split("\\.");
                if (cmSplit.length > 1) {
                    lm.m_className = cmSplit[cmSplit.length - 2];
                }

                lm.m_methodName = cmSplit[cmSplit.length - 1];
            } else {
                lm.m_methodName = classMethod;
            }

            if (obj.has("logLevel")) {
                lm.m_logLevel = C2FLogger.LogLevel.valueOf(obj.get("logLevel").getAsString());
            } else {
                lm.m_logLevel = C2FLogger.LogLevel.INFO;
            }

            JsonElement sessionEl = obj.get("sessionId");
            if (null != sessionEl) {
                lm.m_sessionId = sessionEl.getAsString();
            }

            if (obj.has("logType")) {
                lm.m_logType = C2FLogger.LogType.valueOf(obj.get("logType").getAsString());
            } else {
                lm.m_logType = C2FLogger.LogType.GENERAL;
            }

            lm.m_message = escapeMessage(obj.has("message") ? obj.get("message").getAsString() : "");
            return lm;
        } else {
            return null;
        }
    }

    public static List<LogMessage> fromJsonArrayString(String jsonStr) {
        ArrayList<LogMessage> msgs = new ArrayList();

        try {
            JsonElement arrayEl = (new JsonParser()).parse(jsonStr);
            if (arrayEl.isJsonArray()) {
                JsonArray array = arrayEl.getAsJsonArray();
                Iterator var4 = array.iterator();

                while(var4.hasNext()) {
                    JsonElement elem = (JsonElement)var4.next();

                    try {
                        msgs.add(internalParseLogMessage(elem));
                    } catch (JsonSyntaxException var7) {
                        System.out.println("ERROR: Unable to parse LogMessage: " + elem.getAsString() + "\nError is: " + var7.getMessage());
                        var7.printStackTrace();
                    }
                }
            }

            return msgs;
        } catch (JsonSyntaxException var8) {
            System.out.println("ERROR: Unable to parse LogMessage array: " + jsonStr);
            var8.printStackTrace();
            return null;
        }
    }

    static {
        REPLACEMENTS.put('\\', "\\\\");
        REPLACEMENTS.put('\n', "\\n");
        REPLACEMENTS.put('\r', "\\r");
        REPLACEMENTS.put('\t', "\\t");
        REPLACEMENTS.put('"', "'");
    }
}
