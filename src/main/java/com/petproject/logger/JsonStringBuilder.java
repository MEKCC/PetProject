package com.petproject.logger;

import java.util.EmptyStackException;
import java.util.Stack;

public class JsonStringBuilder {
    private Stack<Integer> m_state = new Stack();
    private static final int IN_OBJECT = 1;
    private static final int IN_ARRAY = 2;
    private Stack<Boolean> m_needComma = new Stack();
    private boolean m_isValid = true;
    private StringBuilder m_out = new StringBuilder("");

    public JsonStringBuilder() {
    }

    public String toString() {
        return this.m_out.toString();
    }

    public void startObject() {
        if (0 < this.m_state.size()) {
            this.addCommaMaybe();
        }

        this.m_state.push(1);
        this.m_needComma.push(Boolean.FALSE);
        this.m_out.append("{ ");
    }

    public void startNamedObject(String name) {
        if (0 < this.m_state.size()) {
            this.addCommaMaybe();
        }

        this.m_out.append("\\\"" + name + "\\\": ");
        this.m_state.push(1);
        this.m_needComma.push(Boolean.FALSE);
        this.m_out.append("{ ");
    }

    public boolean endObject() {
        this.m_out.append(" }");
        return this.verifyState(1, true);
    }

    public boolean addProperty(String name, Float f) {
        this.addCommaMaybe();
        this.m_out.append("\\\"" + name + "\\\": ");
        if (this.verifyState(1, false)) {
            this.m_out.append(f.toString());
            return true;
        } else {
            return false;
        }
    }

    public boolean addProperty(String name, Double d) {
        this.addCommaMaybe();
        this.m_out.append("\\\"" + name + "\\\": ");
        if (this.verifyState(1, false)) {
            this.m_out.append(d.toString());
            return true;
        } else {
            return false;
        }
    }

    public boolean addProperty(String name, Long l) {
        this.addCommaMaybe();
        this.m_out.append("\\\"" + name + "\\\": ");
        if (this.verifyState(1, false)) {
            this.m_out.append(l.toString());
            return true;
        } else {
            return false;
        }
    }

    public boolean addProperty(String name, Integer i) {
        this.addCommaMaybe();
        this.m_out.append("\\\"" + name + "\\\": ");
        if (this.verifyState(1, false)) {
            this.m_out.append(i.toString());
            return true;
        } else {
            return false;
        }
    }

    public boolean addProperty(String name, String s) {
        this.addCommaMaybe();
        this.m_out.append("\\\"" + name + "\\\": ");
        if (this.verifyState(1, false)) {
            this.escapeJson(s);
            return true;
        } else {
            return false;
        }
    }

    public boolean addProperty(String name, Boolean b) {
        this.addCommaMaybe();
        this.m_out.append("\\\"" + name + "\\\": ");
        if (this.verifyState(1, false)) {
            this.m_out.append(b.toString());
            return true;
        } else {
            return false;
        }
    }

    public void startArray() {
        if (0 < this.m_state.size()) {
            this.addCommaMaybe();
        }

        this.m_state.push(2);
        this.m_needComma.push(Boolean.FALSE);
        this.m_out.append("[ ");
    }

    public void startNamedArray(String name) {
        if (0 < this.m_state.size()) {
            this.addCommaMaybe();
        }

        this.m_out.append("\\\"" + name + "\\\": ");
        this.m_state.push(2);
        this.m_needComma.push(Boolean.FALSE);
        this.m_out.append("[ ");
    }

    public boolean endArray() {
        this.m_out.append(" ]");
        return this.verifyState(2, true);
    }

    public boolean add(Float f) {
        this.addCommaMaybe();
        if (this.verifyState(2, false)) {
            this.m_out.append(f.toString());
            return true;
        } else {
            return false;
        }
    }

    public boolean add(Double d) {
        this.addCommaMaybe();
        if (this.verifyState(2, false)) {
            this.m_out.append(d.toString());
            return true;
        } else {
            return false;
        }
    }

    public boolean add(Long l) {
        this.addCommaMaybe();
        if (this.verifyState(2, false)) {
            this.m_out.append(l.toString());
            return true;
        } else {
            return false;
        }
    }

    public boolean add(Integer i) {
        this.addCommaMaybe();
        if (this.verifyState(2, false)) {
            this.m_out.append(i.toString());
            return true;
        } else {
            return false;
        }
    }

    public boolean add(String s) {
        this.addCommaMaybe();
        if (this.verifyState(2, false)) {
            this.escapeJson(s);
            return true;
        } else {
            return false;
        }
    }

    private boolean verifyState(int expectedState, boolean pop) {
        try {
            int state;
            if (pop) {
                state = this.m_state.pop();
                this.m_needComma.pop();
            } else {
                state = this.m_state.peek();
            }

            if (expectedState != state) {
                this.m_isValid = false;
            }
        } catch (EmptyStackException var4) {
            this.m_isValid = false;
        }

        return this.m_isValid;
    }

    private void addCommaMaybe() {
        try {
            Boolean needComma = this.m_needComma.peek();
            if (needComma) {
                this.m_out.append(", ");
            } else {
                this.m_needComma.pop();
                this.m_needComma.push(Boolean.TRUE);
            }
        } catch (EmptyStackException var2) {
            this.m_isValid = false;
        }

    }

    private void escapeJson(String s) {
        this.m_out.append("\\\"");
        if (null != s) {
            for(int i = 0; i < s.length(); ++i) {
                char ch = s.charAt(i);
                switch (ch) {
                    case '\b':
                    case '\t':
                    case '\n':
                    case '\f':
                    case '\r':
                        this.m_out.append(" ");
                        break;
                    case '"':
                        this.m_out.append("\\\\\\\"");
                        break;
                    case '\\':
                        this.m_out.append("\\\\\\\\");
                        break;
                    default:
                        if (!Character.isISOControl(ch)) {
                            this.m_out.append(ch);
                        }
                }
            }
        }

        this.m_out.append("\\\"");
    }
}
