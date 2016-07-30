package com.relevantcodes.extentreports.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class LogStructure {
    
    private List<Log> logList;

    LogStructure() {
        logList = new ArrayList<>();
    }
    
    public void add(Log log) {
        logList.add(log);
    }

    public Log get(int x) {
        return logList.get(x);
    }

    public List<Log> getAll() {
        return logList;
    }

    public LogIterator getIterator() { return new LogIterator(); }

    // log iterator
    private class LogIterator implements Iterator<Log> {
        private int logIterIndex;

        LogIterator() {
            logIterIndex = 0;
        }

        @Override
        public boolean hasNext() {
            return logList != null && logList.size() >= logIterIndex + 1;
        }

        @Override
        public Log next() {
            if (hasNext()) {
                return logList.get(logIterIndex++);
            }

            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            logList.remove(logIterIndex);
        }
    }
}