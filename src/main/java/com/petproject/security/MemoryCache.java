package com.petproject.security;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;

public class MemoryCache<KeyType, DataType> {
    private Map<KeyType, MemoryCache<KeyType, DataType>.UserData> m_userDataCache = new HashMap();
    private TreeMap<Long, List<KeyType>> m_cacheTimeout = new TreeMap();
    private long m_timeLimit;
    private boolean m_sinceLastUse;

    public MemoryCache(long timeoutInMillis, boolean sinceLastUse) {
        this.m_timeLimit = timeoutInMillis;
        this.m_sinceLastUse = sinceLastUse;
    }

    public synchronized DataType load(KeyType key) {
        this.clearOldData();
        MemoryCache<KeyType, DataType>.UserData ret = (UserData)this.m_userDataCache.get(key);
        if (null != ret) {
            if (this.m_sinceLastUse) {
                long lastAccessed = ret.getLastAccessed();
                List<KeyType> timeSet = (List)this.m_cacheTimeout.get(lastAccessed);
                if (null != timeSet) {
                    timeSet.remove(key);
                    if (0 == timeSet.size()) {
                        this.m_cacheTimeout.remove(lastAccessed);
                    }
                }

                ret.resetLastAccessed();
                this.addToTimeout(ret.getLastAccessed(), key);
            }

            return ret.getUserData();
        } else {
            return null;
        }
    }

    public synchronized void store(KeyType key, DataType data) {
        this.clearOldData();
        MemoryCache<KeyType, DataType>.UserData ud = new UserData(data);
        this.m_userDataCache.put(key, ud);
        this.addToTimeout(ud.getLastAccessed(), key);
    }

    public synchronized void remove(KeyType key) {
        MemoryCache<KeyType, DataType>.UserData ret = (UserData)this.m_userDataCache.remove(key);
        if (null != ret) {
            long lastAccessed = ret.getLastAccessed();
            List<KeyType> timeSet = (List)this.m_cacheTimeout.get(lastAccessed);
            if (null != timeSet) {
                timeSet.remove(key);
                if (0 == timeSet.size()) {
                    this.m_cacheTimeout.remove(lastAccessed);
                }
            }
        }

    }

    private void clearOldData() {
        long tooLate = System.currentTimeMillis() - this.m_timeLimit;

        try {
            boolean isTooOld = true;

            do {
                long firstKey = (Long)this.m_cacheTimeout.firstKey();
                if (firstKey < tooLate) {
                    List<KeyType> delme = (List)this.m_cacheTimeout.remove(firstKey);
                    Iterator var7 = delme.iterator();

                    while(var7.hasNext()) {
                        KeyType k = (KeyType) var7.next();
                        this.m_userDataCache.remove(k);
                    }
                } else {
                    isTooOld = false;
                }
            } while(isTooOld);
        } catch (NoSuchElementException var9) {
        }

    }

    private void addToTimeout(long newTime, KeyType key) {
        List<KeyType> newTimeSet = (List)this.m_cacheTimeout.get(newTime);
        if (null == newTimeSet) {
            newTimeSet = new LinkedList();
            this.m_cacheTimeout.put(newTime, newTimeSet);
        }

        ((List)newTimeSet).add(key);
    }

    private class UserData {
        private DataType m_userData;
        private long m_lastAccessed;

        public UserData(DataType data) {
            this.m_userData = data;
            this.resetLastAccessed();
        }

        public DataType getUserData() {
            return this.m_userData;
        }

        public long getLastAccessed() {
            return this.m_lastAccessed;
        }

        public void resetLastAccessed() {
            this.m_lastAccessed = System.currentTimeMillis();
        }
    }
}